package com.github.aleksandrsl.intellijluau.parser

import com.github.aleksandrsl.intellijluau.psi.LuauTypes
import com.intellij.lang.PsiBuilder
import com.intellij.lang.parser.GeneratedParserUtilBase

object LuauParserUtilBase : GeneratedParserUtilBase() {

    /**
     * Parses an expression statement using the given builder and parser.
     *
     * Expression statement in luau is function call only.
     *
     * @param builder The PsiBuilder instance used for parsing the statement.
     * @param l The current recursion depth level.
     * @param indexAccessOrFuncCallParser A parser responsible for handling index access or function call expressions.
     * @return True if the expression statement was successfully parsed, otherwise false.
     */
    @JvmStatic
    fun parseExprStatement(builder: PsiBuilder, l: Int, indexAccessOrFuncCallParser: Parser): Boolean {
        // Parse the index/call expression, and if it's parsed correctly, I check its type.
        val result = indexAccessOrFuncCallParser.parse(builder, l)

        // If the type is correct, then we are good. If it's not, I don't need to try and reparse this.
        // There is nothing valuable further than expr statement. So we set error and return true (i.e., isFunctionCall is ignored on purpose).
        if (result && !isFunctionCall(builder)) {
            // 1. I don't know how to correctly report errors, but this works.
            //    I did add `report_error_(builder, result)` in the first implementation where I used all these enter/exitSection functions, but it seems to have no extra effect.
            //    Afaiu `builder.error` is a low level API. Since I don't need any clever inner/left and any other handling here, I can use it directly.
            //    I also checked with debugger and there is not so much sense in doing this enter/exit section things and marker. Since we can basically use the parent marker.
            // 2. The second thing is, my error doesn't include the element inside, as normal errors do. Point of improvement?
            //    But at the same time it's nice that the error is only at the end of the line.
            builder.error("Expected function call, got index access")
        }
        return result
    }

    @JvmStatic
    fun parseIndexAccess(builder: PsiBuilder, l: Int, indexAccessOrFuncCallParser: Parser): Boolean {
        // Similar to the one above. If we parse the thing as the call/index access, then it's not a single variable (the other valid case for the parent rule).
        // So we just eat this expression by returning the result, and show error if the type is not correct.
        val result = indexAccessOrFuncCallParser.parse(builder, l)
        if (result && !isIndexAccess(builder)) {
            builder.error("Expected index access, got function call")
        }
        return result
    }

    private fun isFunctionCall(builder: PsiBuilder): Boolean {
        return builder.latestDoneMarker?.tokenType == LuauTypes.FUNC_CALL
    }

    private fun isIndexAccess(builder: PsiBuilder): Boolean {
        return builder.latestDoneMarker?.tokenType == LuauTypes.INDEX_ACCESS
    }
}

