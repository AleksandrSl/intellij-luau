// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.aleksandrsl.intellijluau.psi.impl.*;

public interface LuauTypes {

  IElementType ASSIGNMENT_STATEMENT = new LuauElementType("ASSIGNMENT_STATEMENT");
  IElementType AS_EXP = new LuauElementType("AS_EXP");
  IElementType BINARY_EXP = new LuauElementType("BINARY_EXP");
  IElementType BINDING = new LuauElementType("BINDING");
  IElementType BINDING_LIST = new LuauElementType("BINDING_LIST");
  IElementType BINOP = new LuauElementType("BINOP");
  IElementType BLOCK = new LuauElementType("BLOCK");
  IElementType BOUND_TYPE_LIST = new LuauElementType("BOUND_TYPE_LIST");
  IElementType CLASSIC_FOR_STATEMENT = new LuauElementType("CLASSIC_FOR_STATEMENT");
  IElementType CLOSURE_EXPR = new LuauElementType("CLOSURE_EXPR");
  IElementType COMPOUNDOP = new LuauElementType("COMPOUNDOP");
  IElementType DO_STATEMENT = new LuauElementType("DO_STATEMENT");
  IElementType EXPRESSION = new LuauElementType("EXPRESSION");
  IElementType EXPRESSION_STATEMENT = new LuauElementType("EXPRESSION_STATEMENT");
  IElementType EXP_LIST = new LuauElementType("EXP_LIST");
  IElementType FIELD = new LuauElementType("FIELD");
  IElementType FIELDLIST = new LuauElementType("FIELDLIST");
  IElementType FIELDSEP = new LuauElementType("FIELDSEP");
  IElementType FOREACH_STATEMENT = new LuauElementType("FOREACH_STATEMENT");
  IElementType FUNCARGS = new LuauElementType("FUNCARGS");
  IElementType FUNCNAME = new LuauElementType("FUNCNAME");
  IElementType FUNCTIONCALL = new LuauElementType("FUNCTIONCALL");
  IElementType FUNCTION_TYPE = new LuauElementType("FUNCTION_TYPE");
  IElementType FUNC_BODY = new LuauElementType("FUNC_BODY");
  IElementType FUNC_DEF_STAT = new LuauElementType("FUNC_DEF_STAT");
  IElementType GENERIC_TYPE_LIST = new LuauElementType("GENERIC_TYPE_LIST");
  IElementType GENERIC_TYPE_LIST_WITH_DEFAULTS = new LuauElementType("GENERIC_TYPE_LIST_WITH_DEFAULTS");
  IElementType GENERIC_TYPE_PACK = new LuauElementType("GENERIC_TYPE_PACK");
  IElementType GENERIC_TYPE_PACK_PARAMETER = new LuauElementType("GENERIC_TYPE_PACK_PARAMETER");
  IElementType GENERIC_TYPE_PACK_PARAMETER_WITH_DEFAULT = new LuauElementType("GENERIC_TYPE_PACK_PARAMETER_WITH_DEFAULT");
  IElementType IFELSEEXP = new LuauElementType("IFELSEEXP");
  IElementType IF_STATEMENT = new LuauElementType("IF_STATEMENT");
  IElementType INTERSECTION_SUFFIX = new LuauElementType("INTERSECTION_SUFFIX");
  IElementType LAST_STATEMENT = new LuauElementType("LAST_STATEMENT");
  IElementType LOCAL_FUNC_DEF_STAT = new LuauElementType("LOCAL_FUNC_DEF_STAT");
  IElementType NAMELIST = new LuauElementType("NAMELIST");
  IElementType PARLIST = new LuauElementType("PARLIST");
  IElementType POSTFIX_EXP = new LuauElementType("POSTFIX_EXP");
  IElementType PREFIX_EXP = new LuauElementType("PREFIX_EXP");
  IElementType PROP_LIST = new LuauElementType("PROP_LIST");
  IElementType REPEAT_STATEMENT = new LuauElementType("REPEAT_STATEMENT");
  IElementType RETURN_TYPE = new LuauElementType("RETURN_TYPE");
  IElementType SHEBANG_LINE = new LuauElementType("SHEBANG_LINE");
  IElementType SIMPLE_EXP = new LuauElementType("SIMPLE_EXP");
  IElementType SIMPLE_TYPE = new LuauElementType("SIMPLE_TYPE");
  IElementType SIMPLE_VAR = new LuauElementType("SIMPLE_VAR");
  IElementType SINGLETON_TYPE = new LuauElementType("SINGLETON_TYPE");
  IElementType STATEMENT = new LuauElementType("STATEMENT");
  IElementType STRINGINTERP = new LuauElementType("STRINGINTERP");
  IElementType TABLECONSTRUCTOR = new LuauElementType("TABLECONSTRUCTOR");
  IElementType TABLE_INDEXER = new LuauElementType("TABLE_INDEXER");
  IElementType TABLE_PROP = new LuauElementType("TABLE_PROP");
  IElementType TABLE_PROP_OR_INDEXER = new LuauElementType("TABLE_PROP_OR_INDEXER");
  IElementType TABLE_TYPE = new LuauElementType("TABLE_TYPE");
  IElementType TYPE = new LuauElementType("TYPE");
  IElementType TYPE_LIST = new LuauElementType("TYPE_LIST");
  IElementType TYPE_PACK = new LuauElementType("TYPE_PACK");
  IElementType TYPE_PARAMS = new LuauElementType("TYPE_PARAMS");
  IElementType UNARY_EXP = new LuauElementType("UNARY_EXP");
  IElementType UNION_SUFFIX = new LuauElementType("UNION_SUFFIX");
  IElementType UNOP = new LuauElementType("UNOP");
  IElementType VAR = new LuauElementType("VAR");
  IElementType VARIADIC_TYPE_PACK = new LuauElementType("VARIADIC_TYPE_PACK");
  IElementType WHILE_STATEMENT = new LuauElementType("WHILE_STATEMENT");

  IElementType AND = new LuauTokenType("and");
  IElementType ASSIGN = new LuauTokenType("=");
  IElementType BIT_AND = new LuauTokenType("&");
  IElementType BIT_LTLT = new LuauTokenType("<<");
  IElementType BIT_OR = new LuauTokenType("|");
  IElementType BIT_RTRT = new LuauTokenType(">>");
  IElementType BIT_TILDE = new LuauTokenType("~");
  IElementType BLOCK_COMMENT = new LuauTokenType("BLOCK_COMMENT");
  IElementType BREAK = new LuauTokenType("break");
  IElementType CLASSMETHODDEFSTAT = new LuauTokenType("classMethodDefStat");
  IElementType COLON = new LuauTokenType(":");
  IElementType COMMA = new LuauTokenType(",");
  IElementType CONCAT = new LuauTokenType("..");
  IElementType DIV = new LuauTokenType("/");
  IElementType DO = new LuauTokenType("do");
  IElementType DOC_BLOCK_COMMENT = new LuauTokenType("DOC_BLOCK_COMMENT");
  IElementType DOC_COMMENT = new LuauTokenType("DOC_COMMENT");
  IElementType DOT = new LuauTokenType(".");
  IElementType DOUBLE_COLON = new LuauTokenType("::");
  IElementType DOUBLE_DIV = new LuauTokenType("//");
  IElementType ELLIPSIS = new LuauTokenType("...");
  IElementType ELSE = new LuauTokenType("else");
  IElementType ELSEIF = new LuauTokenType("elseif");
  IElementType END = new LuauTokenType("end");
  IElementType ENDREGION = new LuauTokenType("ENDREGION");
  IElementType EQ = new LuauTokenType("==");
  IElementType EXP = new LuauTokenType("^");
  IElementType FALSE = new LuauTokenType("false");
  IElementType FOR = new LuauTokenType("for");
  IElementType FUNCTION = new LuauTokenType("function");
  IElementType GE = new LuauTokenType(">=");
  IElementType GETN = new LuauTokenType("#");
  IElementType GOTO = new LuauTokenType("goto");
  IElementType GT = new LuauTokenType(">");
  IElementType ID = new LuauTokenType("ID");
  IElementType IF = new LuauTokenType("if");
  IElementType IN = new LuauTokenType("in");
  IElementType INTERP_BEGIN = new LuauTokenType("INTERP_BEGIN");
  IElementType INTERP_END = new LuauTokenType("INTERP_END");
  IElementType INTERP_MID = new LuauTokenType("INTERP_MID");
  IElementType LBRACK = new LuauTokenType("[");
  IElementType LCURLY = new LuauTokenType("{");
  IElementType LE = new LuauTokenType("<=");
  IElementType LOCAL = new LuauTokenType("local");
  IElementType LPAREN = new LuauTokenType("(");
  IElementType LT = new LuauTokenType("<");
  IElementType MINUS = new LuauTokenType("-");
  IElementType MOD = new LuauTokenType("%");
  IElementType MULT = new LuauTokenType("*");
  IElementType NE = new LuauTokenType("~=");
  IElementType NIL = new LuauTokenType("nil");
  IElementType NOT = new LuauTokenType("not");
  IElementType NUMBER = new LuauTokenType("NUMBER");
  IElementType OR = new LuauTokenType("or");
  IElementType PLUS = new LuauTokenType("+");
  IElementType QUESTION = new LuauTokenType("?");
  IElementType RBRACK = new LuauTokenType("]");
  IElementType RCURLY = new LuauTokenType("}");
  IElementType REGION = new LuauTokenType("REGION");
  IElementType REPEAT = new LuauTokenType("repeat");
  IElementType RETURN = new LuauTokenType("return");
  IElementType RPAREN = new LuauTokenType(")");
  IElementType SEMI = new LuauTokenType(";");
  IElementType SHEBANG = new LuauTokenType("#!");
  IElementType SHEBANG_CONTENT = new LuauTokenType("SHEBANG_CONTENT");
  IElementType SHORT_COMMENT = new LuauTokenType("SHORT_COMMENT");
  IElementType STRING = new LuauTokenType("STRING");
  IElementType THEN = new LuauTokenType("then");
  IElementType TRUE = new LuauTokenType("true");
  IElementType UNTIL = new LuauTokenType("until");
  IElementType WHILE = new LuauTokenType("while");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ASSIGNMENT_STATEMENT) {
        return new LuauAssignmentStatementImpl(node);
      }
      else if (type == AS_EXP) {
        return new LuauAsExpImpl(node);
      }
      else if (type == BINARY_EXP) {
        return new LuauBinaryExpImpl(node);
      }
      else if (type == BINDING) {
        return new LuauBindingImpl(node);
      }
      else if (type == BINDING_LIST) {
        return new LuauBindingListImpl(node);
      }
      else if (type == BINOP) {
        return new LuauBinopImpl(node);
      }
      else if (type == BLOCK) {
        return new LuauBlockImpl(node);
      }
      else if (type == BOUND_TYPE_LIST) {
        return new LuauBoundTypeListImpl(node);
      }
      else if (type == CLASSIC_FOR_STATEMENT) {
        return new LuauClassicForStatementImpl(node);
      }
      else if (type == CLOSURE_EXPR) {
        return new LuauClosureExprImpl(node);
      }
      else if (type == COMPOUNDOP) {
        return new LuauCompoundopImpl(node);
      }
      else if (type == DO_STATEMENT) {
        return new LuauDoStatementImpl(node);
      }
      else if (type == EXPRESSION_STATEMENT) {
        return new LuauExpressionStatementImpl(node);
      }
      else if (type == EXP_LIST) {
        return new LuauExpListImpl(node);
      }
      else if (type == FIELD) {
        return new LuauFieldImpl(node);
      }
      else if (type == FIELDLIST) {
        return new LuauFieldlistImpl(node);
      }
      else if (type == FIELDSEP) {
        return new LuauFieldsepImpl(node);
      }
      else if (type == FOREACH_STATEMENT) {
        return new LuauForeachStatementImpl(node);
      }
      else if (type == FUNCARGS) {
        return new LuauFuncargsImpl(node);
      }
      else if (type == FUNCNAME) {
        return new LuauFuncnameImpl(node);
      }
      else if (type == FUNCTIONCALL) {
        return new LuauFunctioncallImpl(node);
      }
      else if (type == FUNCTION_TYPE) {
        return new LuauFunctionTypeImpl(node);
      }
      else if (type == FUNC_BODY) {
        return new LuauFuncBodyImpl(node);
      }
      else if (type == FUNC_DEF_STAT) {
        return new LuauFuncDefStatImpl(node);
      }
      else if (type == GENERIC_TYPE_LIST) {
        return new LuauGenericTypeListImpl(node);
      }
      else if (type == GENERIC_TYPE_LIST_WITH_DEFAULTS) {
        return new LuauGenericTypeListWithDefaultsImpl(node);
      }
      else if (type == GENERIC_TYPE_PACK) {
        return new LuauGenericTypePackImpl(node);
      }
      else if (type == GENERIC_TYPE_PACK_PARAMETER) {
        return new LuauGenericTypePackParameterImpl(node);
      }
      else if (type == GENERIC_TYPE_PACK_PARAMETER_WITH_DEFAULT) {
        return new LuauGenericTypePackParameterWithDefaultImpl(node);
      }
      else if (type == IFELSEEXP) {
        return new LuauIfelseexpImpl(node);
      }
      else if (type == IF_STATEMENT) {
        return new LuauIfStatementImpl(node);
      }
      else if (type == INTERSECTION_SUFFIX) {
        return new LuauIntersectionSuffixImpl(node);
      }
      else if (type == LAST_STATEMENT) {
        return new LuauLastStatementImpl(node);
      }
      else if (type == LOCAL_FUNC_DEF_STAT) {
        return new LuauLocalFuncDefStatImpl(node);
      }
      else if (type == NAMELIST) {
        return new LuauNamelistImpl(node);
      }
      else if (type == PARLIST) {
        return new LuauParlistImpl(node);
      }
      else if (type == POSTFIX_EXP) {
        return new LuauPostfixExpImpl(node);
      }
      else if (type == PREFIX_EXP) {
        return new LuauPrefixExpImpl(node);
      }
      else if (type == PROP_LIST) {
        return new LuauPropListImpl(node);
      }
      else if (type == REPEAT_STATEMENT) {
        return new LuauRepeatStatementImpl(node);
      }
      else if (type == RETURN_TYPE) {
        return new LuauReturnTypeImpl(node);
      }
      else if (type == SHEBANG_LINE) {
        return new LuauShebangLineImpl(node);
      }
      else if (type == SIMPLE_EXP) {
        return new LuauSimpleExpImpl(node);
      }
      else if (type == SIMPLE_TYPE) {
        return new LuauSimpleTypeImpl(node);
      }
      else if (type == SIMPLE_VAR) {
        return new LuauSimpleVarImpl(node);
      }
      else if (type == SINGLETON_TYPE) {
        return new LuauSingletonTypeImpl(node);
      }
      else if (type == STATEMENT) {
        return new LuauStatementImpl(node);
      }
      else if (type == STRINGINTERP) {
        return new LuauStringinterpImpl(node);
      }
      else if (type == TABLECONSTRUCTOR) {
        return new LuauTableconstructorImpl(node);
      }
      else if (type == TABLE_INDEXER) {
        return new LuauTableIndexerImpl(node);
      }
      else if (type == TABLE_PROP) {
        return new LuauTablePropImpl(node);
      }
      else if (type == TABLE_PROP_OR_INDEXER) {
        return new LuauTablePropOrIndexerImpl(node);
      }
      else if (type == TABLE_TYPE) {
        return new LuauTableTypeImpl(node);
      }
      else if (type == TYPE) {
        return new LuauTypeImpl(node);
      }
      else if (type == TYPE_LIST) {
        return new LuauTypeListImpl(node);
      }
      else if (type == TYPE_PACK) {
        return new LuauTypePackImpl(node);
      }
      else if (type == TYPE_PARAMS) {
        return new LuauTypeParamsImpl(node);
      }
      else if (type == UNARY_EXP) {
        return new LuauUnaryExpImpl(node);
      }
      else if (type == UNION_SUFFIX) {
        return new LuauUnionSuffixImpl(node);
      }
      else if (type == UNOP) {
        return new LuauUnopImpl(node);
      }
      else if (type == VAR) {
        return new LuauVarImpl(node);
      }
      else if (type == VARIADIC_TYPE_PACK) {
        return new LuauVariadicTypePackImpl(node);
      }
      else if (type == WHILE_STATEMENT) {
        return new LuauWhileStatementImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
