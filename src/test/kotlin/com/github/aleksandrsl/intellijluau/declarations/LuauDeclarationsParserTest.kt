package com.github.aleksandrsl.intellijluau.declarations

import com.intellij.testFramework.utils.module.assertEqualsUnordered
import com.jetbrains.rd.util.firstOrNull
import junit.framework.TestCase

class LuauDeclarationsParserTest : TestCase() {
    private val parser: LuauDeclarationsParser = LuauDeclarationsParser()

    fun `test parser processes valid luau file`() {
        val declarations = javaClass.classLoader.getResource("declarations/globalTypes.RobloxScriptSecurity.d.luau")
            ?.let { parser.parse(it.path) }

        assertNotNull(declarations)
        assertTrue(declarations!!.enums.isNotEmpty())
        assertTrue(declarations.classes.isNotEmpty())
        assertTrue(declarations.functions.isNotEmpty())
        assertTrue(declarations.typeAliases.isNotEmpty())
        assertTrue(declarations.globalObjects.isNotEmpty())
    }

    fun `test parser processes type alias declaration`() {
        val declarations = parser.parseContent("""type ProtectedString = string""")

        val typeAliasDeclaration = declarations.typeAliases.firstOrNull()
        assertNotNull(typeAliasDeclaration)
        assertEquals("ProtectedString", typeAliasDeclaration?.key)
        assertEquals("string", typeAliasDeclaration?.value?.type)
    }

    fun `test parser processes global object declaration`() {
        val declarations = parser.parseContent(
            """declare utf8: {
    char: (...number) -> string,
    charpattern: string,
    codepoint: (string, number?, number?) -> (...number),
    codes: (string) -> ((string, number) -> (number, number), string, number),
    graphemes: (string, number?, number?) -> (() -> (number, number)),
    len: (string, number?, number?) -> (number?, number?),
    nfcnormalize: (string) -> string,
    nfdnormalize: (string) -> string,
    offset: (string, number, number?) -> number?,
}""".trimIndent()
        )

        val globalObjectDeclaration = declarations.globalObjects.firstOrNull()
        assertNotNull(globalObjectDeclaration)
        assertEquals("utf8", globalObjectDeclaration?.key)
        assertEqualsUnordered(
            setOf(
                "char",
                "charpattern",
                "codepoint",
                "codes",
                "graphemes",
                "len",
                "nfcnormalize",
                "nfdnormalize",
                "offset"
            ), globalObjectDeclaration!!.value.properties.keys
        )
    }

    fun `test parser processes short global object declaration`() {
        val declarations = parser.parseContent(
            """declare game: DataModel
                declare plugin: Plugin
            """.trimIndent()
        )

        val game = declarations.globalObjects["game"]
        val plugin = declarations.globalObjects["plugin"]
        assertNotNull(game)
        assertNotNull(plugin)
        assertEquals("DataModel", game?.type)
        assertEquals("Plugin", plugin?.type)
    }

    fun `test parser processes function declaration`() {
        val declarations = parser.parseContent("""declare function collectgarbage(mode: "count"): number""")

        val functionDeclaration = declarations.functions.firstOrNull()
        assertNotNull(functionDeclaration)
        assertEquals("collectgarbage", functionDeclaration?.key)
    }

    fun `test parser processes class declaration`() {
        // When
        val declarations = parser.parseContent(
            """declare class OverlapParams
	BruteForceAllSlow: boolean
	CollisionGroup: string
	FilterDescendantsInstances: { Instance }
	FilterType: EnumRaycastFilterType
	MaxParts: number
	RespectCanCollide: boolean
	function AddToFilter(self, instances: Instance | { Instance }): nil
end""".trimIndent()
        )

        // Then
        val classDeclaration = declarations.classes.firstOrNull()
        assertNotNull(classDeclaration)
        assertEquals("OverlapParams", classDeclaration?.key)
        assertEquals("OverlapParams", classDeclaration?.value?.name)
        assertEqualsUnordered(
            classDeclaration?.value?.properties?.keys!!, setOf(
                "BruteForceAllSlow",
                "CollisionGroup",
                "FilterDescendantsInstances",
                "FilterType",
                "MaxParts",
                "RespectCanCollide"
            )
        )
    }

    fun `test parser processes enum class declaration`() {
        val declarations = parser.parseContent(
            """declare class EnumTextXAlignment extends EnumItem end
declare class EnumTextXAlignment_INTERNAL extends Enum
	Center: EnumTextXAlignment
	Left: EnumTextXAlignment
	Right: EnumTextXAlignment
end""".trimIndent()
        )

        val enumClassDeclaration = declarations.enums.firstOrNull()
        assertNotNull(enumClassDeclaration)
        assertEquals("TextXAlignment", enumClassDeclaration?.value?.name)
        assertEquals("Enum", enumClassDeclaration?.value?.extends)
        assertEqualsUnordered(listOf("Center", "Left", "Right"), enumClassDeclaration?.value?.values!!)
    }

    fun `test parser performance is within acceptable range`() {
        val testFilePath = javaClass.classLoader.getResource("declarations/globalTypes.RobloxScriptSecurity.d.luau")

        assertNotNull(testFilePath)
        val startTime = System.currentTimeMillis()
        val declarations = parser.parse(testFilePath!!.path)
        val duration = System.currentTimeMillis() - startTime

        assertTrue(duration < 500)
        assertNotNull(declarations)
        assertTrue(declarations.enums.isNotEmpty())
    }

    fun `test parser handles ancestor Instance correctly`() {
        val declarations = parser.parseContent(
            """
declare class ServiceProvider extends Instance
	Close: RBXScriptSignal<>
end

declare class DataModel extends ServiceProvider
	CreatorId: number
end

declare class GenericSettings extends ServiceProvider
end
        """.trimIndent()
        )

        assertTrue(declarations.classes["GenericSettings"]!!.isInstance)
        assertTrue(declarations.classes["DataModel"]!!.isInstance)
    }
}
