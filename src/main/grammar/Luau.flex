package com.github.aleksandrsl.intellijluau.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static com.github.aleksandrsl.intellijluau.psi.LuauTypes.*;
import com.intellij.psi.TokenType;
import java.util.Stack;

%%

%{
  public LuauLexer() {
    this((java.io.Reader)null);
  }
  private Stack<Integer> stack = new Stack<>();
  private void pushState(int state) {
    stack.push(yystate());
    yybegin(state);
  }
  private void popState() {
    Integer state = stack.pop();
    yybegin(state);
  }

  private int nBrackets = 0;
  private boolean checkAhead(char c, int offset) {
    return this.zzMarkedPos + offset < this.zzBuffer.length() && this.zzBuffer.charAt(this.zzMarkedPos + offset) == c;
  }

  private boolean checkBlock() {
    nBrackets = 0;
    if (checkAhead('[', 0)) {
      int n = 0;
      while (checkAhead('=', n + 1)) n++;
      if (checkAhead('[', n + 1)) {
        nBrackets = n;
        return true;
      }
    }
    return false;
  }

  private boolean checkDocBlock() {
    return checkAhead('-', nBrackets + 2)
      && checkAhead('-', nBrackets + 3)
      && checkAhead('-', nBrackets + 4);
  }

  private int checkBlockEnd() {
    int pos = zzMarkedPos;
    int end = zzEndRead;
    while(pos < end) {
      char c = zzBuffer.charAt(pos);
      if (c == ']') {
        pos++;
        int size = 0;
        while (pos < zzEndRead && zzBuffer.charAt(pos) == '=') {
          size++;
          pos++;
        }
        if (size == nBrackets && pos < zzEndRead && zzBuffer.charAt(pos) == ']') {
          pos++;
          break;
        }
        continue;
      }
      pos++;
    }
    return pos - zzMarkedPos;
  }
%}

%class LuauLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+

VALID_CHAR=[a-zA-Z_\u00ff-\uffff]
ID={VALID_CHAR} ({VALID_CHAR}|[0-9])*

//Number
n=[0-9][0-9_]*
h=[0-9a-fA-F][0-9a-fA-F_]*
exp=[Ee][+-]?{n}
// 123ULL/123LL
// 0x123FFULL/0x123FFLL
// TODO (AleksandrSl 22/06/2024): Does lua has this?
JIT_EXT_NUMBER=(0[xX]{h}|{n})U?LL
BIN_NUMBER=0[bB][01][01_]*
// Floating point hex is not a thing in luau, see https://luau-lang.org/compatibility
// There is nothing about p notation in the docs, but luau doesn't recognize it
HEX_NUMBER=0[xX]{h}{exp}?
DEC_NUMBER=({n}|{n}[.]{n}){exp}?|[.]{n}|{n}[.]
NUMBER={JIT_EXT_NUMBER}|{HEX_NUMBER}|{BIN_NUMBER}|{DEC_NUMBER}

//Comments
REGION_START =--(region|\{\{\{)([^\r\n]*)*
REGION_END =--(endregion|\}\}\})([^\r\n]*)*
SHORT_COMMENT=--[^\r\n]*
DOC_COMMENT=----*[^\r\n]*(\r?\n{LINE_WS}*----*[^\r\n]*)*

//Strings
COMMON_STRING_ESCAPES = \\z\s*|\\.|\\[\r\n]
// ? is important because we can miss the second quote with string being yet incomplete and we don't have [^] rule in lexer. Thus we break promise to parse the whole file.
// Line breaks are also allowed, I have to check if this actually needed if I don't want to write separate String lexer other lua plugins do
// Just \ is allowed in luau but not in lua 5.3. I can't just drop them from the first negation, becuase otherwise it will eat up all the escapes.
DOUBLE_QUOTED_STRING=\"([^\"\r\n\\]|{COMMON_STRING_ESCAPES})*\"?
SINGLE_QUOTED_STRING='([^'\r\n\\]|{COMMON_STRING_ESCAPES})*'?

/*

From lua5.3 which is close to luau
A short literal string can be delimited by matching single or double quotes,
 and can contain the following C-like escape sequences:
 '\a' (bell), '\b' (backspace), '\f' (form feed), '\n' (newline), '\r' (carriage return),
 '\t' (horizontal tab), '\v' (vertical tab), '\\' (backslash),
 '\"' (quotation mark [double quote]), and
 '\'' (apostrophe [single quote]).
 A backslash followed by a line break results in a newline in the string.
 The escape sequence '\z' skips the following span of white-space characters,
  including line breaks;
   A short literal string cannot contain unescaped line breaks nor escapes not forming a valid escape sequence.

  luau definitely allows other escapes.
*/
// . doesn't match new lines, so match all the stuff after the \ and allow
TEMPLATE_STRING_PART=([^`\\{\r\n]|{COMMON_STRING_ESCAPES})+

/*
 case BrokenInterpDoubleBrace:
   return "'{{', which is invalid (did you mean '\\{'?)";

  Interesting, luau has common errors in lexer
*/

%state xSHEBANG
%state xDOUBLE_QUOTED_STRING
%state xSINGLE_QUOTED_STRING
%state xCOMMENT
%state xTEMPLATE_STRING
%state xTEMPLATE_STRING_EXPRESSION

%%

<YYINITIAL> {
  "--"                        {
    boolean block = checkBlock();
    if (block) {
      boolean docBlock = checkDocBlock();
      yypushback(yylength());
      zzMarkedPos += checkBlockEnd();
      return docBlock ? DOC_BLOCK_COMMENT : BLOCK_COMMENT;
    } else { yypushback(yylength()); pushState(xCOMMENT); }
  }
  {REGION_START}              { return REGION; }
  {REGION_END}                { return ENDREGION; }
  "#!"                        { pushState(xSHEBANG); return SHEBANG; }
}

<YYINITIAL, xTEMPLATE_STRING_EXPRESSION> {
  {WHITE_SPACE}               { return TokenType.WHITE_SPACE; }
  "and"                       { return AND; }
  "break"                     { return BREAK; }
  "do"                        { return DO; }
  "else"                      { return ELSE; }
  "elseif"                    { return ELSEIF; }
  "end"                       { return END; }
  "false"                     { return FALSE; }
  "for"                       { return FOR; }
  "function"                  { return FUNCTION; }
  "if"                        { return IF; }
  "in"                        { return IN; }
  "local"                     { return LOCAL; }
  "nil"                       { return NIL; }
  "not"                       { return NOT; }
  "or"                        { return OR; }
  "repeat"                    { return REPEAT; }
  "return"                    { return RETURN; }
  "then"                      { return THEN; }
  "true"                      { return TRUE; }
  "until"                     { return UNTIL; }
  "while"                     { return WHILE; }
  "..."                       { return ELLIPSIS; }
  ".."                        { return CONCAT; }
  "=="                        { return EQ; }
  ">="                        { return GE; }
  "<="                        { return LE; }
  "~="                        { return NE; }
  "-"                         { return MINUS; }
  "+"                         { return PLUS; }
  "*"                         { return MULT; }
  "%"                         { return MOD; }
  "//"                        { return DOUBLE_DIV; }
  "/"                         { return DIV; }
  "="                         { return ASSIGN; }
  ">"                         { return GT; }
  "<"                         { return LT; }
  "("                         { return LPAREN; }
  ")"                         { return RPAREN; }
  "["                         {
    if (checkAhead('=', 0) || checkAhead('[', 0)) {
      yypushback(yylength());
      checkBlock();
      zzMarkedPos += checkBlockEnd();
      return STRING;
    } else {
      return LBRACK;
    }
  }
  "]"                         { return RBRACK; }
  "{"                         { return LCURLY; }
  "}"                         { if (yystate() == xTEMPLATE_STRING_EXPRESSION) { popState(); }; return RCURLY; }
  "#"                         { return GETN; }
  ","                         { return COMMA; }
  ";"                         { return SEMI; }
  "::"                        { return DOUBLE_COLON; } // type casts
  ":"                         { return COLON; }
  "."                         { return DOT; }
  "^"                         { return EXP; }
  "?"                         { return QUESTION; }
  "+="                        { return PLUS_EQ; }
  "-="                        { return MINUS_EQ; }
  "*="                        { return MULT_EQ; }
  "/="                        { return DIV_EQ; }
  "//="                       { return DOUBLE_DIV_EQ; }
  "%="                        { return MOD_EQ; }
  "^="                        { return EXP_EQ; }
  "..="                       { return CONCAT_EQ; }
  "->"                        { return ARROW; }
  "&"                         { return INTERSECTION; }
  "|"                         { return UNION; }
  "@"                         { return AT; }
  "\""                        { pushState(xDOUBLE_QUOTED_STRING); yypushback(yylength()); }
  "'"                         { pushState(xSINGLE_QUOTED_STRING); yypushback(yylength()); }
  "`"                         { pushState(xTEMPLATE_STRING); return TEMPLATE_STRING_SQUOTE; }
  {ID}                        { return ID; }
  {NUMBER}                    { return NUMBER; }
  [^]                         { return TokenType.BAD_CHARACTER; }
}

<xSHEBANG> {
  [^\r\n]+                    { popState(); return SHEBANG_CONTENT; }
}

<xCOMMENT> {
  {DOC_COMMENT}               { popState(); return DOC_COMMENT; }
  {SHORT_COMMENT}             { popState(); return SHORT_COMMENT; }
}

<xDOUBLE_QUOTED_STRING> {
  // \n as a bad token isn't properly highlighted by lexer, let's highlight the whole string for now, until I know how to do errors js style
  {DOUBLE_QUOTED_STRING}      { popState(); if (yycharat(yylength() - 1) == '"') { return STRING; } else { return TokenType.BAD_CHARACTER; } }
  [^]                         { return TokenType.BAD_CHARACTER; }
}

<xSINGLE_QUOTED_STRING> {
  {SINGLE_QUOTED_STRING}      { popState(); if (yycharat(yylength() - 1) == '\'') { return STRING; } else { return TokenType.BAD_CHARACTER; } }
  [^]                         { return TokenType.BAD_CHARACTER; }
}

<xTEMPLATE_STRING> {
  "{"                         { pushState(xTEMPLATE_STRING_EXPRESSION); return LCURLY; }
  "`"                         { popState(); return TEMPLATE_STRING_EQUOTE; }
  {TEMPLATE_STRING_PART}      { return STRING; }
  [^]                         { return TokenType.BAD_CHARACTER; }
}
