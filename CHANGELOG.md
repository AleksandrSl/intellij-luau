<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# intellij-luau Changelog

## [Unreleased]

## [0.1.0] - 2025-09-16

### Added

- Breadcrumbs navigation for functions, methods, and type declarations
- Restart lsp in all the projects using the latest version when an update is downloaded
- `Update Roblox API Definitions` action, to refresh Roblox API definitions. They are also refreshed once a day
  automatically
- Support platform and sourcemap configuration for LSP
- Support for the platform type and sourcemap enabled/disabled state for LSP
- Improved LSP restart messages
- Docs for Roblox APIs
- Breadcrumbs and sticky lines
- Live templates
- VCS inlay hints
- Default ignore globs for sourcemap generation (as in VS Code)
- Use Foreman for StyLua if available
- Ignore Packages
- Improved completion: include function parameters in suggestions
- Luau LSP integration
- Formatting with StyLua
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)

### Fixed

- Do not restart LSP unless the settings are applied
- First incorrect start of the LSP in new projects
- Check for the rojo default project file
- Correctly hide loader indicators when the start state is disabled
- Synchronously get latest LSP version from disc to start
- Incorrect recovery rule breaking parsing ot type casts at the end of the block inside conditionals
- Do not suggest rojo sourcemaps generation when sourcemaps are not needed
- Do not add roblox data unless the platform type is roblox
- Plugin icon
- Correctly get a name for methods
- Allow completion without LSP support
- Use silent process handler for sourcemap generators
- Do not show LSP settings if IDE doesn't support LSP
- Use a correct notification group for sourcemap related notifications
- Do not run sourcemap generator when LSP is turned off
- NullPointerException in settings
- Missing registration for file template
- Legacy configurable ID calculation mode
- Parsing of type casts with optional values
- Parsing of parenthesized generic type packs in function returns
- Link to the LSP release notes
- Restart sourcemap generation only when related settings are changed
- Restart LSP only if it's running when Roblox docs are updated
- Missing LSP errors in newer IDEs (including 2025.1)
- Restart LSP only if the already selected version was downloaded
- Warning for long-running processes
- Return the correct version for the specific version in the settings

### Changed

- Bump minimal supported IDE version
- When using latest, the real LSP version is no longer saved in persisted settings
- Use an LSP version from the LSP server info if available
- Increase sourcemap generation timeout
- Remove sourcemap generator start notification
- Store LSP binaries in a directory preserved across IDE updates
- Restructure formatting to provide clearer errors
- Make update/install LSP notifications using buttons
- Improve notifications text for the sourcemap generator
- Improve error recovery for function definitions

## [0.1.0-eap] - 2025-06-12

### Added

- Luau LSP integration
- Formatting with StyLua
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)

[Unreleased]: https://github.com/AleksandrSl/intellij-luau/compare/v0.1.0...HEAD
[0.1.0]: https://github.com/AleksandrSl/intellij-luau/compare/v0.1.0-eap...v0.1.0
[0.1.0-eap]: https://github.com/AleksandrSl/intellij-luau/commits/v0.1.0-eap
