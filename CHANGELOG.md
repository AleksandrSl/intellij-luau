<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# intellij-luau Changelog

## [Unreleased]

### Added

- Breadcrumbs navigation for functions, methods, and type declarations
- Restart lsp in all the projects using the latest version when an update is downloaded.
- `Update Roblox API Definitions` action, to refresh Roblox API definitions. They are also refreshed once a day automatically.
- Support platform and sourcemap configuration for LSP

### Fixed

- Do not restart LSP unless the settings are applied
- First incorrect start of the LSP in new projects
- Check for the rojo default project file. 

## [0.1.0-eap] - 2025-06-12

### Added

- Luau LSP integration
- Formatting with StyLua
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)

[Unreleased]: https://github.com/AleksandrSl/intellij-luau/compare/v0.1.0-eap...HEAD
[0.1.0-eap]: https://github.com/AleksandrSl/intellij-luau/commits/v0.1.0-eap
