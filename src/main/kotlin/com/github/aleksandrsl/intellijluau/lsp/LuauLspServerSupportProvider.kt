@file:Suppress("UnstableApiUsage")

package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.LuauFileType
import com.github.aleksandrsl.intellijluau.LuauIcons
import com.github.aleksandrsl.intellijluau.cli.LspCli
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServer
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import com.intellij.platform.lsp.api.lsWidget.LspServerWidgetItem
import org.eclipse.lsp4j.ClientCapabilities
import org.eclipse.lsp4j.ConfigurationItem

private val LOG = logger<LuauLspServerSupportProvider>()

class LuauLspServerSupportProvider : LspServerSupportProvider {
    override fun fileOpened(
        project: Project, file: VirtualFile, serverStarter: LspServerSupportProvider.LspServerStarter
    ) {
        // fileType may be slow, but it's used in other lsp implementations, so should be fine.
        // It's ok to check the settings here, ts does that and prisma plugin PrismaLspServerActivationRule
        if (file.fileType == LuauFileType && ProjectSettingsState.getInstance(project).isLspEnabledAndMinimallyConfigured) {
            serverStarter.ensureServerStarted(LuauLspServerDescriptor(project))
        }
    }

    override fun createLspServerWidgetItem(lspServer: LspServer, currentFile: VirtualFile?): LspServerWidgetItem =
        object : LspServerWidgetItem(
            lspServer, currentFile, LuauIcons.FILE, ProjectSettingsConfigurable::class.java
        ) {
            // The version should be available in the serverInfo > 1.49.1
            override val versionPostfix: @NlsSafe String
                get() {
                    val version =
                        lspServer.initializeResult?.serverInfo?.version ?: lspServer.project.getAutoLspVersion()
                    return if (version != null) " $version" else super.versionPostfix
                }
        }
}

private class LuauLspServerDescriptor(project: Project) : ProjectWideLspServerDescriptor(
    project, LuauBundle.message("luau.lsp.name")
) {
    override fun isSupportedFile(file: VirtualFile) = file.fileType == LuauFileType

    override val clientCapabilities: ClientCapabilities
        get() = super.clientCapabilities.apply {
            workspace.configuration = true
            // In 2025.1 there was added the `"diagnostic":{"dynamicRegistration":true}` config, which turns off the legacy publishDiagnostics
            // https://platform.jetbrains.com/t/lsp-diagnostics-change-in-2025-1/2124
            // So I remove this config to have the old way of showing errors, until I figure out what the new one is.
            textDocument.diagnostic = null
        }

    /*
     * See https://github.com/JohnnyMorganz/luau-lsp/blob/248ed7bd11dde2059d8fe00235776895738c5a16/src/include/LSP/ClientConfiguration.hpp for details
     * struct ClientConfiguration
     */
    override fun getWorkspaceConfiguration(item: ConfigurationItem): Any {
        val settings = ProjectSettingsState.getInstance(project).state
        val config: MutableMap<String?, Any?> = HashMap()

        val sourcemap: MutableMap<String?, Any?> = HashMap()
        sourcemap["enabled"] = settings.lspSourcemapSupportEnabled
        if (settings.lspSourcemapSupportEnabled && settings.lspSourcemapFile.isNotBlank()) {
            sourcemap["sourcemapFile"] = settings.lspSourcemapFile
        }
        config["sourcemap"] = sourcemap

        val platform: MutableMap<String?, Any?> = HashMap()
        platform["type"] = settings.platformType.value
        config["platform"] = platform

        config["ignoreGlobs"] = listOf("**/_Index/**")
        /*
         * Settings that are used by LSP, but I do not yet support
         *
         * types
         * /// Any definition files to load globally
         * std::vector<std::filesystem::path> definitionFiles{};
         * /// A list of globals to remove from the global scope. Accepts full libraries or particular functions (e.g., `table` or `table.clone`)
         * std::vector<std::string> disabledGlobals{};
         *
         * diagnostics
         * /// Whether to also compute diagnostics for dependents when a file changes
         * bool includeDependents = true;
         * /// Whether to compute diagnostics for a whole workspace
         * bool workspace = false;
         * /// Whether to use expressive DM types in the diagnostics typechecker
         * bool strictDatamodelTypes = false;
         *
         * inlayHints
         * InlayHintsParameterNamesConfig parameterNames = InlayHintsParameterNamesConfig::None;
         * bool variableTypes = false;
         * bool parameterTypes = false;
         * bool functionReturnTypes = false;
         * bool hideHintsForErrorTypes = false;
         * bool hideHintsForMatchingParameterNames = true;
         * size_t typeHintMaxLength = 50;
         * /// Whether type inlay hints should be made insertable
         * bool makeInsertable = true;
         *
         * hover
         * bool enabled = true;
         * bool showTableKinds = false;
         * bool multilineFunctionDefinitions = false;
         * bool strictDatamodelTypes = true;
         * bool includeStringLength = true;
         *
         * completion
         * bool enabled = true;
         * /// Whether to automatically autocomplete end
         * bool autocompleteEnd = false;
         * /// Whether we should suggest automatic imports in completions
         * /// DEPRECATED: USE `completion.imports.enabled` INSTEAD
         * bool suggestImports = false;
         * /// Automatic imports configuration
         * ClientCompletionImportsConfiguration imports{};
         * /// Automatically add parentheses to a function call
         * bool addParentheses = true;
         * /// If parentheses are added, include a $0 tabstop after the parentheses
         * bool addTabstopAfterParentheses = true;
         * /// If parentheses are added, fill call arguments with parameter names
         * bool fillCallArguments = true;
         * /// Whether to show non-function properties when performing a method call with a colon
         * bool showPropertiesOnMethodCall = false;
         * /// Enables the experimental fragment autocomplete system for performance improvements
         * bool enableFragmentAutocomplete = false;
         *
         * /// Whether we should suggest automatic imports in completions
         * bool enabled = false;
         * /// Whether services should be suggested in auto-import
         * bool suggestServices = true;
         * /// Whether requires should be suggested in auto-import
         * bool suggestRequires = true;
         * /// The style of the auto-imported require
         * ImportRequireStyle requireStyle = ImportRequireStyle::Auto;
         * ClientCompletionImportsStringRequiresConfiguration stringRequires;
         * /// Whether services and requires should be separated by an empty line
         * bool separateGroupsWithLine = false;
         * /// Files that match these globs will not be shown during auto-import
         * std::vector<std::string> ignoreGlobs{};
         *
         * signatureHelp
         * bool enabled = true;
         *
         * index
         * /// Whether the whole workspace should be indexed. If disabled, only limited support is
         * // available for features such as "Find All References" and "Rename"
         * bool enabled = true;
         * /// The maximum number of files that can be indexed
         * size_t maxFiles = 10000;
         *
         * fflags
         *
         * bytecode
         */
        return config
    }

    // TODO (AleksandrSl 29/06/2025): Can be enabled post 2025.1 where the support for pull diagnostics is added.
//    override val lspDiagnosticsSupport: LspDiagnosticsSupport = object : LspDiagnosticsSupport() {
//        override fun shouldAskServerForDiagnostics(file: VirtualFile): Boolean = true
//    }

    override fun createCommandLine(): GeneralCommandLine {
        // A hacky way to check whether LSP configuration is correct and up to date and provide the feedback only once per LSP start.
        // It would be ideal to do this before creating the descriptor, but the only entry point is fileOpened that is called for all the files open,
        // whenever only one language server is created in the end.
        // Another solution would be to call this check in the LspServerManager listener
        LuauLspManager.getInstance().checkLsp(project)

        val lspConfiguration = project.getLspConfiguration()
        if (lspConfiguration !is LspConfiguration.Enabled) {
            throw IllegalStateException("Tried to created a Luau LSP with disabled configuration")
        }
        return LspCli(project, lspConfiguration).createLspCli()
    }
}
