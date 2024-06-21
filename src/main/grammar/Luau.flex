package com.github.aleksandrsl.intellijluau.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static com.github.aleksandrsl.intellijluau.psi.LuauTypes.*;
import com.intellij.psi.TokenType;

%%

%{
  public LuauLexer() {
    this((java.io.Reader)null);
  }
  private boolean insideTemplate = false;
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
n=[0-9]+
h=[0-9a-fA-F]+
exp=[Ee]([+-]?{n})?
ppp=[Pp][+-]{n}
// 123ULL/123LL
// 0x123FFULL/0x123FFLL
JIT_EXT_NUMBER=(0[xX]{h}|{n})U?LL
HEX_NUMBER=0[xX]({h}|{h}[.]{h})({exp}|{ppp})?
NUMBER={JIT_EXT_NUMBER}|{HEX_NUMBER}|({n}|{n}[.]{n}){exp}?|[.]{n}|{n}[.]

//Comments
REGION_START =--(region|\{\{\{)([^\r\n]*)*
REGION_END =--(endregion|\}\}\})([^\r\n]*)*
BLOCK_COMMENT=--\[=*\[[\s\S]*(\]=*\])?
DOC_BLOCK_COMMENT=--\[=*\[---+[\s\S]*(\]=*\])?
SHORT_COMMENT=--[^\r\n]*
DOC_COMMENT=----*[^\r\n]*(\r?\n{LINE_WS}*----*[^\r\n]*)*

//Strings
// ? is important because we can miss the second quote with string being yet incomplete and we don't have [^] rule in lexer. Thus we break promise to parse the whole file.
// Line breaks are also allowed, I have to check if this actually needed if I don't want to write separate String lexer other lua plugins do
DOUBLE_QUOTED_STRING=\"([^\\\"]|\\\S|\\[\r\n])*\"?
SINGLE_QUOTED_STRING='([^\\\']|\\\S|\\[\r\n])*'?
TEMPLATE_QUOTED_STRING_PART=([^\`\r\n\{])*
//[[]]
LONG_STRING=\[=*\[[\s\S]*\]=*\]

%state xSHEBANG
%state xDOUBLE_QUOTED_STRING
%state xSINGLE_QUOTED_STRING
%state xBLOCK_STRING
%state xCOMMENT
%state xBLOCK_COMMENT
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
          }
          else { yypushback(yylength()); yybegin(xCOMMENT); }
     }
    {REGION_START}              { return REGION; }
    {REGION_END}                { return ENDREGION; }
    "#!"                        { yybegin(xSHEBANG); return SHEBANG; }
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
     "}"                         { if (yystate() == xTEMPLATE_STRING_EXPRESSION) { yybegin(xTEMPLATE_STRING); }; return RCURLY; }
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

     "\""                        { yybegin(xDOUBLE_QUOTED_STRING); yypushback(yylength()); }
     "'"                         { yybegin(xSINGLE_QUOTED_STRING); yypushback(yylength()); }
     "`"                         { yybegin(xTEMPLATE_STRING); insideTemplate = true; return TEMPLATE_STRING_SQUOTE; }

     {ID}                        { return ID; }
     {NUMBER}                    { return NUMBER; }

     [^]                         { return TokenType.BAD_CHARACTER; }
}

<xSHEBANG> {
    [^\r\n]*                  { yybegin(YYINITIAL); return SHEBANG_CONTENT; }
}

<xCOMMENT> {
    {DOC_COMMENT}             { yybegin(YYINITIAL);return DOC_COMMENT; }
    {SHORT_COMMENT}           { yybegin(YYINITIAL);return SHORT_COMMENT; }
}

<xDOUBLE_QUOTED_STRING> {
    {DOUBLE_QUOTED_STRING}    { yybegin(insideTemplate ? xTEMPLATE_STRING : YYINITIAL); return STRING; }
}

<xSINGLE_QUOTED_STRING> {
    {SINGLE_QUOTED_STRING}    { yybegin(insideTemplate ? xTEMPLATE_STRING : YYINITIAL); return STRING; }
}

<xTEMPLATE_STRING> {
   "{" { yybegin(xTEMPLATE_STRING_EXPRESSION); return LCURLY; }
   "`" { yybegin(YYINITIAL); insideTemplate = false; return TEMPLATE_STRING_EQUOTE; }
   {EOL} { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
   {TEMPLATE_QUOTED_STRING_PART}  { return STRING; }
}
