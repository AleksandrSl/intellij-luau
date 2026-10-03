package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.tools.LspFlag
import com.github.aleksandrsl.intellijluau.tools.LspFlagType
import com.github.aleksandrsl.intellijluau.tools.parseShowFlagsOutput
import org.junit.Assert.assertEquals
import org.junit.Test

class ShowFlagsParserTest {
    @Test
    fun parsesBooleanAndIntegerFlags() {
        val flags = parseShowFlagsOutput(
            listOf(
                "Available flags:",
                "  LuauSolverV2=false",
                "  LuauCheckedFunctionSyntax=true",
                "  LuauRecursionLimit=1000",
                "  LuauTypeFamilyUseGuesserDepth=-1",
                "  SomethingElse=abc",
                "",
            )
        )
        assertEquals(
            listOf(
                LspFlag("LuauSolverV2", LspFlagType.Bool, "false"),
                LspFlag("LuauCheckedFunctionSyntax", LspFlagType.Bool, "true"),
                LspFlag("LuauRecursionLimit", LspFlagType.Int, "1000"),
                LspFlag("LuauTypeFamilyUseGuesserDepth", LspFlagType.Int, "-1"),
            ), flags
        )
    }
}
