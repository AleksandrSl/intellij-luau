# intellij-luau

[![Build](https://github.com/AleksandrSl/intellij-luau/actions/workflows/build.yml/badge.svg)](https://github.com/AleksandrSl/intellij-luau/actions/workflows/build.yml)
[![Version](https://img.shields.io/jetbrains/plugin/v/24957-luau.svg)](https://plugins.jetbrains.com/plugin/24957-luau)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/24957-luau.svg)](https://plugins.jetbrains.com/plugin/24957-luau)

## Description
<!-- Plugin description -->
This plugin adds support for [Luau language](https://luau.org)

- Luau LSP integration ([Only for IDEs with LSP support](https://plugins.jetbrains.com/docs/intellij/language-server-protocol.html#supported-ides))
- StyLua: format action and ability to use it instead of builtin-in IDEA formatter
- Basic syntax highlighting/completion when LSP is disabled

Plugin is in development, feel free to request features and suggest changes in [Issue tracker](https://github.com/AleksandrSl/intellij-luau/issues).

## Getting started

### Supported file extensions

Plugin works both with `.luau` and `.lua` and may conflict with other Lua plugins.

### Install the plugin

- Using the IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "intellij-luau"</kbd> >
  <kbd>Install</kbd>

- Manually:

  Download the [latest release](https://github.com/AleksandrSl/intellij-luau/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

### Configure the plugin

By default, LSP is enabled if [supported](https://plugins.jetbrains.com/docs/intellij/language-server-protocol.html#supported-ides) by the IDE.
Once you open any Luau file, you will be prompted to download the latest version of the LSP (it will be installed into the IDE service directory).

If you want to configure a specific version of the LSP go to `File | Settings | Languages & Frameworks | Luau`.

By default `sourcemap.json` is used to enhance autocompletion. 
There are several ways to autogenerate it as in the VSCode plugin.
If you have `default.project.json` and `rojo` installed, you'll be prompted to enable rojo autogeneration.
If you have a custom command to run, you can configure sourcemap generation in the same settings.

If you want to make settings default for the rest of the projects, there is an "apply and save as default" button at the end of the settings page. 
Once you saved the default configuration, you will be prompted to apply them when opening a new project. 

[LSP docs](https://github.com/JohnnyMorganz/luau-lsp)

<!-- Plugin description end -->

### Luau Language Server Companion

You can use [companion plugin](https://create.roblox.com/store/asset/10913122509/Luau-Language-Server-Companion) to get information about DataModels that are not save on FS either temporarily (while the project is not saved) or at all.

1. Install companion plugin to Studio
2. Turn on "Enable Roblox Studio Companion Plugin". This will start and HTTP server, communicating with the Studio plugin. You can configure port in the same settings (make sure to match it in the plugin settings in Studio).


### Roblox Script Sync

It just works without any need for LSP. However, LSP enhances the experience. 
If you have sourcemap support enabled in this plugin and a running sync, LSP will generate sourcemap on the fly without any help from Rojo.

In my limited testing everything works as expected. However, I noticed that Studio Luau Language Server Companion 
sometimes misses calls to the LSP to update the sourcemap, reconnecting the companion plugin fixes it. 

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation

## Acknowledgements

- IntellijElm, IntellijHaxe, and Intellij plugins for a ton of helpful examples and good code comments.
- Luau LSP for existence.

## Questions

### Why not add this to existing lua plugins? 
The primary reason is that I wanted the thing to work as quickly as I can. 
Adding support for somewhat different grammar and not breaking something that is already there sounded tricky to me.
But if there is an interest in the plugin in the future, maybe stuff can be ported.

Also, the builtin LSP support requires the most recent IDEA version. 

### Why is `.lua` a supported file extension? 
The codebase I started working on has all the files with `.lua` instead of proper `.luau` for unknown reason.
