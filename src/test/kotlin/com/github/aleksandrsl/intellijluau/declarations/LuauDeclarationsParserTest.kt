package com.github.aleksandrsl.intellijluau.declarations

import com.intellij.testFramework.assertInstanceOf
import com.intellij.testFramework.utils.module.assertEqualsUnordered
import junit.framework.TestCase

class LuauDeclarationsParserTest : TestCase() {
    private val parser: LuauDeclarationsParser = LuauDeclarationsParser()

    fun `test parser processes valid luau file`() {
        val declarations = javaClass.classLoader.getResource("declarations/globalTypes.RobloxScriptSecurity.d.luau")
            ?.let { parser.parse(it.path) }

        assertNotNull(declarations)
        assertTrue(declarations!!.isNotEmpty())
    }

    fun `test parser processes type alias declaration`() {
        val declarations = parser.parseContent("""type ProtectedString = string""")

        val typeAliasDeclaration = declarations.values.firstOrNull { it is LuauDeclaration.TypeAlias }
        assertNotNull(typeAliasDeclaration)
        assertTrue(typeAliasDeclaration is LuauDeclaration.TypeAlias)
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

        val globalObjectDeclaration = declarations.values.firstOrNull { it is LuauDeclaration.GlobalObject }
        assertNotNull(globalObjectDeclaration)
        assertTrue(globalObjectDeclaration is LuauDeclaration.GlobalObject)
    }

    fun `test parser processes function declaration`() {
        val declarations = parser.parseContent("""declare function collectgarbage(mode: "count"): number""")

        // Then
        val functionDeclaration = declarations.values.firstOrNull { it is LuauDeclaration.Function }
        assertNotNull(functionDeclaration)
        assertTrue(functionDeclaration is LuauDeclaration.Function)
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
        val classDeclaration = declarations.values.firstOrNull { it is LuauDeclaration.Class }
        assertNotNull(classDeclaration)
        assertTrue(classDeclaration is LuauDeclaration.Class)
        assertInstanceOf<LuauDeclaration.Class>(classDeclaration)
        assertEquals((classDeclaration as LuauDeclaration.Class).name, "OverlapParams")
        assertEqualsUnordered(
            listOf(
                "BruteForceAllSlow",
                "CollisionGroup",
                "FilterDescendantsInstances",
                "FilterType",
                "MaxParts",
                "RespectCanCollide"
            ),
            classDeclaration.properties.keys
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

        val enumClassDeclaration = declarations.values.firstOrNull { it is LuauDeclaration.EnumClass }
        assertNotNull(enumClassDeclaration)
        assertTrue(enumClassDeclaration is LuauDeclaration.EnumClass)
        assertEquals((enumClassDeclaration as LuauDeclaration.EnumClass).name, "TextXAlignment")
        assertEquals("Enum", enumClassDeclaration.extends)
        assertEqualsUnordered(listOf("Center", "Left", "Right"), enumClassDeclaration.values)
    }

    fun `test parser correctly identifies declaration types`() {
        val declarations = javaClass.classLoader.getResource("declarations/globalTypes.RobloxScriptSecurity.d.luau")
            ?.let { parser.parse(it.path) }


        assertNotNull(declarations)
        val stats = declarations!!.values.groupBy { declaration ->
            when (declaration) {
                is LuauDeclaration.TypeAlias -> "typeAlias"
                is LuauDeclaration.GlobalObject -> "globalObject"
                is LuauDeclaration.Function -> "function"
                is LuauDeclaration.Class -> "class"
                is LuauDeclaration.EnumClass -> "enumClass"
                else -> "unknown"
            }
        }

        assertTrue(stats.isNotEmpty())
    }


    fun `test parser handles debug global object correctly`() {
        val declarations = javaClass.classLoader.getResource("declarations/globalTypes.RobloxScriptSecurity.d.luau")
            ?.let { parser.parse(it.path) }

        assertNotNull(declarations)
        val debugDeclaration = declarations!!["debug"]
        assertNotNull(debugDeclaration)
        assertTrue(debugDeclaration is LuauDeclaration.GlobalObject)
    }


    fun `test parser performance is within acceptable range`() {
        val testFilePath = javaClass.classLoader.getResource("declarations/globalTypes.RobloxScriptSecurity.d.luau")

        assertNotNull(testFilePath)
        val startTime = System.currentTimeMillis()
        val declarations = parser.parse(testFilePath!!.path)
        val duration = System.currentTimeMillis() - startTime

        assertTrue(duration < 500)
        assertTrue(declarations.isNotEmpty())
    }
}
