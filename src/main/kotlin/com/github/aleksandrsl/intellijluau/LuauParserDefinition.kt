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
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class LuauParserDefinition : ParserDefinition {
    val WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE)
    val COMMENTS = TokenSet.create(LuauTypes.BLOCK_COMMENT, LuauTypes.DOC_COMMENT, LuauTypes.SHORT_COMMENT, LuauTypes.DOC_BLOCK_COMMENT)

    val FILE = IFileElementType(LuauLanguage.INSTANCE)

    override fun createLexer(project: Project?): Lexer {
        return LuauLexerAdapter()
    }

    override fun getWhitespaceTokens(): TokenSet {
        return WHITE_SPACES
    }

    override fun getCommentTokens(): TokenSet {
        return COMMENTS
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

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements {
        return ParserDefinition.SpaceRequirements.MAY
    }

    override fun createElement(node: ASTNode?): PsiElement {
        return LuauTypes.Factory.createElement(node)
    }

    companion object {
        val COMMENT_TOKENS = TokenSet.create(LuauTypes.BLOCK_COMMENT, LuauTypes.DOC_COMMENT, LuauTypes.SHORT_COMMENT, LuauTypes.DOC_BLOCK_COMMENT)
        val KEYWORD_TOKENS = TokenSet.create(
            LuauTypes.AND,
            LuauTypes.BREAK,
            LuauTypes.DO,
            LuauTypes.ELSE,
            LuauTypes.ELSEIF,
            LuauTypes.END,
            LuauTypes.FOR,
            LuauTypes.FUNCTION,
            LuauTypes.IF,
            LuauTypes.IN,
            LuauTypes.LOCAL,
            LuauTypes.NOT,
            LuauTypes.OR,
            LuauTypes.REPEAT,
            LuauTypes.RETURN,
            LuauTypes.THEN,
            LuauTypes.UNTIL,
            LuauTypes.WHILE,
            // Luau type cast operator
            LuauTypes.DOUBLE_COLON,
        )
    }
}
