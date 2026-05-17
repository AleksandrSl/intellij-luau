# Luau LSP, Studio Companion Plugin, and Roblox Studio Script Sync Notes

## Overview

Roblox Studio Script Sync, the Luau Language Server, and the Luau Language Server Studio Companion Plugin are separate systems that can work together.

The key point:

> Roblox Studio Script Sync does **not** use Luau LSP or `GET /get-file-paths` on its own.

`GET /get-file-paths` is only used by the **Luau Language Server Studio Companion Plugin** when it wants to use Roblox Studio Script Sync’s file-to-instance mapping to enrich Luau LSP’s internal DataModel/sourcemap state.

---

## Components

### Roblox Studio Script Sync

Studio Script Sync synchronizes Roblox script instances with files on disk.

Conceptually:
```
text
Roblox Studio Script Instance <-> local .luau/.lua file
```
It does not require:

- Luau LSP
- `sourcemap.json`
- `GET /get-file-paths`
- `POST /full`
- `POST /clear`
- `$/plugin/full`
- `$/plugin/clear`

Script Sync can work independently.

---

### Luau Language Server

Luau LSP provides editor features such as:

- diagnostics
- autocomplete
- hover
- go to definition
- require resolution
- DataModel-aware typing
- `script` / `game` / `workspace` intelligence

For Roblox DataModel-aware features, Luau LSP needs to know how files map to the Roblox DataModel.

This mapping can come from:

1. Rojo sourcemap
2. manually generated `sourcemap.json`
3. Studio Companion Plugin information
4. Studio Companion Plugin + Studio Script Sync `FilePaths`

---

### Luau Language Server Studio Companion Plugin

The Studio Companion Plugin runs inside Roblox Studio and sends DataModel information to the editor’s local HTTP server.

The editor then forwards that information to Luau LSP through custom LSP notifications.

The editor-side HTTP server exposes:
```
text
GET  /get-file-paths
POST /full
POST /clear
```
The LSP notifications are:
```
text
$/plugin/full
$/plugin/clear
```
---

## What `GET /get-file-paths` is for

`GET /get-file-paths` is used by the Studio Companion Plugin, not by Studio Script Sync directly.

The purpose is to let the Studio Companion Plugin ask the editor:
```
text
Which local Luau files exist in this workspace?
```
The editor responds:
```
json
{
"files": [
"/absolute/path/to/Foo.luau",
"/absolute/path/to/Bar.server.luau"
]
}
```
Then, inside Studio, the Companion Plugin can ask Studio Script Sync:
```
text
Given this local file path, which Studio Instance is synced to it?
```
This produces a mapping:
```
text
/path/to/Foo.luau -> game.ReplicatedStorage.Foo
/path/to/Bar.server.luau -> game.ServerScriptService.Bar
```
The plugin then includes those file paths in the DataModel tree sent to Luau LSP.

---

## Full Studio Script Sync + Luau LSP flow
```
text
IntelliJ / VSCode HTTP server
^
| GET /get-file-paths
|
Studio Companion Plugin
|
| Uses InstanceFileSyncService.GetSyncedInstance(filePath)
v
Maps local files -> Studio Instances
|
| POST /full { tree: ... FilePaths: [...] ... }
v
Editor plugin
|
| LSP notification $/plugin/full
v
Luau LSP
|
| hydrates internal sourcemap/DataModel tree
v
DataModel-aware autocomplete, require resolution, go to definition, etc.
```
---

## Shape of `GET /get-file-paths`

Request:
```
http
GET /get-file-paths
```
Response:
```
json
{
"files": [
"/absolute/path/to/SomeScript.luau",
"/absolute/path/to/AnotherScript.lua"
]
}
```
Important requirements:

- The response must be JSON.
- The top-level key must be `files`.
- Values should be filesystem paths, not `file://` URIs.
- Paths should be absolute.
- Include `.lua` and `.luau`.
- Paths should match what Studio Script Sync knows.

Good:
```
json
{
"files": ["/Users/me/project/src/Foo.luau"]
}
```
Bad:
```
json
{
"uris": ["file:///Users/me/project/src/Foo.luau"]
}
```
---

## Shape of `POST /full`

The Companion Plugin sends:
```
http
POST /full
Content-Type: application/json
```
Body:
```
json
{
"tree": {
"Name": "game",
"ClassName": "DataModel",
"FilePaths": [],
"Children": [
{
"Name": "ReplicatedStorage",
"ClassName": "ReplicatedStorage",
"FilePaths": [],
"Children": [
{
"Name": "SharedModule",
"ClassName": "ModuleScript",
"FilePaths": [
"/absolute/path/to/SharedModule.luau"
],
"Children": []
}
]
}
]
}
}
```
The editor should forward the inner `tree` directly to Luau LSP:
```
json
{
"jsonrpc": "2.0",
"method": "$/plugin/full",
"params": {
"Name": "game",
"ClassName": "DataModel",
"Children": []
}
}
```
Do not wrap it again as `{ "tree": ... }`.

---

## Shape of `POST /clear`

The Companion Plugin sends:
```
http
POST /clear
```
The editor should forward:
```
json
{
"jsonrpc": "2.0",
"method": "$/plugin/clear"
}
```
No params are required.

---

## When does the Companion Plugin call `GET /get-file-paths`?

The Companion Plugin calls `GET /get-file-paths` whenever it is about to send a full DataModel update.

Flow:
```
text
sendFullDMInfo()
-> getFilePaths()
-> HTTP GET /get-file-paths
-> map file paths to synced Studio Instances
-> encodeAll(filePaths)
-> HTTP POST /full
```
So `GET /get-file-paths` normally happens immediately before `POST /full`.

### Triggers

#### 1. Initial connection

When the Studio Companion Plugin connects to the editor server:
```
text
GET /get-file-paths
POST /full
```
#### 2. Tracked instance changes

When an included Studio instance changes, the plugin queues a debounced full update.

Examples:

- instance renamed
- instance moved/reparented
- instance added
- instance removed
- hierarchy changed

Expected sequence:
```
text
GET /get-file-paths
POST /full
```
#### 3. Studio Script Sync status changes

The plugin listens for Studio Script Sync status changes.

When sync status changes for an included instance:
```
text
GET /get-file-paths
POST /full
```
#### 4. Auto-connect after Script Sync activity

If the plugin is disconnected but detects Script Sync activity, it may auto-connect. If connection succeeds:
```
text
GET /get-file-paths
POST /full
```
---

## Important distinction: old Studio Plugin flow vs new Script Sync file path flow

The old Companion Plugin flow could provide DataModel autocomplete even for unsaved/not-on-disk instances.

That proves the DataModel sync path works.

The new `GET /get-file-paths` flow adds:
```
text
DataModel Instance <-> local file path
```
This is needed for file-backed features.

| Feature | Old plugin flow | New `get-file-paths` / Script Sync flow |
|---|---:|---:|
| Autocomplete unsaved Studio instances | Yes | Yes |
| See DataModel children | Yes | Yes |
| Know which local file backs a synced script | No | Yes |
| Go to definition from DataModel require to file | Limited / no | Yes |
| Resolve `require(script.Parent.Module)` to synced file | Limited / no | Yes |
| Include `filePaths` in generated sourcemap | No | Yes |

---

## `sourcemap.enabled` vs `sourcemap.autogenerate`

### `sourcemap.enabled`

This is needed for sourcemap/DataModel features.

For Studio Companion + Script Sync support, use:
```
json
{
"luau-lsp.sourcemap.enabled": true
}
```
### `sourcemap.autogenerate`

This is **not required** for Studio Companion + Script Sync data to be used in memory.

From the server-side flow:
```
text
$/plugin/full
-> hydrateSourcemapWithPluginInfo()
-> writePathsToMap()
-> updateSourcemapTypes()
```
The in-memory update and LSP features happen regardless of `autogenerate`.

`autogenerate` only gates whether Luau LSP writes the hydrated sourcemap back to disk.

Conceptually:
```
text
if sourcemap.enabled && sourcemap.autogenerate:
write hydrated sourcemap.json
```
So for a clean Script Sync test:
```
json
{
"luau-lsp.sourcemap.enabled": true,
"luau-lsp.sourcemap.autogenerate": false,
"luau-lsp.studioPlugin.enabled": true
}
```
This allows in-memory DataModel/file path support without requiring disk sourcemap generation.

---

## VSCode-specific behavior

In VSCode, `luau-lsp.sourcemap.autogenerate: true` also causes the VSCode extension to run a sourcemap generator.

By default, that generator is:
```
shell
rojo sourcemap ...
```
unless the user configured:
```
json
{
"luau-lsp.sourcemap.generatorCommand": "custom command"
}
```
Therefore, in VSCode:
```
json
{
"luau-lsp.sourcemap.autogenerate": true
}
```
can mean both:

1. VSCode client runs Rojo/custom sourcemap generation.
2. Luau LSP may persist plugin-hydrated sourcemap data.

For IntelliJ, it is better to avoid coupling these concepts too tightly.

---

## Recommended IntelliJ plugin modes

### 1. Rojo mode

Use when the project has a Rojo project file.

Behavior:
```
text
IntelliJ runs rojo sourcemap --watch
Luau LSP reads sourcemap.json
Studio Plugin may optionally augment DataModel info
```
### 2. Studio Script Sync mode

Use when the project relies on Studio Script Sync.

Behavior:
```
text
IntelliJ does not run Rojo
IntelliJ exposes GET /get-file-paths
Studio Companion Plugin maps paths through Script Sync
Studio Companion Plugin sends FilePaths in POST /full
Luau LSP uses the mapping in memory
```
Recommended LSP settings:
```
json
{
"luau-lsp.sourcemap.enabled": true,
"luau-lsp.sourcemap.autogenerate": false,
"luau-lsp.studioPlugin.enabled": true
}
```
### 3. Manual/custom sourcemap mode

Use when the user owns `sourcemap.json`.

Behavior:
```
text
User/custom tool writes sourcemap.json
Luau LSP watches/reads sourcemap.json
```
---

## How to test `GET /get-file-paths`

### Test A: endpoint unit test

Start the IntelliJ HTTP server and run:
```
shell
curl http://localhost:3667/get-file-paths
```
Expected:
```
json
{
"files": [
"/absolute/path/to/File.luau"
]
}
```
Pass conditions:

- HTTP 200
- valid JSON
- top-level `files`
- absolute paths
- no `file://` URIs

---

### Test B: Companion Plugin calls endpoint

1. Start IntelliJ HTTP server.
2. Start Luau LSP.
3. Open Roblox Studio.
4. Enable/connect Luau LSP Companion Plugin.
5. Watch IntelliJ logs.

Expected:
```
text
GET /get-file-paths
POST /full
```
---

### Test C: Studio maps paths to instances

Inspect/log incoming `POST /full`.

Expected script nodes contain:
```
json
"FilePaths": [
"/absolute/path/to/synced/script.luau"
]
```
If `GET /get-file-paths` is called but `FilePaths` are empty, the editor endpoint works, but Studio Script Sync did not map those paths to instances.

Common causes:

- paths are not absolute
- paths are URI strings instead of filesystem paths
- paths do not exactly match Studio Script Sync’s known paths
- Script Sync is not active
- path casing mismatch
- symlink/realpath mismatch
- returned files are outside the synced folder

---

### Test D: LSP receives tree

Log outgoing LSP notifications.

Expected:
```
text
$/plugin/full
```
with `FilePaths` present in the params.

---

### Test E: LSP feature test

Use a feature that requires file mapping, not just DataModel autocomplete.

Good tests:

- Go to definition from a DataModel-based `require(...)`
- Resolve `require(script.Parent.SomeModule)`
- Open synced local file from require target
- Verify `script` type corresponds to the synced Studio instance

Example:
```
luau
local ReplicatedStorage = game:GetService("ReplicatedStorage")
local SharedModule = require(ReplicatedStorage.SharedModule)

SharedModule.
```
Best proof:
```
text
Go to definition on the require target opens the correct synced local file.
```
---

## Troubleshooting

### `GET /get-file-paths` is never called

Likely causes:

- Companion Plugin is old.
- Studio Script Sync APIs are unavailable.
- Plugin connected to a different host/port.
- Another editor/server is receiving the request.
- The Studio Plugin is not actually connected.
- The request is blocked by Studio HTTP settings.

### `GET /get-file-paths` is called, but `POST /full` has no `FilePaths`

Likely causes:

- paths returned by editor are wrong
- paths are not absolute
- paths are URI strings
- paths do not match Script Sync
- Script Sync has not completed linking
- path casing or symlink mismatch

### `POST /full` has `FilePaths`, but LSP behavior does not work

Likely causes:

- editor forwards `{ tree: ... }` instead of the tree directly
- `sourcemap.enabled` is false
- Roblox platform/type definitions are not configured
- wrong workspace root
- old Luau LSP version
- path normalization mismatch

---

## Key conclusions

1. Studio Script Sync does not use Luau LSP on its own.
2. `GET /get-file-paths` is called by the Luau LSP Studio Companion Plugin.
3. The endpoint is needed only for Luau LSP to consume Studio Script Sync file-to-instance mappings.
4. The Companion Plugin calls `GET /get-file-paths` before sending full DataModel updates.
5. `sourcemap.autogenerate` is not required for in-memory Studio Companion + Script Sync support.
6. `sourcemap.autogenerate` is only needed if Luau LSP should write the hydrated sourcemap to disk.
7. In VSCode, `autogenerate: true` also causes Rojo/custom generator execution.
8. For IntelliJ Script Sync support, a clean setup is:
```
json
{
"luau-lsp.sourcemap.enabled": true,
"luau-lsp.sourcemap.autogenerate": false,
"luau-lsp.studioPlugin.enabled": true
}
```
9. The most important IntelliJ implementation requirement is the local HTTP bridge:
```
text
GET  /get-file-paths
POST /full
POST /clear
```

```
```markdown
# Luau LSP, Studio Companion Plugin, and Roblox Studio Script Sync Notes

## Overview

Roblox Studio Script Sync, the Luau Language Server, and the Luau Language Server Studio Companion Plugin are separate systems that can work together.

The key point:

> Roblox Studio Script Sync does **not** use Luau LSP or `GET /get-file-paths` on its own.

`GET /get-file-paths` is only used by the **Luau Language Server Studio Companion Plugin** when it wants to use Roblox Studio Script Sync’s file-to-instance mapping to enrich Luau LSP’s internal DataModel/sourcemap state.

---

## Components

### Roblox Studio Script Sync

Studio Script Sync synchronizes Roblox script instances with files on disk.

Conceptually:
```
text
Roblox Studio Script Instance <-> local .luau/.lua file
```
It does not require:

- Luau LSP
- `sourcemap.json`
- `GET /get-file-paths`
- `POST /full`
- `POST /clear`
- `$/plugin/full`
- `$/plugin/clear`

Script Sync can work independently.

---

### Luau Language Server

Luau LSP provides editor features such as:

- diagnostics
- autocomplete
- hover
- go to definition
- require resolution
- DataModel-aware typing
- `script` / `game` / `workspace` intelligence

For Roblox DataModel-aware features, Luau LSP needs to know how files map to the Roblox DataModel.

This mapping can come from:

1. Rojo sourcemap
2. manually generated `sourcemap.json`
3. Studio Companion Plugin information
4. Studio Companion Plugin + Studio Script Sync `FilePaths`

---

### Luau Language Server Studio Companion Plugin

The Studio Companion Plugin runs inside Roblox Studio and sends DataModel information to the editor’s local HTTP server.

The editor then forwards that information to Luau LSP through custom LSP notifications.

The editor-side HTTP server exposes:
```
text
GET  /get-file-paths
POST /full
POST /clear
```
The LSP notifications are:
```
text
$/plugin/full
$/plugin/clear
```
---

## What `GET /get-file-paths` is for

`GET /get-file-paths` is used by the Studio Companion Plugin, not by Studio Script Sync directly.

The purpose is to let the Studio Companion Plugin ask the editor:
```
text
Which local Luau files exist in this workspace?
```
The editor responds:
```
json
{
  "files": [
    "/absolute/path/to/Foo.luau",
    "/absolute/path/to/Bar.server.luau"
  ]
}
```
Then, inside Studio, the Companion Plugin can ask Studio Script Sync:
```
text
Given this local file path, which Studio Instance is synced to it?
```
This produces a mapping:
```
text
/path/to/Foo.luau -> game.ReplicatedStorage.Foo
/path/to/Bar.server.luau -> game.ServerScriptService.Bar
```
The plugin then includes those file paths in the DataModel tree sent to Luau LSP.

---

## Full Studio Script Sync + Luau LSP flow
```
text
IntelliJ / VSCode HTTP server
        ^
        | GET /get-file-paths
        |
Studio Companion Plugin
        |
        | Uses InstanceFileSyncService.GetSyncedInstance(filePath)
        v
Maps local files -> Studio Instances
        |
        | POST /full { tree: ... FilePaths: [...] ... }
        v
Editor plugin
        |
        | LSP notification $/plugin/full
        v
Luau LSP
        |
        | hydrates internal sourcemap/DataModel tree
        v
DataModel-aware autocomplete, require resolution, go to definition, etc.
```
---

## Shape of `GET /get-file-paths`

Request:
```
http
GET /get-file-paths
```
Response:
```
json
{
  "files": [
    "/absolute/path/to/SomeScript.luau",
    "/absolute/path/to/AnotherScript.lua"
  ]
}
```
Important requirements:

- The response must be JSON.
- The top-level key must be `files`.
- Values should be filesystem paths, not `file://` URIs.
- Paths should be absolute.
- Include `.lua` and `.luau`.
- Paths should match what Studio Script Sync knows.

Good:
```
json
{
  "files": ["/Users/me/project/src/Foo.luau"]
}
```
Bad:
```
json
{
  "uris": ["file:///Users/me/project/src/Foo.luau"]
}
```
---

## Shape of `POST /full`

The Companion Plugin sends:
```
http
POST /full
Content-Type: application/json
```
Body:
```
json
{
  "tree": {
    "Name": "game",
    "ClassName": "DataModel",
    "FilePaths": [],
    "Children": [
      {
        "Name": "ReplicatedStorage",
        "ClassName": "ReplicatedStorage",
        "FilePaths": [],
        "Children": [
          {
            "Name": "SharedModule",
            "ClassName": "ModuleScript",
            "FilePaths": [
              "/absolute/path/to/SharedModule.luau"
            ],
            "Children": []
          }
        ]
      }
    ]
  }
}
```
The editor should forward the inner `tree` directly to Luau LSP:
```
json
{
  "jsonrpc": "2.0",
  "method": "$/plugin/full",
  "params": {
    "Name": "game",
    "ClassName": "DataModel",
    "Children": []
  }
}
```
Do not wrap it again as `{ "tree": ... }`.

---

## Shape of `POST /clear`

The Companion Plugin sends:
```
http
POST /clear
```
The editor should forward:
```
json
{
  "jsonrpc": "2.0",
  "method": "$/plugin/clear"
}
```
No params are required.

---

## When does the Companion Plugin call `GET /get-file-paths`?

The Companion Plugin calls `GET /get-file-paths` whenever it is about to send a full DataModel update.

Flow:
```
text
sendFullDMInfo()
  -> getFilePaths()
       -> HTTP GET /get-file-paths
       -> map file paths to synced Studio Instances
  -> encodeAll(filePaths)
  -> HTTP POST /full
```
So `GET /get-file-paths` normally happens immediately before `POST /full`.

### Triggers

#### 1. Initial connection

When the Studio Companion Plugin connects to the editor server:
```
text
GET /get-file-paths
POST /full
```
#### 2. Tracked instance changes

When an included Studio instance changes, the plugin queues a debounced full update.

Examples:

- instance renamed
- instance moved/reparented
- instance added
- instance removed
- hierarchy changed

Expected sequence:
```
text
GET /get-file-paths
POST /full
```
#### 3. Studio Script Sync status changes

The plugin listens for Studio Script Sync status changes.

When sync status changes for an included instance:
```
text
GET /get-file-paths
POST /full
```
#### 4. Auto-connect after Script Sync activity

If the plugin is disconnected but detects Script Sync activity, it may auto-connect. If connection succeeds:
```
text
GET /get-file-paths
POST /full
```
---

## Important distinction: old Studio Plugin flow vs new Script Sync file path flow

The old Companion Plugin flow could provide DataModel autocomplete even for unsaved/not-on-disk instances.

That proves the DataModel sync path works.

The new `GET /get-file-paths` flow adds:
```
text
DataModel Instance <-> local file path
```
This is needed for file-backed features.

| Feature | Old plugin flow | New `get-file-paths` / Script Sync flow |
|---|---:|---:|
| Autocomplete unsaved Studio instances | Yes | Yes |
| See DataModel children | Yes | Yes |
| Know which local file backs a synced script | No | Yes |
| Go to definition from DataModel require to file | Limited / no | Yes |
| Resolve `require(script.Parent.Module)` to synced file | Limited / no | Yes |
| Include `filePaths` in generated sourcemap | No | Yes |

---

## `sourcemap.enabled` vs `sourcemap.autogenerate`

### `sourcemap.enabled`

This is needed for sourcemap/DataModel features.

For Studio Companion + Script Sync support, use:
```
json
{
  "luau-lsp.sourcemap.enabled": true
}
```
### `sourcemap.autogenerate`

This is **not required** for Studio Companion + Script Sync data to be used in memory.

From the server-side flow:
```
text
$/plugin/full
  -> hydrateSourcemapWithPluginInfo()
  -> writePathsToMap()
  -> updateSourcemapTypes()
```
The in-memory update and LSP features happen regardless of `autogenerate`.

`autogenerate` only gates whether Luau LSP writes the hydrated sourcemap back to disk.

Conceptually:
```
text
if sourcemap.enabled && sourcemap.autogenerate:
    write hydrated sourcemap.json
```
So for a clean Script Sync test:
```
json
{
  "luau-lsp.sourcemap.enabled": true,
  "luau-lsp.sourcemap.autogenerate": false,
  "luau-lsp.studioPlugin.enabled": true
}
```
This allows in-memory DataModel/file path support without requiring disk sourcemap generation.

---

## VSCode-specific behavior

In VSCode, `luau-lsp.sourcemap.autogenerate: true` also causes the VSCode extension to run a sourcemap generator.

By default, that generator is:
```
shell
rojo sourcemap ...
```
unless the user configured:
```
json
{
  "luau-lsp.sourcemap.generatorCommand": "custom command"
}
```
Therefore, in VSCode:
```
json
{
  "luau-lsp.sourcemap.autogenerate": true
}
```
can mean both:

1. VSCode client runs Rojo/custom sourcemap generation.
2. Luau LSP may persist plugin-hydrated sourcemap data.

For IntelliJ, it is better to avoid coupling these concepts too tightly.

---

## Recommended IntelliJ plugin modes

### 1. Rojo mode

Use when the project has a Rojo project file.

Behavior:
```
text
IntelliJ runs rojo sourcemap --watch
Luau LSP reads sourcemap.json
Studio Plugin may optionally augment DataModel info
```
### 2. Studio Script Sync mode

Use when the project relies on Studio Script Sync.

Behavior:
```
text
IntelliJ does not run Rojo
IntelliJ exposes GET /get-file-paths
Studio Companion Plugin maps paths through Script Sync
Studio Companion Plugin sends FilePaths in POST /full
Luau LSP uses the mapping in memory
```
Recommended LSP settings:
```
json
{
  "luau-lsp.sourcemap.enabled": true,
  "luau-lsp.sourcemap.autogenerate": false,
  "luau-lsp.studioPlugin.enabled": true
}
```
### 3. Manual/custom sourcemap mode

Use when the user owns `sourcemap.json`.

Behavior:
```
text
User/custom tool writes sourcemap.json
Luau LSP watches/reads sourcemap.json
```
---

## How to test `GET /get-file-paths`

### Test A: endpoint unit test

Start the IntelliJ HTTP server and run:
```
shell
curl http://localhost:3667/get-file-paths
```
Expected:
```
json
{
  "files": [
    "/absolute/path/to/File.luau"
  ]
}
```
Pass conditions:

- HTTP 200
- valid JSON
- top-level `files`
- absolute paths
- no `file://` URIs

---

### Test B: Companion Plugin calls endpoint

1. Start IntelliJ HTTP server.
2. Start Luau LSP.
3. Open Roblox Studio.
4. Enable/connect Luau LSP Companion Plugin.
5. Watch IntelliJ logs.

Expected:
```
text
GET /get-file-paths
POST /full
```
---

### Test C: Studio maps paths to instances

Inspect/log incoming `POST /full`.

Expected script nodes contain:
```
json
"FilePaths": [
  "/absolute/path/to/synced/script.luau"
]
```
If `GET /get-file-paths` is called but `FilePaths` are empty, the editor endpoint works, but Studio Script Sync did not map those paths to instances.

Common causes:

- paths are not absolute
- paths are URI strings instead of filesystem paths
- paths do not exactly match Studio Script Sync’s known paths
- Script Sync is not active
- path casing mismatch
- symlink/realpath mismatch
- returned files are outside the synced folder

---

### Test D: LSP receives tree

Log outgoing LSP notifications.

Expected:
```
text
$/plugin/full
```
with `FilePaths` present in the params.

---

### Test E: LSP feature test

Use a feature that requires file mapping, not just DataModel autocomplete.

Good tests:

- Go to definition from a DataModel-based `require(...)`
- Resolve `require(script.Parent.SomeModule)`
- Open synced local file from require target
- Verify `script` type corresponds to the synced Studio instance

Example:
```
luau
local ReplicatedStorage = game:GetService("ReplicatedStorage")
local SharedModule = require(ReplicatedStorage.SharedModule)

SharedModule.
```
Best proof:
```
text
Go to definition on the require target opens the correct synced local file.
```
---

## Troubleshooting

### `GET /get-file-paths` is never called

Likely causes:

- Companion Plugin is old.
- Studio Script Sync APIs are unavailable.
- Plugin connected to a different host/port.
- Another editor/server is receiving the request.
- The Studio Plugin is not actually connected.
- The request is blocked by Studio HTTP settings.

### `GET /get-file-paths` is called, but `POST /full` has no `FilePaths`

Likely causes:

- paths returned by editor are wrong
- paths are not absolute
- paths are URI strings
- paths do not match Script Sync
- Script Sync has not completed linking
- path casing or symlink mismatch

### `POST /full` has `FilePaths`, but LSP behavior does not work

Likely causes:

- editor forwards `{ tree: ... }` instead of the tree directly
- `sourcemap.enabled` is false
- Roblox platform/type definitions are not configured
- wrong workspace root
- old Luau LSP version
- path normalization mismatch

---

## Key conclusions

1. Studio Script Sync does not use Luau LSP on its own.
2. `GET /get-file-paths` is called by the Luau LSP Studio Companion Plugin.
3. The endpoint is needed only for Luau LSP to consume Studio Script Sync file-to-instance mappings.
4. The Companion Plugin calls `GET /get-file-paths` before sending full DataModel updates.
5. `sourcemap.autogenerate` is not required for in-memory Studio Companion + Script Sync support.
6. `sourcemap.autogenerate` is only needed if Luau LSP should write the hydrated sourcemap to disk.
7. In VSCode, `autogenerate: true` also causes Rojo/custom generator execution.
8. For IntelliJ Script Sync support, a clean setup is:
```
json
{
  "luau-lsp.sourcemap.enabled": true,
  "luau-lsp.sourcemap.autogenerate": false,
  "luau-lsp.studioPlugin.enabled": true
}
```
9. The most important IntelliJ implementation requirement is the local HTTP bridge:
```
text
GET  /get-file-paths
POST /full
POST /clear
```
