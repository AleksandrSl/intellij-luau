package com.github.aleksandrsl.intellijluau

import com.github.aleksandrsl.intellijluau.declarations.LuauDeclaration
import com.github.aleksandrsl.intellijluau.declarations.LuauDeclarations
import com.github.aleksandrsl.intellijluau.declarations.LuauDeclarationsParser
import com.github.aleksandrsl.intellijluau.psi.LuauSimpleReference
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.PluginPathManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.util.messages.MessageBusConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.concurrent.atomic.AtomicReference

private val LOG = logger<LuauStdLibService>()

class LuauStdLibServiceImpl(val project: Project, private val coroutineScope: CoroutineScope) : Disposable,
    BaseLuauStdLibService() {
    override var stdlibDeclarations: LuauDeclarations? = null
    private var messageBusConnection: MessageBusConnection? = null
    private val loadingDeferred = AtomicReference<Deferred<Unit>?>(null)


    init {
        messageBusConnection = project.messageBus.connect(this)
        messageBusConnection?.subscribe(
            ProjectSettingsConfigurable.TOPIC,
            object : ProjectSettingsConfigurable.SettingsChangeListener {
                override fun settingsChanged(e: ProjectSettingsConfigurable.SettingsChangedEvent) {
//                    if (e.newState.robloxSecurityLevel != e.oldState.robloxSecurityLevel) {
//                        loadStdLibDeclarations()
//                    }
                }
            })
        loadStdLibDeclarations()
    }

    private fun loadStdLibDeclarations() {
        loadingDeferred.get()?.cancel()
        val settings = ProjectSettingsState.getInstance(project)

        // Calling this from coroutine scope produces garbage temporary file location
        // C:\Users\slale\.gradle\caches\8.9\transforms\2c8a365b11e772d43ecee417107d45cc\transformed\WebStorm-2024.3.4-win\typeDeclarations\globalTypes.None.d.luau
        val file = PluginPathManager.getPluginResource(
            javaClass, "typeDeclarations/globalTypes.${settings.robloxSecurityLevel.name}.d.luau"
        )

        loadingDeferred.set(coroutineScope.async(Dispatchers.IO) {
            if (file?.exists() == true) {
                try {
                    val parser = LuauDeclarationsParser()
                    stdlibDeclarations = parser.parse(file.path)
                } catch (e: Exception) {
                    LOG.error("Failed to parse stdlib declarations", e)
                }
            }
            // Adhoc solution. I'm surprised it works and doesn't complain that it's run from IO.
            DaemonCodeAnalyzer.getInstance(project).restart()
            println("Restarted daemon code analyzer")
        })
    }

    override fun dispose() {
    }
}

class LuauStdLibServiceTestImpl : BaseLuauStdLibService() {
    override var stdlibDeclarations: LuauDeclarations? = LuauDeclarationsParser().parseContent(
        """
        declare class Enum
            function GetEnumItems(self): { any }
        end

        declare class EnumItem
            Name: string
            Value: number
            EnumType: Enum
            function IsA(self, enumName: string): boolean
        end
        declare class EnumFont extends EnumItem end
        declare class EnumFont_INTERNAL extends Enum
            BuilderSans: EnumFont
            BuilderSansBold: EnumFont
            Unknown: EnumFont
        end
        declare debug: {
            info: (<R...>(thread, number, string) -> R...) & (<R...>(number, string) -> R...) & (<A..., R1..., R2...>((A...) -> R1..., string) -> R2...),
            traceback: ((string?, number?) -> string) & ((thread, string?, number?) -> string),
            profilebegin: (label: string) -> (),
            profileend: () -> (),
            getmemorycategory: () -> string,
            setmemorycategory: (tag: string) -> (),
            resetmemorycategory: () -> (),
        }
        declare class Workspace extends WorldRoot
            AirDensity: number
            AllowThirdPartySales: boolean
            ClientAnimatorThrottling: EnumClientAnimatorThrottlingMode
            function PGSIsEnabled(self): boolean
            function UnjoinFromOutsiders(self, objects: { Instance }): nil
        end
        declare game: DataModel
        declare function collectgarbage(mode: "count"): number
    """.trimIndent()
    )
}

// I miserably failed to force the main implementation to work in tests.
// It's both an async parsing and a reannotation.
// Plus, I don't know how to copy files to the test area the sandbox rule seems not to apply to the tests.
// So I ended up with two implementations and an interface.
// But then I thought that I can keep the main logic the same via abstract class.
abstract class BaseLuauStdLibService : LuauStdLibService {
    protected abstract var stdlibDeclarations: LuauDeclarations?

    // Instances cannot be created or used directly,
    // either it's a local variable or can be mark as unknown global.
    // Looks like most of the classes with `extends` cannot be created directly, let's check for that
    // Lol. There are exceptions, RaycastResult cannot be created according to the lsp, but it doesn't inherit anything.
    // Lol 2. Instance a global object is usable on its own.
    // So there is a class instance and a global instance. Cool.
    // My understanding up to this moment that only global declarations can be used as is. They usually have a constructor.
    // Most of the classes that can be used have counterparts as declarations.
    override fun resolveReference(chain: List<LuauSimpleReference>): List<LuauDeclaration>? {
        if (chain.isEmpty()) return null
        if (stdlibDeclarations == null) return null
        // We are locked on the enums.
        if (chain.first().text == "Enum") {
            if (chain.size == 1) return stdlibDeclarations!!.classes["Enum"]?.let { listOf(it) }
            return stdlibDeclarations!!.enums[chain[1].text]?.let { listOf(it) }
        } else {
            return stdlibDeclarations!!.globalObjects[chain.first().text]?.let { listOf(it) }
                ?: stdlibDeclarations!!.functions[chain.first().text]?.let { listOf(it) }
        }
    }
}

interface LuauStdLibService {
    fun resolveReference(chain: List<LuauSimpleReference>): List<LuauDeclaration>?
}

