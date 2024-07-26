# intellij-luau

![Build](https://github.com/AleksandrSl/intellij-luau/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/24957-luau.svg)](https://plugins.jetbrains.com/plugin/24957-luau)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/24957-luau.svg)](https://plugins.jetbrains.com/plugin/24957-luau)

## Description
<!-- Plugin description -->
This plugin adds support for Luau language, it's very raw, but I hope to shape it with time

- [x] Luau LSP
- [x] Basic syntax highlighting
- [x] StyLua: format action to format current file

<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "intellij-luau"</kbd> >
  <kbd>Install</kbd>
  
- Manually:

  Download the [latest release](https://github.com/AleksandrSl/intellij-luau/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation

## Acknowledgements

- EmmyLua and Luanalysis for grammar stuff that I used as a base. 
- IntellijElm, IntellijHaxe and Intellij plugins for a ton of helpful examples and good code comments.
- Luau LSP for existence 

## Questions

### Why not add this to existing lua plugins? 
The primary reason is that I wanted the thing to work as quickly as I can. 
Adding support for somewhat different grammar and not breaking something that is already there sounded tricky to me.
But if there is an interest in the plugin in the future, maybe stuff can be ported.

Also, the builtin LSP support requires the most recent IDEA version. 

### Why is `.lua` a supported file extension? 
The codebase I started working on has all the files with `.lua` instead of proper `.luau` for unknown reason.

