// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.aleksandrsl.intellijluau.psi.impl.*;

public interface LuauTypes {

  IElementType AND_EXPR = new LuauElementType("AND_EXPR");
  IElementType ASSIGNMENT_STATEMENT = new LuauElementType("ASSIGNMENT_STATEMENT");
  IElementType AS_EXPR = new LuauElementType("AS_EXPR");
  IElementType ATTRIBUTE = new LuauElementType("ATTRIBUTE");
  IElementType ATTRIBUTES = new LuauElementType("ATTRIBUTES");
  IElementType BINDING = new LuauElementType("BINDING");
  IElementType BINDING_LIST = new LuauElementType("BINDING_LIST");
  IElementType BLOCK = new LuauElementType("BLOCK");
  IElementType BOUND_TYPE_LIST = new LuauElementType("BOUND_TYPE_LIST");
  IElementType CLASSIC_FOR_STATEMENT = new LuauElementType("CLASSIC_FOR_STATEMENT");
  IElementType CLOSURE_EXPR = new LuauElementType("CLOSURE_EXPR");
  IElementType COMPARISON_EXPR = new LuauElementType("COMPARISON_EXPR");
  IElementType COMPOUND_OP_STATEMENT = new LuauElementType("COMPOUND_OP_STATEMENT");
  IElementType COMPUTED_KEY = new LuauElementType("COMPUTED_KEY");
  IElementType CONCAT_EXPR = new LuauElementType("CONCAT_EXPR");
  IElementType CONTINUE_SOFT_KEYWORD = new LuauElementType("CONTINUE_SOFT_KEYWORD");
  IElementType DIV_EXPR = new LuauElementType("DIV_EXPR");
  IElementType DO_STATEMENT = new LuauElementType("DO_STATEMENT");
  IElementType EXPORT_SOFT_KEYWORD = new LuauElementType("EXPORT_SOFT_KEYWORD");
  IElementType EXPRESSION = new LuauElementType("EXPRESSION");
  IElementType EXPRESSION_STATEMENT = new LuauElementType("EXPRESSION_STATEMENT");
  IElementType EXP_EXPR = new LuauElementType("EXP_EXPR");
  IElementType EXP_LIST = new LuauElementType("EXP_LIST");
  IElementType FIELD = new LuauElementType("FIELD");
  IElementType FLOOR_DIV_EXPR = new LuauElementType("FLOOR_DIV_EXPR");
  IElementType FOREACH_STATEMENT = new LuauElementType("FOREACH_STATEMENT");
  IElementType FUNCTION_TYPE = new LuauElementType("FUNCTION_TYPE");
  IElementType FUNC_ARGS = new LuauElementType("FUNC_ARGS");
  IElementType FUNC_BODY = new LuauElementType("FUNC_BODY");
  IElementType FUNC_CALL = new LuauElementType("FUNC_CALL");
  IElementType FUNC_DEF_STATEMENT = new LuauElementType("FUNC_DEF_STATEMENT");
  IElementType FUNC_TYPE_PARAMS = new LuauElementType("FUNC_TYPE_PARAMS");
  IElementType GENERIC_TYPE_DECLARATION = new LuauElementType("GENERIC_TYPE_DECLARATION");
  IElementType GENERIC_TYPE_LIST = new LuauElementType("GENERIC_TYPE_LIST");
  IElementType GENERIC_TYPE_LIST_WITH_DEFAULTS = new LuauElementType("GENERIC_TYPE_LIST_WITH_DEFAULTS");
  IElementType GENERIC_TYPE_PACK = new LuauElementType("GENERIC_TYPE_PACK");
  IElementType GENERIC_TYPE_PACK_PARAMETER = new LuauElementType("GENERIC_TYPE_PACK_PARAMETER");
  IElementType GENERIC_TYPE_PACK_PARAMETER_WITH_DEFAULT = new LuauElementType("GENERIC_TYPE_PACK_PARAMETER_WITH_DEFAULT");
  IElementType GENERIC_TYPE_WITH_DEFAULT_DECLARATION = new LuauElementType("GENERIC_TYPE_WITH_DEFAULT_DECLARATION");
  IElementType IFELSE_EXPR = new LuauElementType("IFELSE_EXPR");
  IElementType IF_STATEMENT = new LuauElementType("IF_STATEMENT");
  IElementType INDEXED_FIELD = new LuauElementType("INDEXED_FIELD");
  IElementType INDEX_ACCESS = new LuauElementType("INDEX_ACCESS");
  IElementType INTERSECTION_TYPE = new LuauElementType("INTERSECTION_TYPE");
  IElementType KEYED_FIELD = new LuauElementType("KEYED_FIELD");
  IElementType LAST_STATEMENT = new LuauElementType("LAST_STATEMENT");
  IElementType LENGTH_EXPR = new LuauElementType("LENGTH_EXPR");
  IElementType LIST_ARGS = new LuauElementType("LIST_ARGS");
  IElementType LITERAL_EXPR = new LuauElementType("LITERAL_EXPR");
  IElementType LITERAL_FIELD = new LuauElementType("LITERAL_FIELD");
  IElementType LITERAL_TABLE = new LuauElementType("LITERAL_TABLE");
  IElementType LOCAL_DEF_STATEMENT = new LuauElementType("LOCAL_DEF_STATEMENT");
  IElementType LOCAL_FUNC_DEF_STATEMENT = new LuauElementType("LOCAL_FUNC_DEF_STATEMENT");
  IElementType LVALUE = new LuauElementType("LVALUE");
  IElementType METHOD_DEF_STATEMENT = new LuauElementType("METHOD_DEF_STATEMENT");
  IElementType METHOD_NAME = new LuauElementType("METHOD_NAME");
  IElementType MINUS_EXPR = new LuauElementType("MINUS_EXPR");
  IElementType MOD_EXPR = new LuauElementType("MOD_EXPR");
  IElementType MUL_EXPR = new LuauElementType("MUL_EXPR");
  IElementType NOT_EXPR = new LuauElementType("NOT_EXPR");
  IElementType OPERATOR = new LuauElementType("OPERATOR");
  IElementType OR_EXPR = new LuauElementType("OR_EXPR");
  IElementType PARAMETERS = new LuauElementType("PARAMETERS");
  IElementType PARAMETRIZED_ATTRIBUTE = new LuauElementType("PARAMETRIZED_ATTRIBUTE");
  IElementType PARENTHESISED_TYPE = new LuauElementType("PARENTHESISED_TYPE");
  IElementType PAREN_EXPR = new LuauElementType("PAREN_EXPR");
  IElementType PAR_LIST = new LuauElementType("PAR_LIST");
  IElementType PLUS_EXPR = new LuauElementType("PLUS_EXPR");
  IElementType PRIMARY_GROUP_EXPR = new LuauElementType("PRIMARY_GROUP_EXPR");
  IElementType READ_SOFT_KEYWORD = new LuauElementType("READ_SOFT_KEYWORD");
  IElementType REPEAT_STATEMENT = new LuauElementType("REPEAT_STATEMENT");
  IElementType RETURN_STATEMENT = new LuauElementType("RETURN_STATEMENT");
  IElementType RETURN_TYPE = new LuauElementType("RETURN_TYPE");
  IElementType ROOT_BLOCK = new LuauElementType("ROOT_BLOCK");
  IElementType SHEBANG_LINE = new LuauElementType("SHEBANG_LINE");
  IElementType SIMPLE_REFERENCE = new LuauElementType("SIMPLE_REFERENCE");
  IElementType SIMPLE_TYPE_REFERENCE = new LuauElementType("SIMPLE_TYPE_REFERENCE");
  IElementType SINGLETON_TYPE = new LuauElementType("SINGLETON_TYPE");
  IElementType SINGLE_ARG = new LuauElementType("SINGLE_ARG");
  IElementType STRING_KEYED_FIELD = new LuauElementType("STRING_KEYED_FIELD");
  IElementType TABLE_CONSTRUCTOR = new LuauElementType("TABLE_CONSTRUCTOR");
  IElementType TABLE_CONSTRUCTOR_EXPR = new LuauElementType("TABLE_CONSTRUCTOR_EXPR");
  IElementType TABLE_TYPE = new LuauElementType("TABLE_TYPE");
  IElementType TEMPLATE_STRING_EXPR = new LuauElementType("TEMPLATE_STRING_EXPR");
  IElementType TYPE = new LuauElementType("TYPE");
  IElementType TYPEOF_SOFT_KEYWORD = new LuauElementType("TYPEOF_SOFT_KEYWORD");
  IElementType TYPEOF_TYPE = new LuauElementType("TYPEOF_TYPE");
  IElementType TYPE_COMPUTED_KEY = new LuauElementType("TYPE_COMPUTED_KEY");
  IElementType TYPE_DECLARATION_STATEMENT = new LuauElementType("TYPE_DECLARATION_STATEMENT");
  IElementType TYPE_FIELD = new LuauElementType("TYPE_FIELD");
  IElementType TYPE_FUNCTION_DECLARATION_STATEMENT = new LuauElementType("TYPE_FUNCTION_DECLARATION_STATEMENT");
  IElementType TYPE_KEYED_FIELD = new LuauElementType("TYPE_KEYED_FIELD");
  IElementType TYPE_LIST = new LuauElementType("TYPE_LIST");
  IElementType TYPE_PACK = new LuauElementType("TYPE_PACK");
  IElementType TYPE_PARAMS = new LuauElementType("TYPE_PARAMS");
  IElementType TYPE_REFERENCE = new LuauElementType("TYPE_REFERENCE");
  IElementType TYPE_SOFT_KEYWORD = new LuauElementType("TYPE_SOFT_KEYWORD");
  IElementType TYPE_STRING_KEYED_FIELD = new LuauElementType("TYPE_STRING_KEYED_FIELD");
  IElementType UNARY_MIN_EXPR = new LuauElementType("UNARY_MIN_EXPR");
  IElementType UNION_TYPE = new LuauElementType("UNION_TYPE");
  IElementType VARIADIC_TYPE_PACK = new LuauElementType("VARIADIC_TYPE_PACK");
  IElementType VAR_LIST = new LuauElementType("VAR_LIST");
  IElementType WHILE_STATEMENT = new LuauElementType("WHILE_STATEMENT");
  IElementType WRITE_SOFT_KEYWORD = new LuauElementType("WRITE_SOFT_KEYWORD");

  IElementType AND = new LuauTokenType("and");
  IElementType ARROW = new LuauTokenType("->");
  IElementType ASSIGN = new LuauTokenType("=");
  IElementType AT = new LuauTokenType("@");
  IElementType BLOCK_COMMENT = new LuauTokenType("BLOCK_COMMENT");
  IElementType BREAK = new LuauTokenType("break");
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
      if (type == AND_EXPR) {
        return new LuauAndExprImpl(node);
      }
      else if (type == ASSIGNMENT_STATEMENT) {
        return new LuauAssignmentStatementImpl(node);
      }
      else if (type == AS_EXPR) {
        return new LuauAsExprImpl(node);
      }
      else if (type == ATTRIBUTE) {
        return new LuauAttributeImpl(node);
      }
      else if (type == ATTRIBUTES) {
        return new LuauAttributesImpl(node);
      }
      else if (type == BINDING) {
        return new LuauBindingImpl(node);
      }
      else if (type == BINDING_LIST) {
        return new LuauBindingListImpl(node);
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
      else if (type == COMPARISON_EXPR) {
        return new LuauComparisonExprImpl(node);
      }
      else if (type == COMPOUND_OP_STATEMENT) {
        return new LuauCompoundOpStatementImpl(node);
      }
      else if (type == COMPUTED_KEY) {
        return new LuauComputedKeyImpl(node);
      }
      else if (type == CONCAT_EXPR) {
        return new LuauConcatExprImpl(node);
      }
      else if (type == CONTINUE_SOFT_KEYWORD) {
        return new LuauContinueSoftKeywordImpl(node);
      }
      else if (type == DIV_EXPR) {
        return new LuauDivExprImpl(node);
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
      else if (type == EXP_EXPR) {
        return new LuauExpExprImpl(node);
      }
      else if (type == EXP_LIST) {
        return new LuauExpListImpl(node);
      }
      else if (type == FIELD) {
        return new LuauFieldImpl(node);
      }
      else if (type == FLOOR_DIV_EXPR) {
        return new LuauFloorDivExprImpl(node);
      }
      else if (type == FOREACH_STATEMENT) {
        return new LuauForeachStatementImpl(node);
      }
      else if (type == FUNCTION_TYPE) {
        return new LuauFunctionTypeImpl(node);
      }
      else if (type == FUNC_BODY) {
        return new LuauFuncBodyImpl(node);
      }
      else if (type == FUNC_CALL) {
        return new LuauFuncCallImpl(node);
      }
      else if (type == FUNC_DEF_STATEMENT) {
        return new LuauFuncDefStatementImpl(node);
      }
      else if (type == FUNC_TYPE_PARAMS) {
        return new LuauFuncTypeParamsImpl(node);
      }
      else if (type == GENERIC_TYPE_DECLARATION) {
        return new LuauGenericTypeDeclarationImpl(node);
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
      else if (type == GENERIC_TYPE_WITH_DEFAULT_DECLARATION) {
        return new LuauGenericTypeWithDefaultDeclarationImpl(node);
      }
      else if (type == IFELSE_EXPR) {
        return new LuauIfelseExprImpl(node);
      }
      else if (type == IF_STATEMENT) {
        return new LuauIfStatementImpl(node);
      }
      else if (type == INDEXED_FIELD) {
        return new LuauIndexedFieldImpl(node);
      }
      else if (type == INDEX_ACCESS) {
        return new LuauIndexAccessImpl(node);
      }
      else if (type == INTERSECTION_TYPE) {
        return new LuauIntersectionTypeImpl(node);
      }
      else if (type == KEYED_FIELD) {
        return new LuauKeyedFieldImpl(node);
      }
      else if (type == LAST_STATEMENT) {
        return new LuauLastStatementImpl(node);
      }
      else if (type == LENGTH_EXPR) {
        return new LuauLengthExprImpl(node);
      }
      else if (type == LIST_ARGS) {
        return new LuauListArgsImpl(node);
      }
      else if (type == LITERAL_EXPR) {
        return new LuauLiteralExprImpl(node);
      }
      else if (type == LITERAL_FIELD) {
        return new LuauLiteralFieldImpl(node);
      }
      else if (type == LITERAL_TABLE) {
        return new LuauLiteralTableImpl(node);
      }
      else if (type == LOCAL_DEF_STATEMENT) {
        return new LuauLocalDefStatementImpl(node);
      }
      else if (type == LOCAL_FUNC_DEF_STATEMENT) {
        return new LuauLocalFuncDefStatementImpl(node);
      }
      else if (type == LVALUE) {
        return new LuauLvalueImpl(node);
      }
      else if (type == METHOD_DEF_STATEMENT) {
        return new LuauMethodDefStatementImpl(node);
      }
      else if (type == METHOD_NAME) {
        return new LuauMethodNameImpl(node);
      }
      else if (type == MINUS_EXPR) {
        return new LuauMinusExprImpl(node);
      }
      else if (type == MOD_EXPR) {
        return new LuauModExprImpl(node);
      }
      else if (type == MUL_EXPR) {
        return new LuauMulExprImpl(node);
      }
      else if (type == NOT_EXPR) {
        return new LuauNotExprImpl(node);
      }
      else if (type == OPERATOR) {
        return new LuauOperatorImpl(node);
      }
      else if (type == OR_EXPR) {
        return new LuauOrExprImpl(node);
      }
      else if (type == PARAMETERS) {
        return new LuauParametersImpl(node);
      }
      else if (type == PARAMETRIZED_ATTRIBUTE) {
        return new LuauParametrizedAttributeImpl(node);
      }
      else if (type == PARENTHESISED_TYPE) {
        return new LuauParenthesisedTypeImpl(node);
      }
      else if (type == PAREN_EXPR) {
        return new LuauParenExprImpl(node);
      }
      else if (type == PAR_LIST) {
        return new LuauParListImpl(node);
      }
      else if (type == PLUS_EXPR) {
        return new LuauPlusExprImpl(node);
      }
      else if (type == PRIMARY_GROUP_EXPR) {
        return new LuauPrimaryGroupExprImpl(node);
      }
      else if (type == READ_SOFT_KEYWORD) {
        return new LuauReadSoftKeywordImpl(node);
      }
      else if (type == REPEAT_STATEMENT) {
        return new LuauRepeatStatementImpl(node);
      }
      else if (type == RETURN_STATEMENT) {
        return new LuauReturnStatementImpl(node);
      }
      else if (type == RETURN_TYPE) {
        return new LuauReturnTypeImpl(node);
      }
      else if (type == ROOT_BLOCK) {
        return new LuauRootBlockImpl(node);
      }
      else if (type == SHEBANG_LINE) {
        return new LuauShebangLineImpl(node);
      }
      else if (type == SIMPLE_REFERENCE) {
        return new LuauSimpleReferenceImpl(node);
      }
      else if (type == SIMPLE_TYPE_REFERENCE) {
        return new LuauSimpleTypeReferenceImpl(node);
      }
      else if (type == SINGLETON_TYPE) {
        return new LuauSingletonTypeImpl(node);
      }
      else if (type == SINGLE_ARG) {
        return new LuauSingleArgImpl(node);
      }
      else if (type == STRING_KEYED_FIELD) {
        return new LuauStringKeyedFieldImpl(node);
      }
      else if (type == TABLE_CONSTRUCTOR) {
        return new LuauTableConstructorImpl(node);
      }
      else if (type == TABLE_CONSTRUCTOR_EXPR) {
        return new LuauTableConstructorExprImpl(node);
      }
      else if (type == TABLE_TYPE) {
        return new LuauTableTypeImpl(node);
      }
      else if (type == TEMPLATE_STRING_EXPR) {
        return new LuauTemplateStringExprImpl(node);
      }
      else if (type == TYPE) {
        return new LuauTypeImpl(node);
      }
      else if (type == TYPEOF_SOFT_KEYWORD) {
        return new LuauTypeofSoftKeywordImpl(node);
      }
      else if (type == TYPEOF_TYPE) {
        return new LuauTypeofTypeImpl(node);
      }
      else if (type == TYPE_COMPUTED_KEY) {
        return new LuauTypeComputedKeyImpl(node);
      }
      else if (type == TYPE_DECLARATION_STATEMENT) {
        return new LuauTypeDeclarationStatementImpl(node);
      }
      else if (type == TYPE_FIELD) {
        return new LuauTypeFieldImpl(node);
      }
      else if (type == TYPE_FUNCTION_DECLARATION_STATEMENT) {
        return new LuauTypeFunctionDeclarationStatementImpl(node);
      }
      else if (type == TYPE_KEYED_FIELD) {
        return new LuauTypeKeyedFieldImpl(node);
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
      else if (type == TYPE_REFERENCE) {
        return new LuauTypeReferenceImpl(node);
      }
      else if (type == TYPE_SOFT_KEYWORD) {
        return new LuauTypeSoftKeywordImpl(node);
      }
      else if (type == TYPE_STRING_KEYED_FIELD) {
        return new LuauTypeStringKeyedFieldImpl(node);
      }
      else if (type == UNARY_MIN_EXPR) {
        return new LuauUnaryMinExprImpl(node);
      }
      else if (type == UNION_TYPE) {
        return new LuauUnionTypeImpl(node);
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
      else if (type == WRITE_SOFT_KEYWORD) {
        return new LuauWriteSoftKeywordImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
