package com.github.aleksandrsl.intellijluau.parser

import com.github.aleksandrsl.intellijluau.LuauTokenSets.FUNCTION_TYPE_RECOVERY
import com.github.aleksandrsl.intellijluau.psi.LuauTypes
import com.intellij.lang.PsiBuilder
import com.intellij.lang.parser.GeneratedParserUtilBase
import com.intellij.psi.tree.IElementType

/**
 * Utility class for parsing Luau-specific language constructs.
 *
 * This class extends the base functionality of the IntelliJ `GeneratedParserUtilBase`
 * to provide custom parsing logic tailored to the Luau language.
 */
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



    /**
     * Determines if the current parsing context corresponds to a function type.
     *
     * It's definitely not an ideal way, but I didn't come up with a better way to make nested () work.
     *
     * @param builder A `PsiBuilder` instance used for parsing and token navigation.
     * @param l The current level of recursion or parsing context depth.
     * @return `false` if the current context is determined to be not a function;
     *  `true` otherwise, to avoid reparsing the code that is incorrect anyway.
     */
    @JvmStatic
    fun isFunctionType(builder: PsiBuilder, l: Int): Boolean {
        var parenCount = 0
        var offset = 0

        if (builder.lookAhead(offset) !== LuauTypes.LPAREN) return false
        // TODO (AleksandrSl 30/03/2025): Possible improvement.
        //  If we return on any place other than return next === LuauTypes.ARROW
        //  Just mark all the code until the symbol we ended up as wrong and continue parsing from that point.
        while (true) {
            val tokenType: IElementType = builder.lookAhead(offset++) ?: return false

            when (tokenType) {
                LuauTypes.LPAREN -> parenCount++
                LuauTypes.RPAREN -> {
                    parenCount--
                    if (parenCount == 0) {
                        val next: IElementType = builder.lookAhead(offset) ?: return false
                        return next === LuauTypes.ARROW
                    }
                }
                else -> {
                    // If we've reached this point, we are out of the possible type tokens so we don't know
                    // whether we see function or anything else.
                    if (FUNCTION_TYPE_RECOVERY.contains(tokenType)) {
                        return false
                    }
                }
            }
        }
    }


    /**
     * Parses a type expression in a specified context using the provided parsers for simple,
     * union, and intersection types.
     *
     * If we detect an indicator of union type then we only parse union type parts and otherwise.
     * In luau it's prohibited to mix them, unless you nest one part the parts into parentheses
     *
     * The method is not strictly required when I use upper for union_type.
     * It was required when I had the left modifier to avoid extra markers.
     * See https://github.com/JetBrains/Grammar-Kit/issues/396
     *
     * @param builder The `PsiBuilder` object used for parsing and token manipulation.
     * @param l The current level of recursion used to guard against deep recursion.
     * @param parseSimpleType A `Parser` responsible for parsing simple type expressions.
     * @param parseUnionType A `Parser` responsible for parsing union type expressions.
     * @param parseIntersectionType A `Parser` responsible for parsing intersection type expressions.
     * @return `true` if the parsing was successful, otherwise `false`.
     */
    @JvmStatic
    fun parseType(builder: PsiBuilder, l: Int, parseSimpleType: Parser, parseUnionType: Parser, parseIntersectionType: Parser): Boolean {
        val m = enter_section_(builder)
        var type = 0
        var r = parseSimpleType.parse(builder, l + 1)
        if (r && consumeTokenFast(builder, LuauTypes.QUESTION)) type = 1
        r && when(builder.lookAhead(0)) {
            LuauTypes.UNION -> {
                type = 1
                true
            }
            LuauTypes.INTERSECTION -> {
                type = 2
                true
            }
            else -> true
        }
        r = r && when (type) {
            1 -> parseUnionType.parse(builder, l + 1)
            2 -> parseIntersectionType.parse(builder, l + 1)
            else -> true
        }
        exit_section_(builder, m, null, r)
        return r
    }


    private fun isFunctionCall(builder: PsiBuilder): Boolean {
        return builder.latestDoneMarker?.tokenType == LuauTypes.FUNC_CALL
    }

    private fun isIndexAccess(builder: PsiBuilder): Boolean {
        return builder.latestDoneMarker?.tokenType == LuauTypes.INDEX_ACCESS
    }
}

