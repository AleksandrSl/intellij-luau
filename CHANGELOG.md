<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# intellij-luau Changelog

## [Unreleased]

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

### Changed

- LSP real version when using latest is no longer saved in persisted settings
- Use an LSP version from the LSP server info if available

## [0.1.0-eap] - 2025-06-12

### Added

- Luau LSP integration
- Formatting with StyLua
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)

[Unreleased]: https://github.com/AleksandrSl/intellij-luau/compare/v0.1.0-eap...HEAD
[0.1.0-eap]: https://github.com/AleksandrSl/intellij-luau/commits/v0.1.0-eap
