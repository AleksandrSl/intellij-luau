package com.github.aleksandrsl.intellijluau.declarations

import java.io.File

/**
 * Data classes to represent different types of Luau declarations
 */
sealed class LuauDeclaration {
    data class TypeAlias(val name: String, val type: String) : LuauDeclaration()

    data class GlobalObject(
        val name: String,
        val type: String? = null,
        val properties: Map<String, Any>
    ) : LuauDeclaration()

    data class Function(
        val name: String,
        val parameters: List<Parameter>,
        val returnType: String
    ) : LuauDeclaration()

    data class Class(
        val name: String,
        val extends: String? = null,
        val properties: Map<String, String> = mapOf(),
        val methods: Map<String, Function> = mapOf(),
        val isInstance: Boolean
    ) : LuauDeclaration()

    data class EnumClass(
        val name: String,
        val extends: String? = null,
        val values: List<String> = listOf()
    ) : LuauDeclaration()

    data class Parameter(val name: String, val type: String, val optional: Boolean = false)

    /**
     * Convert the declaration to a map representation as required by the issue description
     */
    fun toMap(): Map<String, Any> {
        return when (this) {
            is TypeAlias -> mapOf(
                "type" to "typeAlias",
                "data" to type
            )

            is GlobalObject -> mapOf(
                "type" to "globalObject",
                "data" to properties
            )

            is Function -> mapOf(
                "type" to "function",
                "data" to mapOf(
                    "parameters" to parameters.map {
                        mapOf(
                            "name" to it.name,
                            "type" to it.type,
                            "optional" to it.optional
                        )
                    },
                    "returnType" to returnType
                )
            )

            is Class -> mapOf(
                "type" to "class",
                "data" to mapOf(
                    "extends" to (extends ?: ""),
                    "properties" to properties,
                    "methods" to methods.mapValues { it.value.toMap()["data"] as Map<String, Any> }
                )
            )

            is EnumClass -> mapOf(
                "type" to "enumClass",
                "data" to mapOf(
                    "extends" to (extends ?: ""),
                    "values" to values
                )
            )
        }
    }
}

val enumClassRegex = Regex("declare\\sclass\\sEnum(\\w+)_INTERNAL\\s+extends\\s+(\\w+)")
val enumClassItemRegex = Regex("declare\\sclass\\sEnum(\\w+)\\s+extends\\s+(\\w+)\\s+end")
val globalDeclarationRegex = Regex("declare\\s+(\\w+):\\s*\\{")
val aliasGlobalDeclarationRegex = Regex("declare\\s+(\\w+):\\s*(\\w+)\\s*")
val classDeclarationRegex = Regex("declare class (\\w+)(?:\\s+extends\\s+(\\w+))?")
val typeAliasRegex = Regex("type\\s+(\\w+)\\s*=\\s*(.+)")
val functionRegex = Regex("declare function (\\w+)\\(([^)]*)\\)(?::\\s*(.+))?")
val propertyRegex = Regex("(\\w+):\\s*(.+?),?$")
val methodRegex = Regex("function\\s+(\\w+)\\(([^)]*)\\)(?::\\s*(.+))?")


data class LuauDeclarations(
    val typeAliases: Map<String, LuauDeclaration.TypeAlias>,
    val globalObjects: Map<String, LuauDeclaration.GlobalObject>,
    val functions: Map<String, LuauDeclaration.Function>,
    val classes: Map<String, LuauDeclaration.Class>,
    val enums: Map<String, LuauDeclaration.EnumClass>
)

/**
 * Parser for Luau declaration files (.d.luau)
 */
class LuauDeclarationsParser {
    /**
     * Parse a Luau declaration file into a map of declarations
     * @param filePath Path to the Luau declaration file
     * @return Map of declaration name to a declaration object
     */
    fun parse(filePath: String): LuauDeclarations {
        val file = File(filePath)
        if (!file.exists()) {
            throw IllegalArgumentException("File does not exist: $filePath")
        }

        val content = file.readText()
        return parseContent(content)
    }

    /**
     * Parse the content of a Luau declaration file
     * @param content Content of the Luau declaration file
     * @return Map of declaration name to a declaration object
     */
    fun parseContent(content: String): LuauDeclarations {
        val typeAliases = mutableMapOf<String, LuauDeclaration.TypeAlias>()
        val globalObjects = mutableMapOf<String, LuauDeclaration.GlobalObject>()
        val functions = mutableMapOf<String, LuauDeclaration.Function>()
        val classes = mutableMapOf<String, LuauDeclaration.Class>()
        val enums = mutableMapOf<String, LuauDeclaration.EnumClass>()

        // Remove comments
        val contentWithoutComments = removeComments(content)

        // Split content into lines
        val lines = contentWithoutComments.lines()

        var i = 0
        while (i < lines.size) {
            val line = lines[i].trim()

            // Skip empty lines
            if (line.isEmpty()) {
                i++
                continue
            }

            when {
                // Type alias
                line.startsWith("type ") -> {
                    val typeDeclaration = parseTypeAlias(line)
                    typeDeclaration?.let { typeAliases[it.name] = it }
                    i++
                }

                // TODO (AleksandrSl 08/04/2025): I can use type ENUM_LIST = { to remap enum names correctly
                // Enum class declaration
                line.matches(enumClassRegex) -> {
                    val (enumClassDeclaration, linesConsumed) = parseEnumClass(lines.subList(i, lines.size))
                    enumClassDeclaration?.let { enums[it.name] = it }
                    i += linesConsumed
                }

                line.matches(enumClassItemRegex) -> {
                    // Skip them since I'm not interested
                    i++
                }

                // Function declaration
                line.startsWith("declare function ") -> {
                    val functionDeclaration = parseFunction(line)
                    functionDeclaration?.let { functions[it.name] = it }
                    i++
                }

                // Class declaration
                line.startsWith("declare class ") -> {
                    val (classDeclaration, linesConsumed) = parseClass(lines.subList(i, lines.size))
                    classDeclaration?.let { classes[it.name] = it }
                    i += linesConsumed
                }

                // The less specific should go last. why AI doesn't know this
                // Global object declaration
                line.startsWith("declare ") -> {
                    val (objectDeclaration, linesConsumed) = parseGlobalObject(lines.subList(i, lines.size))
                    objectDeclaration?.let { globalObjects[it.name] = it }
                    i += linesConsumed
                }

                else -> i++
            }
        }

        // Resolve inheritance to mark classes inheriting from Instance
        // I don't know why, I had a reason before that these classes cannot be instantiated,
        // but now I think that all classes are not instantiable
        classes.values.forEach { declaration ->
            declaration.extends?.let {
                // Probably we can inherit not only classes?
                var currentParent = classes[it]
                while (currentParent != null) {
                    if (currentParent.isInstance) {
                        classes[declaration.name] = declaration.copy(isInstance = true)
                        break
                    }
                    currentParent =
                        currentParent.extends?.let { extends -> classes[extends] }
                }
            }
        }

        return LuauDeclarations(
            typeAliases = typeAliases,
            globalObjects = globalObjects,
            functions = functions,
            classes = classes,
            enums = enums
        )
    }

    /**
     * Remove comments from Luau content
     */
    private fun removeComments(content: String): String {
        // Remove single-line comments
        val withoutSingleLineComments = content.lines().joinToString("\n") { line ->
            val commentIndex = line.indexOf("--")
            if (commentIndex >= 0) line.take(commentIndex) else line
        }

        // TODO: Handle multi-line comments if needed

        return withoutSingleLineComments
    }

    /**
     * Parse a type alias declaration
     * Example: type Content = string
     */
    private fun parseTypeAlias(line: String): LuauDeclaration.TypeAlias? {
        val matchResult = typeAliasRegex.find(line) ?: return null

        val (name, type) = matchResult.destructured
        return LuauDeclaration.TypeAlias(name, type.trim())
    }

    /**
     * Parse a global object declaration
     * Example: declare debug: { ... } or declare game: DataModel
     * The thing after the `:` is the type.
     * Until now, there are now multiline intersections helpfully.
     */
    private fun parseGlobalObject(lines: List<String>): Pair<LuauDeclaration.GlobalObject?, Int> {
        val firstLine = lines[0].trim()
        val aliasMatch = aliasGlobalDeclarationRegex.find(firstLine)
        if (aliasMatch != null) {
            return Pair(LuauDeclaration.GlobalObject(aliasMatch.groupValues[1], aliasMatch.groupValues[2], mapOf()), 1)
        }

        val matchResult = globalDeclarationRegex.find(firstLine) ?: return Pair(null, 1)

        val name = matchResult.groupValues[1]
        val properties = mutableMapOf<String, Any>()

        var i = 1
        var braceCount = 1 // We've already seen one opening brace

        while (i < lines.size && braceCount > 0) {
            val line = lines[i].trim()

            // Count braces to track nesting
            braceCount += line.count { it == '{' }
            braceCount -= line.count { it == '}' }

            // Parse property if this is a property line
            if (line.contains(":") && !line.startsWith("{") && !line.startsWith("}")) {
                val propertyMatch = propertyRegex.find(line)

                if (propertyMatch != null) {
                    val (propertyName, propertyType) = propertyMatch.destructured

                    // Check if this is a nested object
                    if (propertyType.trim().startsWith("{")) {
                        // This is a nested object, we'll parse it recursively
                        val nestedProperties = parseNestedObject(lines.subList(i, lines.size))
                        properties[propertyName] = nestedProperties

                        // Skip lines that were part of the nested object
                        var nestedBraceCount = 1
                        var j = i + 1
                        while (j < lines.size && nestedBraceCount > 0) {
                            val nestedLine = lines[j].trim()
                            nestedBraceCount += nestedLine.count { it == '{' }
                            nestedBraceCount -= nestedLine.count { it == '}' }
                            j++
                            if (nestedBraceCount == 0) break
                        }
                        i = j - 1
                    } else {
                        // Regular property
                        properties[propertyName] = propertyType.trim()
                    }
                }
            }

            i++

            // Break if we've closed all braces
            if (braceCount == 0) {
                break
            }
        }

        return Pair(LuauDeclaration.GlobalObject(name, properties = properties), i)
    }

    /**
     * Parse a nested object within a global object declaration
     */
    private fun parseNestedObject(lines: List<String>): Map<String, Any> {
        val properties = mutableMapOf<String, Any>()
        var braceCount = 1 // We start after the opening brace
        var i = 1

        while (i < lines.size && braceCount > 0) {
            val line = lines[i].trim()

            // Count braces to track nesting
            braceCount += line.count { it == '{' }
            braceCount -= line.count { it == '}' }

            // Parse property if this is a property line
            if (line.contains(":") && !line.startsWith("{") && !line.startsWith("}")) {
                val propertyMatch = propertyRegex.find(line)

                if (propertyMatch != null) {
                    val (propertyName, propertyType) = propertyMatch.destructured

                    // Check if this is a nested object
                    if (propertyType.trim().startsWith("{")) {
                        // This is a nested object, we'll parse it recursively
                        val nestedProperties = parseNestedObject(lines.subList(i, lines.size))
                        properties[propertyName] = nestedProperties

                        // Skip lines that were part of the nested object
                        var nestedBraceCount = 1
                        var j = i + 1
                        while (j < lines.size && nestedBraceCount > 0) {
                            val nestedLine = lines[j].trim()
                            nestedBraceCount += nestedLine.count { it == '{' }
                            nestedBraceCount -= nestedLine.count { it == '}' }
                            j++
                            if (nestedBraceCount == 0) break
                        }
                        i = j - 1
                    } else {
                        // Regular property
                        properties[propertyName] = propertyType.trim()
                    }
                }
            }

            i++

            // Break if we've closed all braces
            if (braceCount == 0) {
                break
            }
        }

        return properties
    }

    /**
     * Parse a function declaration
     * Example: declare function collectgarbage(mode: "count"): number
     */
    private fun parseFunction(line: String): LuauDeclaration.Function? {
        val matchResult = functionRegex.find(line) ?: return null

        val (name, paramsStr, returnType) = matchResult.destructured

        // Parse parameters
        val parameters = if (paramsStr.isNotBlank()) {
            paramsStr.split(",").map { paramStr ->
                val paramParts = paramStr.trim().split(":")
                val paramName = paramParts[0].trim()
                val paramType = if (paramParts.size > 1) paramParts[1].trim() else "any"
                val isOptional = paramName.endsWith("?") || paramType.endsWith("?")

                val cleanName = paramName.removeSuffix("?")
                val cleanType = paramType.removeSuffix("?")

                LuauDeclaration.Parameter(cleanName, cleanType, isOptional)
            }
        } else {
            emptyList()
        }

        return LuauDeclaration.Function(name, parameters, returnType.ifBlank { "void" })
    }

    /**
     * Parse a class declaration
     * Example: declare class EnumItem
     *     Name: string
     *     Value: number
     *     EnumType: Enum
     *     function IsA(self, enumName: string): boolean
     * end
     */
    private fun parseClass(lines: List<String>): Pair<LuauDeclaration.Class?, Int> {
        val firstLine = lines[0].trim()
        val matchResult = classDeclarationRegex.find(firstLine) ?: return Pair(null, 1)

        val (name, extends) = matchResult.destructured
        val properties = mutableMapOf<String, String>()
        val methods = mutableMapOf<String, LuauDeclaration.Function>()

        var i = 1
        while (i < lines.size) {
            val line = lines[i].trim()

            if (line == "end") {
                i++
                break
            }

            when {
                // Property
                line.contains(":") && !line.startsWith("function") -> {
                    val propertyParts = line.split(":", limit = 2)
                    if (propertyParts.size == 2) {
                        val propertyName = propertyParts[0].trim()
                        val propertyType = propertyParts[1].trim()
                        properties[propertyName] = propertyType
                    }
                }

                // Method
                line.startsWith("function") -> {
                    val methodMatch = methodRegex.find(line)

                    if (methodMatch != null) {
                        val (methodName, paramsStr, returnType) = methodMatch.destructured

                        // Parse parameters
                        val parameters = if (paramsStr.isNotBlank()) {
                            paramsStr.split(",").map { paramStr ->
                                val paramParts = paramStr.trim().split(":")
                                val paramName = paramParts[0].trim()
                                val paramType = if (paramParts.size > 1) paramParts[1].trim() else "any"
                                val isOptional = paramName.endsWith("?") || paramType.endsWith("?")

                                val cleanName = paramName.removeSuffix("?")
                                val cleanType = paramType.removeSuffix("?")

                                LuauDeclaration.Parameter(cleanName, cleanType, isOptional)
                            }
                        } else {
                            emptyList()
                        }

                        methods[methodName] = LuauDeclaration.Function(
                            methodName,
                            parameters,
                            returnType.ifBlank { "void" }
                        )
                    }
                }
            }

            i++
        }

        return Pair(
            LuauDeclaration.Class(
                name,
                extends.ifBlank { null },
                properties,
                methods,
                // These are not accessible as references in code. You can't write local a = Plugin.
                // Plugin doesn't exist. Probably they are to receive as a service or only as a type.
                isInstance = extends == "Instance" || name == "Instance"
            ),
            i
        )
    }

    /**
     * Parse an enum class declaration
     * Example: declare class EnumAccessModifierType extends EnumItem end
     * declare class EnumAccessModifierType_INTERNAL extends Enum
     *     Allow: EnumAccessModifierType
     *     Deny: EnumAccessModifierType
     * end
     */
    private fun parseEnumClass(lines: List<String>): Pair<LuauDeclaration.EnumClass?, Int> {
        val firstLine = lines[0].trim()
        val matchResult = enumClassRegex.find(firstLine) ?: return Pair(null, 1)

        val (name, extends) = matchResult.destructured

        // Ignore the simple one-line declarations since I don't understand their purpose
//        // If this is a simple enum class declaration (ends with "end" on the same line)
//        if (firstLine.endsWith(" end")) {
//            return Pair(
//                LuauDeclaration.EnumClass(
//                    name,
//                    extends.ifBlank { null }
//                ),
//                1
//            )
//        }

        // Otherwise, parse the enum values
        val values = mutableListOf<String>()

        var i = 1
        while (i < lines.size) {
            val line = lines[i].trim()

            if (line == "end") {
                i++
                break
            }

            // Enum value
            if (line.contains(":")) {
                val valueParts = line.split(":", limit = 2)
                if (valueParts.size == 2) {
                    val valueName = valueParts[0].trim()
                    values.add(valueName)
                }
            }

            i++
        }

        return Pair(
            LuauDeclaration.EnumClass(
                name,
                extends.ifBlank { null },
                values
            ),
            i
        )
    }
}
