package com.github.aleksandrsl.intellijluau

import com.github.aleksandrsl.intellijluau.lexer.LuauLexerAdapter
import com.github.aleksandrsl.intellijluau.parser.LuauParser
import com.github.aleksandrsl.intellijluau.psi.LuauFile
import com.github.aleksandrsl.intellijluau.psi.LuauTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

private val FILE = IFileElementType(LuauLanguage.INSTANCE)

class LuauParserDefinition : ParserDefinition {

    override fun createLexer(project: Project?): Lexer {
        return LuauLexerAdapter()
    }

    override fun getCommentTokens(): TokenSet {
        return LuauTokenSets.COMMENTS
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createParser(project: Project?): PsiParser {
        return LuauParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return LuauFile(viewProvider)
    }

    // TODO (AleksandrSl 31/03/2025): Check if this one is corrent
    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements {
        return ParserDefinition.SpaceRequirements.MAY
    }

    override fun createElement(node: ASTNode?): PsiElement {
        return LuauTypes.Factory.createElement(node)
    }
}
