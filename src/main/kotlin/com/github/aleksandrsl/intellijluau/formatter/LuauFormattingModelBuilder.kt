package com.github.aleksandrsl.intellijluau.formatter

import com.github.aleksandrsl.intellijluau.LuauLanguage
import com.github.aleksandrsl.intellijluau.formatter.blocks.LuauFormatterBlock
import com.github.aleksandrsl.intellijluau.psi.LuauTypes.*
import com.intellij.formatting.*
import com.intellij.psi.codeStyle.CodeStyleSettings


internal class LuauFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val codeStyleSettings = formattingContext.codeStyleSettings
        return FormattingModelProvider
            .createFormattingModelForPsiFile(
                formattingContext.containingFile,
                LuauFormatterBlock(
                    formattingContext.node,
                    Wrap.createWrap(WrapType.NONE, false),
                    null,
                    createSpaceBuilder(codeStyleSettings)
                ),
                codeStyleSettings
            )
    }
}

private fun createSpaceBuilder(settings: CodeStyleSettings): SpacingBuilder {
    return SpacingBuilder(settings, LuauLanguage.INSTANCE)
        .before(END).lineBreakInCode()
        .after(DO).lineBreakInCode()
        .between(THEN, BLOCK).lineBreakInCode()
        .between(ELSE, BLOCK).lineBreakInCode()
        .between(ELSEIF, BLOCK).lineBreakInCode()
        .after(LOCAL).spaces(1) //local<SPACE>
//                TODO: Enable rule below, but it shouldn't interfere with one above.
//                .around(COLON).none()
        .between(COLON, TYPE).spaces(1)
        .around(EQ).spaceIf(settings.getCommonSettings(LuauLanguage.INSTANCE.id).SPACE_AROUND_ASSIGNMENT_OPERATORS)
}
