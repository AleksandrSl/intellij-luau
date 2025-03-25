package com.github.aleksandrsl.intellijluau.formatter

import com.github.aleksandrsl.intellijluau.LuauLanguage
import com.github.aleksandrsl.intellijluau.LuauTokenSets.ADDITIVE_OPERATORS
import com.github.aleksandrsl.intellijluau.LuauTokenSets.ASSIGN_OPERATORS
import com.github.aleksandrsl.intellijluau.LuauTokenSets.EQUALITY_OPERATORS
import com.github.aleksandrsl.intellijluau.LuauTokenSets.BINARY_LOGICAL_OPERATORS
import com.github.aleksandrsl.intellijluau.LuauTokenSets.MULTIPLICATIVE_OPERATORS
import com.github.aleksandrsl.intellijluau.LuauTokenSets.RELATIONAL_OPERATORS
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
    // I was surprised that you have to do this manually and not take from the spaceIf;
    // we pass the settings to the main constructor.
    // But actually, it makes a lot of sense because the config should be as simple as possible and having a lambda
    // would result in more runtime work.
    // At the same time it's a builder, so it only does this once anyway.
    // Anyway, I took a look at intellij plugins and they do it like that
    val commonSettings = settings.getCommonSettings(LuauLanguage.INSTANCE.id)
    return SpacingBuilder(settings, LuauLanguage.INSTANCE)
        .before(END).lineBreakInCode()
        .after(DO).lineBreakInCode()
        .between(THEN, BLOCK).lineBreakInCode()
        .between(ELSE, BLOCK).lineBreakInCode()
        .between(ELSEIF, BLOCK).lineBreakInCode()
        .after(LOCAL).spaces(1)
        .between(COLON, TYPE).spaces(1)
        .around(ASSIGN_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_ASSIGNMENT_OPERATORS)
        .around(ADDITIVE_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_ADDITIVE_OPERATORS)
        .around(MULTIPLICATIVE_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_MULTIPLICATIVE_OPERATORS)
        .around(BINARY_LOGICAL_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_LOGICAL_OPERATORS)
        .around(EQUALITY_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_EQUALITY_OPERATORS)
        .around(RELATIONAL_OPERATORS).spaceIf(commonSettings.SPACE_AROUND_RELATIONAL_OPERATORS)
        .after(NOT).spaces(1)
}
