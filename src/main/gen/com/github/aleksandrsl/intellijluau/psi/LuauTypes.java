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
  IElementType BIN_OP = new LuauElementType("BIN_OP");
  IElementType BLOCK = new LuauElementType("BLOCK");
  IElementType BOUND_TYPE_LIST = new LuauElementType("BOUND_TYPE_LIST");
  IElementType CLASSIC_FOR_STATEMENT = new LuauElementType("CLASSIC_FOR_STATEMENT");
  IElementType CLOSURE_EXP = new LuauElementType("CLOSURE_EXP");
  IElementType COMPOUND_OP = new LuauElementType("COMPOUND_OP");
  IElementType CONTINUE_SOFT_KEYWORD = new LuauElementType("CONTINUE_SOFT_KEYWORD");
  IElementType DO_STATEMENT = new LuauElementType("DO_STATEMENT");
  IElementType EXPORT_SOFT_KEYWORD = new LuauElementType("EXPORT_SOFT_KEYWORD");
  IElementType EXPRESSION = new LuauElementType("EXPRESSION");
  IElementType EXPRESSION_STATEMENT = new LuauElementType("EXPRESSION_STATEMENT");
  IElementType EXP_LIST = new LuauElementType("EXP_LIST");
  IElementType FIELD = new LuauElementType("FIELD");
  IElementType FIELD_LIST = new LuauElementType("FIELD_LIST");
  IElementType FIELD_SEP = new LuauElementType("FIELD_SEP");
  IElementType FOREACH_STATEMENT = new LuauElementType("FOREACH_STATEMENT");
  IElementType FUNCTION_CALL = new LuauElementType("FUNCTION_CALL");
  IElementType FUNCTION_TYPE = new LuauElementType("FUNCTION_TYPE");
  IElementType FUNC_ARGS = new LuauElementType("FUNC_ARGS");
  IElementType FUNC_BODY = new LuauElementType("FUNC_BODY");
  IElementType FUNC_DEF_STATEMENT = new LuauElementType("FUNC_DEF_STATEMENT");
  IElementType FUNC_NAME = new LuauElementType("FUNC_NAME");
  IElementType GENERIC_TYPE_LIST = new LuauElementType("GENERIC_TYPE_LIST");
  IElementType GENERIC_TYPE_LIST_WITH_DEFAULTS = new LuauElementType("GENERIC_TYPE_LIST_WITH_DEFAULTS");
  IElementType GENERIC_TYPE_PACK = new LuauElementType("GENERIC_TYPE_PACK");
  IElementType GENERIC_TYPE_PACK_PARAMETER = new LuauElementType("GENERIC_TYPE_PACK_PARAMETER");
  IElementType GENERIC_TYPE_PACK_PARAMETER_WITH_DEFAULT = new LuauElementType("GENERIC_TYPE_PACK_PARAMETER_WITH_DEFAULT");
  IElementType IFELSE_EXP = new LuauElementType("IFELSE_EXP");
  IElementType IF_STATEMENT = new LuauElementType("IF_STATEMENT");
  IElementType INTERSECTION_SUFFIX = new LuauElementType("INTERSECTION_SUFFIX");
  IElementType LAST_STATEMENT = new LuauElementType("LAST_STATEMENT");
  IElementType LOCAL_FUNC_DEF_STATEMENT = new LuauElementType("LOCAL_FUNC_DEF_STATEMENT");
  IElementType PAR_LIST = new LuauElementType("PAR_LIST");
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
  IElementType TABLE_CONSTRUCTOR = new LuauElementType("TABLE_CONSTRUCTOR");
  IElementType TABLE_INDEXER = new LuauElementType("TABLE_INDEXER");
  IElementType TABLE_PROP = new LuauElementType("TABLE_PROP");
  IElementType TABLE_PROP_OR_INDEXER = new LuauElementType("TABLE_PROP_OR_INDEXER");
  IElementType TABLE_TYPE = new LuauElementType("TABLE_TYPE");
  IElementType TEMPLATE_STRING = new LuauElementType("TEMPLATE_STRING");
  IElementType TYPE = new LuauElementType("TYPE");
  IElementType TYPE_DECLARATION_STATEMENT = new LuauElementType("TYPE_DECLARATION_STATEMENT");
  IElementType TYPE_LIST = new LuauElementType("TYPE_LIST");
  IElementType TYPE_PACK = new LuauElementType("TYPE_PACK");
  IElementType TYPE_PARAMS = new LuauElementType("TYPE_PARAMS");
  IElementType TYPE_SOFT_KEYWORD = new LuauElementType("TYPE_SOFT_KEYWORD");
  IElementType UNARY_EXP = new LuauElementType("UNARY_EXP");
  IElementType UNION_SUFFIX = new LuauElementType("UNION_SUFFIX");
  IElementType UN_OP = new LuauElementType("UN_OP");
  IElementType VAR = new LuauElementType("VAR");
  IElementType VARIADIC_TYPE_PACK = new LuauElementType("VARIADIC_TYPE_PACK");
  IElementType VAR_LIST = new LuauElementType("VAR_LIST");
  IElementType WHILE_STATEMENT = new LuauElementType("WHILE_STATEMENT");

  IElementType AND = new LuauTokenType("and");
  IElementType ARROW = new LuauTokenType("->");
  IElementType ASSIGN = new LuauTokenType("=");
  IElementType BLOCK_COMMENT = new LuauTokenType("BLOCK_COMMENT");
  IElementType BREAK = new LuauTokenType("break");
  IElementType CLASS_METHOD_DEF_STATEMENT = new LuauTokenType("class_method_def_statement");
  IElementType COLON = new LuauTokenType(":");
  IElementType COMMA = new LuauTokenType(",");
  IElementType CONCAT = new LuauTokenType("..");
  IElementType CONCAT_EQ = new LuauTokenType("..=");
  IElementType DIV = new LuauTokenType("/");
  IElementType DIV_EQ = new LuauTokenType("/=");
  IElementType DO = new LuauTokenType("do");
  IElementType DOC_BLOCK_COMMENT = new LuauTokenType("DOC_BLOCK_COMMENT");
  IElementType DOC_COMMENT = new LuauTokenType("DOC_COMMENT");
  IElementType DOT = new LuauTokenType(".");
  IElementType DOUBLE_COLON = new LuauTokenType("::");
  IElementType DOUBLE_DIV = new LuauTokenType("//");
  IElementType DOUBLE_DIV_EQ = new LuauTokenType("//=");
  IElementType ELLIPSIS = new LuauTokenType("...");
  IElementType ELSE = new LuauTokenType("else");
  IElementType ELSEIF = new LuauTokenType("elseif");
  IElementType END = new LuauTokenType("end");
  IElementType ENDREGION = new LuauTokenType("ENDREGION");
  IElementType EQ = new LuauTokenType("==");
  IElementType EXP = new LuauTokenType("^");
  IElementType EXP_EQ = new LuauTokenType("^=");
  IElementType FALSE = new LuauTokenType("false");
  IElementType FOR = new LuauTokenType("for");
  IElementType FUNCTION = new LuauTokenType("function");
  IElementType GE = new LuauTokenType(">=");
  IElementType GETN = new LuauTokenType("#");
  IElementType GT = new LuauTokenType(">");
  IElementType ID = new LuauTokenType("ID");
  IElementType IF = new LuauTokenType("if");
  IElementType IN = new LuauTokenType("in");
  IElementType INTERSECTION = new LuauTokenType("&");
  IElementType LBRACK = new LuauTokenType("[");
  IElementType LCURLY = new LuauTokenType("{");
  IElementType LE = new LuauTokenType("<=");
  IElementType LOCAL = new LuauTokenType("local");
  IElementType LPAREN = new LuauTokenType("(");
  IElementType LT = new LuauTokenType("<");
  IElementType MINUS = new LuauTokenType("-");
  IElementType MINUS_EQ = new LuauTokenType("-=");
  IElementType MOD = new LuauTokenType("%");
  IElementType MOD_EQ = new LuauTokenType("%=");
  IElementType MULT = new LuauTokenType("*");
  IElementType MULT_EQ = new LuauTokenType("*=");
  IElementType NE = new LuauTokenType("~=");
  IElementType NIL = new LuauTokenType("nil");
  IElementType NOT = new LuauTokenType("not");
  IElementType NUMBER = new LuauTokenType("NUMBER");
  IElementType OR = new LuauTokenType("or");
  IElementType PLUS = new LuauTokenType("+");
  IElementType PLUS_EQ = new LuauTokenType("+=");
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
  IElementType TEMPLATE_STRING_EQUOTE = new LuauTokenType("TEMPLATE_STRING_EQUOTE");
  IElementType TEMPLATE_STRING_SQUOTE = new LuauTokenType("TEMPLATE_STRING_SQUOTE");
  IElementType THEN = new LuauTokenType("then");
  IElementType TRUE = new LuauTokenType("true");
  IElementType UNION = new LuauTokenType("|");
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
      else if (type == BIN_OP) {
        return new LuauBinOpImpl(node);
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
      else if (type == CLOSURE_EXP) {
        return new LuauClosureExpImpl(node);
      }
      else if (type == COMPOUND_OP) {
        return new LuauCompoundOpImpl(node);
      }
      else if (type == CONTINUE_SOFT_KEYWORD) {
        return new LuauContinueSoftKeywordImpl(node);
      }
      else if (type == DO_STATEMENT) {
        return new LuauDoStatementImpl(node);
      }
      else if (type == EXPORT_SOFT_KEYWORD) {
        return new LuauExportSoftKeywordImpl(node);
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
      else if (type == FIELD_LIST) {
        return new LuauFieldListImpl(node);
      }
      else if (type == FIELD_SEP) {
        return new LuauFieldSepImpl(node);
      }
      else if (type == FOREACH_STATEMENT) {
        return new LuauForeachStatementImpl(node);
      }
      else if (type == FUNCTION_CALL) {
        return new LuauFunctionCallImpl(node);
      }
      else if (type == FUNCTION_TYPE) {
        return new LuauFunctionTypeImpl(node);
      }
      else if (type == FUNC_ARGS) {
        return new LuauFuncArgsImpl(node);
      }
      else if (type == FUNC_BODY) {
        return new LuauFuncBodyImpl(node);
      }
      else if (type == FUNC_DEF_STATEMENT) {
        return new LuauFuncDefStatementImpl(node);
      }
      else if (type == FUNC_NAME) {
        return new LuauFuncNameImpl(node);
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
      else if (type == IFELSE_EXP) {
        return new LuauIfelseExpImpl(node);
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
      else if (type == LOCAL_FUNC_DEF_STATEMENT) {
        return new LuauLocalFuncDefStatementImpl(node);
      }
      else if (type == PAR_LIST) {
        return new LuauParListImpl(node);
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
      else if (type == TABLE_CONSTRUCTOR) {
        return new LuauTableConstructorImpl(node);
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
      else if (type == TEMPLATE_STRING) {
        return new LuauTemplateStringImpl(node);
      }
      else if (type == TYPE) {
        return new LuauTypeImpl(node);
      }
      else if (type == TYPE_DECLARATION_STATEMENT) {
        return new LuauTypeDeclarationStatementImpl(node);
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
      else if (type == TYPE_SOFT_KEYWORD) {
        return new LuauTypeSoftKeywordImpl(node);
      }
      else if (type == UNARY_EXP) {
        return new LuauUnaryExpImpl(node);
      }
      else if (type == UNION_SUFFIX) {
        return new LuauUnionSuffixImpl(node);
      }
      else if (type == UN_OP) {
        return new LuauUnOpImpl(node);
      }
      else if (type == VAR) {
        return new LuauVarImpl(node);
      }
      else if (type == VARIADIC_TYPE_PACK) {
        return new LuauVariadicTypePackImpl(node);
      }
      else if (type == VAR_LIST) {
        return new LuauVarListImpl(node);
      }
      else if (type == WHILE_STATEMENT) {
        return new LuauWhileStatementImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
