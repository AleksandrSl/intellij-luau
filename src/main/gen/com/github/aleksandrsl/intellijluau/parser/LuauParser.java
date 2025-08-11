// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.aleksandrsl.intellijluau.psi.LuauTypes.*;
import static com.github.aleksandrsl.intellijluau.parser.LuauParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class LuauParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    boolean r;
    if (t == EXPRESSION) {
      r = expression(b, l + 1, -1);
    }
    else {
      r = luau_file(b, l + 1);
    }
    return r;
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(FUNC_ARGS, LIST_ARGS, SINGLE_ARG),
    create_token_set_(GENERIC_TYPE_DECLARATION, GENERIC_TYPE_PACK_PARAMETER, GENERIC_TYPE_PACK_PARAMETER_WITH_DEFAULT, GENERIC_TYPE_WITH_DEFAULT_DECLARATION),
    create_token_set_(AND_EXPR, AS_EXPR, CLOSURE_EXPR, COMPARISON_EXPR,
      CONCAT_EXPR, DIV_EXPR, EXPRESSION, EXP_EXPR,
      FLOOR_DIV_EXPR, IFELSE_EXPR, LENGTH_EXPR, LITERAL_EXPR,
      MINUS_EXPR, MOD_EXPR, MUL_EXPR, NOT_EXPR,
      OR_EXPR, PAREN_EXPR, PLUS_EXPR, PRIMARY_GROUP_EXPR,
      TABLE_CONSTRUCTOR_EXPR, TEMPLATE_STRING_EXPR, UNARY_MIN_EXPR),
  };

  /* ********************************************************** */
  // 'and'
  public static boolean and_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "and_op")) return false;
    if (!nextTokenIs(b, AND)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, AND);
    exit_section_(b, m, OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // !(exp_first | ')' | 'end' | '}' | statement_first | last_statement_first | ';' | TEMPLATE_STRING_EQUOTE)
  static boolean arg_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arg_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !arg_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // exp_first | ')' | 'end' | '}' | statement_first | last_statement_first | ';' | TEMPLATE_STRING_EQUOTE
  private static boolean arg_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arg_recover_0")) return false;
    boolean r;
    r = exp_first(b, l + 1);
    if (!r) r = consumeTokenFast(b, RPAREN);
    if (!r) r = consumeTokenFast(b, END);
    if (!r) r = consumeTokenFast(b, RCURLY);
    if (!r) r = statement_first(b, l + 1);
    if (!r) r = last_statement_first(b, l + 1);
    if (!r) r = consumeTokenFast(b, SEMI);
    if (!r) r = consumeTokenFast(b, TEMPLATE_STRING_EQUOTE);
    return r;
  }

  /* ********************************************************** */
  // !')' expression (',' !')' | &')')
  static boolean arg_with_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arg_with_recover")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = arg_with_recover_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && arg_with_recover_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, LuauParser::arg_recover);
    return r || p;
  }

  // !')'
  private static boolean arg_with_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arg_with_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RPAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ',' !')' | &')'
  private static boolean arg_with_recover_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arg_with_recover_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = arg_with_recover_2_0(b, l + 1);
    if (!r) r = arg_with_recover_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ',' !')'
  private static boolean arg_with_recover_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arg_with_recover_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && arg_with_recover_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !')'
  private static boolean arg_with_recover_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arg_with_recover_2_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RPAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // &')'
  private static boolean arg_with_recover_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arg_with_recover_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RPAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '::' type
  public static boolean as_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "as_expr")) return false;
    if (!nextTokenIsFast(b, DOUBLE_COLON)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _LEFT_, AS_EXPR, null);
    r = consumeTokenFast(b, DOUBLE_COLON);
    p = r; // pin = 1
    r = r && type(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // var_list '=' exp_list
  public static boolean assignment_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignment_statement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ASSIGNMENT_STATEMENT, "<assignment statement>");
    r = var_list(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
    p = r; // pin = 2
    r = r && exp_list(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '@' (ID | '[' parametrized_attribute* ']')
  public static boolean attribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ATTRIBUTE, "<attribute>");
    r = consumeToken(b, AT);
    p = r; // pin = 1
    r = r && attribute_1(b, l + 1);
    exit_section_(b, l, m, r, p, LuauParser::attribute_recover);
    return r || p;
  }

  // ID | '[' parametrized_attribute* ']'
  private static boolean attribute_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    if (!r) r = attribute_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '[' parametrized_attribute* ']'
  private static boolean attribute_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACK);
    r = r && attribute_1_1_1(b, l + 1);
    r = r && consumeToken(b, RBRACK);
    exit_section_(b, m, null, r);
    return r;
  }

  // parametrized_attribute*
  private static boolean attribute_1_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_1_1_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!parametrized_attribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attribute_1_1_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !(statement_first | exp_first)
  static boolean attribute_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !attribute_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // statement_first | exp_first
  private static boolean attribute_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_recover_0")) return false;
    boolean r;
    r = statement_first(b, l + 1);
    if (!r) r = exp_first(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // attribute+
  public static boolean attributes(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attributes")) return false;
    if (!nextTokenIs(b, AT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attribute(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!attribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attributes", c)) break;
    }
    exit_section_(b, m, ATTRIBUTES, r);
    return r;
  }

  /* ********************************************************** */
  // ID (':' type)?
  public static boolean binding(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binding")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    r = r && binding_1(b, l + 1);
    exit_section_(b, m, BINDING, r);
    return r;
  }

  // (':' type)?
  private static boolean binding_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binding_1")) return false;
    binding_1_0(b, l + 1);
    return true;
  }

  // ':' type
  private static boolean binding_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binding_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // <<list binding>>
  public static boolean binding_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binding_list")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = list(b, l + 1, LuauParser::binding);
    exit_section_(b, m, BINDING_LIST, r);
    return r;
  }

  /* ********************************************************** */
  // (statement_with_recover ';'?)* (last_statement ';'?)?
  public static boolean block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BLOCK, "<block>");
    r = block_0(b, l + 1);
    r = r && block_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (statement_with_recover ';'?)*
  private static boolean block_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!block_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "block_0", c)) break;
    }
    return true;
  }

  // statement_with_recover ';'?
  private static boolean block_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = statement_with_recover(b, l + 1);
    r = r && block_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ';'?
  private static boolean block_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_0_0_1")) return false;
    consumeToken(b, SEMI);
    return true;
  }

  // (last_statement ';'?)?
  private static boolean block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_1")) return false;
    block_1_0(b, l + 1);
    return true;
  }

  // last_statement ';'?
  private static boolean block_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = last_statement(b, l + 1);
    r = r && block_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ';'?
  private static boolean block_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_1_0_1")) return false;
    consumeToken(b, SEMI);
    return true;
  }

  /* ********************************************************** */
  // variadic_type_pack | generic_type_pack | <<list ((ID ':')? type !'...')>> (',' (variadic_type_pack | generic_type_pack))?
  public static boolean bound_type_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BOUND_TYPE_LIST, "<bound type list>");
    r = variadic_type_pack(b, l + 1);
    if (!r) r = generic_type_pack(b, l + 1);
    if (!r) r = bound_type_list_2(b, l + 1);
    exit_section_(b, l, m, r, false, LuauParser::bound_type_list_recover);
    return r;
  }

  // <<list ((ID ':')? type !'...')>> (',' (variadic_type_pack | generic_type_pack))?
  private static boolean bound_type_list_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = list(b, l + 1, LuauParser::bound_type_list_2_0_0);
    r = r && bound_type_list_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (ID ':')? type !'...'
  private static boolean bound_type_list_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_2_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bound_type_list_2_0_0_0(b, l + 1);
    r = r && type(b, l + 1);
    r = r && bound_type_list_2_0_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (ID ':')?
  private static boolean bound_type_list_2_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_2_0_0_0")) return false;
    bound_type_list_2_0_0_0_0(b, l + 1);
    return true;
  }

  // ID ':'
  private static boolean bound_type_list_2_0_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_2_0_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, COLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // !'...'
  private static boolean bound_type_list_2_0_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_2_0_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, ELLIPSIS);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (',' (variadic_type_pack | generic_type_pack))?
  private static boolean bound_type_list_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_2_1")) return false;
    bound_type_list_2_1_0(b, l + 1);
    return true;
  }

  // ',' (variadic_type_pack | generic_type_pack)
  private static boolean bound_type_list_2_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_2_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && bound_type_list_2_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // variadic_type_pack | generic_type_pack
  private static boolean bound_type_list_2_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_2_1_0_1")) return false;
    boolean r;
    r = variadic_type_pack(b, l + 1);
    if (!r) r = generic_type_pack(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // !(')')
  static boolean bound_type_list_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !bound_type_list_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (')')
  private static boolean bound_type_list_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'for' binding '=' expression ',' expression (',' expression)? 'do' block 'end'
  public static boolean classic_for_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classic_for_statement")) return false;
    if (!nextTokenIs(b, FOR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CLASSIC_FOR_STATEMENT, null);
    r = consumeToken(b, FOR);
    r = r && binding(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
    p = r; // pin = 3
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && report_error_(b, expression(b, l + 1, -1)) && r;
    r = p && report_error_(b, classic_for_statement_6(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, DO)) && r;
    r = p && report_error_(b, block(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (',' expression)?
  private static boolean classic_for_statement_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classic_for_statement_6")) return false;
    classic_for_statement_6_0(b, l + 1);
    return true;
  }

  // ',' expression
  private static boolean classic_for_statement_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classic_for_statement_6_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // attributes? 'function' func_body
  public static boolean closure_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "closure_expr")) return false;
    if (!nextTokenIsFast(b, AT, FUNCTION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CLOSURE_EXPR, "<closure expr>");
    r = closure_expr_0(b, l + 1);
    r = r && consumeToken(b, FUNCTION);
    p = r; // pin = 2
    r = r && func_body(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // attributes?
  private static boolean closure_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "closure_expr_0")) return false;
    attributes(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '<' | '<=' | '>' | '>=' | '==' | '~='
  public static boolean comparison_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comparison_op")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPERATOR, "<comparison op>");
    r = consumeToken(b, LT);
    if (!r) r = consumeToken(b, LE);
    if (!r) r = consumeToken(b, GT);
    if (!r) r = consumeToken(b, GE);
    if (!r) r = consumeToken(b, EQ);
    if (!r) r = consumeToken(b, NE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '+=' | '-=' | '*=' | '/=' | '%=' | '^=' | '..=' | '//='
  public static boolean compound_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compound_op")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPERATOR, "<compound op>");
    r = consumeToken(b, PLUS_EQ);
    if (!r) r = consumeToken(b, MINUS_EQ);
    if (!r) r = consumeToken(b, MULT_EQ);
    if (!r) r = consumeToken(b, DIV_EQ);
    if (!r) r = consumeToken(b, MOD_EQ);
    if (!r) r = consumeToken(b, EXP_EQ);
    if (!r) r = consumeToken(b, CONCAT_EQ);
    if (!r) r = consumeToken(b, DOUBLE_DIV_EQ);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // lvalue compound_op expression
  public static boolean compound_op_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compound_op_statement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMPOUND_OP_STATEMENT, "<compound op statement>");
    r = lvalue(b, l + 1);
    r = r && compound_op(b, l + 1);
    p = r; // pin = 2
    r = r && expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '[' expression ']'
  public static boolean computed_key(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "computed_key")) return false;
    if (!nextTokenIs(b, LBRACK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMPUTED_KEY, null);
    r = consumeToken(b, LBRACK);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && consumeToken(b, RBRACK) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '..'
  public static boolean concat_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "concat_op")) return false;
    if (!nextTokenIs(b, CONCAT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CONCAT);
    exit_section_(b, m, OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // 'continue'
  public static boolean continue_soft_keyword(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "continue_soft_keyword")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CONTINUE_SOFT_KEYWORD, "<continue soft keyword>");
    r = consumeToken(b, "continue");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // local_func_def_statement | method_def_statement | func_def_statement | local_def_statement
  static boolean def_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "def_statement")) return false;
    boolean r;
    r = local_func_def_statement(b, l + 1);
    if (!r) r = method_def_statement(b, l + 1);
    if (!r) r = func_def_statement(b, l + 1);
    if (!r) r = local_def_statement(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // '/'
  public static boolean div_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "div_op")) return false;
    if (!nextTokenIs(b, DIV)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DIV);
    exit_section_(b, m, OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // 'do' block 'end'
  public static boolean do_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "do_statement")) return false;
    if (!nextTokenIs(b, DO)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DO_STATEMENT, null);
    r = consumeToken(b, DO);
    p = r; // pin = 1
    r = r && report_error_(b, block(b, l + 1));
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '{' | '(' | 'true' | 'false' | ID | STRING | NUMBER | '#' | 'if' | '-' | 'not' | 'function' | 'nil' | '...' | '@' | TEMPLATE_STRING_SQUOTE
  static boolean exp_first(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exp_first")) return false;
    boolean r;
    r = consumeTokenFast(b, LCURLY);
    if (!r) r = consumeTokenFast(b, LPAREN);
    if (!r) r = consumeTokenFast(b, TRUE);
    if (!r) r = consumeTokenFast(b, FALSE);
    if (!r) r = consumeTokenFast(b, ID);
    if (!r) r = consumeTokenFast(b, STRING);
    if (!r) r = consumeTokenFast(b, NUMBER);
    if (!r) r = consumeTokenFast(b, GETN);
    if (!r) r = consumeTokenFast(b, IF);
    if (!r) r = consumeTokenFast(b, MINUS);
    if (!r) r = consumeTokenFast(b, NOT);
    if (!r) r = consumeTokenFast(b, FUNCTION);
    if (!r) r = consumeTokenFast(b, NIL);
    if (!r) r = consumeTokenFast(b, ELLIPSIS);
    if (!r) r = consumeTokenFast(b, AT);
    if (!r) r = consumeTokenFast(b, TEMPLATE_STRING_SQUOTE);
    return r;
  }

  /* ********************************************************** */
  // <<list expression>>
  public static boolean exp_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exp_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXP_LIST, "<expression list>");
    r = list(b, l + 1, expression_parser_);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'export'
  public static boolean export_soft_keyword(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "export_soft_keyword")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPORT_SOFT_KEYWORD, "<export soft keyword>");
    r = consumeToken(b, "export");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // <<parseExprStatement index_or_call_expr>>
  public static boolean expression_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPRESSION_STATEMENT, "<expression statement>");
    r = parseExprStatement(b, l + 1, LuauParser::index_or_call_expr);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // keyed_field | string_keyed_field | indexed_field
  public static boolean field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FIELD, "<field>");
    r = keyed_field(b, l + 1);
    if (!r) r = string_keyed_field(b, l + 1);
    if (!r) r = indexed_field(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !('}' | '[' | exp_first | statement_first | 'end' | last_statement_first)
  static boolean field_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !field_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '}' | '[' | exp_first | statement_first | 'end' | last_statement_first
  private static boolean field_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_recover_0")) return false;
    boolean r;
    r = consumeTokenFast(b, RCURLY);
    if (!r) r = consumeTokenFast(b, LBRACK);
    if (!r) r = exp_first(b, l + 1);
    if (!r) r = statement_first(b, l + 1);
    if (!r) r = consumeTokenFast(b, END);
    if (!r) r = last_statement_first(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // ',' | ';'
  static boolean field_sep(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_sep")) return false;
    if (!nextTokenIs(b, "", COMMA, SEMI)) return false;
    boolean r;
    r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, SEMI);
    return r;
  }

  /* ********************************************************** */
  // !'}' field (field_sep | &'}')
  static boolean field_with_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_with_recover")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = field_with_recover_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, field(b, l + 1));
    r = p && field_with_recover_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, LuauParser::field_recover);
    return r || p;
  }

  // !'}'
  private static boolean field_with_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_with_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RCURLY);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // field_sep | &'}'
  private static boolean field_with_recover_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_with_recover_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = field_sep(b, l + 1);
    if (!r) r = field_with_recover_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &'}'
  private static boolean field_with_recover_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_with_recover_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RCURLY);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '//'
  public static boolean floor_div_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "floor_div_op")) return false;
    if (!nextTokenIs(b, DOUBLE_DIV)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOUBLE_DIV);
    exit_section_(b, m, OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // 'for' binding_list 'in' exp_list 'do' block 'end'
  public static boolean foreach_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "foreach_statement")) return false;
    if (!nextTokenIs(b, FOR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FOREACH_STATEMENT, null);
    r = consumeToken(b, FOR);
    p = r; // pin = 1
    r = r && report_error_(b, binding_list(b, l + 1));
    r = p && report_error_(b, consumeToken(b, IN)) && r;
    r = p && report_error_(b, exp_list(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, DO)) && r;
    r = p && report_error_(b, block(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // list_args | single_arg
  public static boolean func_args(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_args")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, FUNC_ARGS, "<func args>");
    r = list_args(b, l + 1);
    if (!r) r = single_arg(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // func_type_params? par_list (':' return_type)? block 'end'
  public static boolean func_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_body")) return false;
    if (!nextTokenIs(b, "<func body>", LPAREN, LT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FUNC_BODY, "<func body>");
    r = func_body_0(b, l + 1);
    r = r && par_list(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, func_body_2(b, l + 1));
    r = p && report_error_(b, block(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // func_type_params?
  private static boolean func_body_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_body_0")) return false;
    func_type_params(b, l + 1);
    return true;
  }

  // (':' return_type)?
  private static boolean func_body_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_body_2")) return false;
    func_body_2_0(b, l + 1);
    return true;
  }

  // ':' return_type
  private static boolean func_body_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_body_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && return_type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // func_args | ':' simple_reference func_args
  public static boolean func_call(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_call")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _LEFT_, FUNC_CALL, "<func call>");
    r = func_args(b, l + 1);
    if (!r) r = func_call_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ':' simple_reference func_args
  private static boolean func_call_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_call_1")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, COLON);
    p = r; // pin = 1
    r = r && report_error_(b, simple_reference(b, l + 1));
    r = p && func_args(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // attributes? 'function' ID func_body
  public static boolean func_def_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_def_statement")) return false;
    if (!nextTokenIs(b, "<func def statement>", AT, FUNCTION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FUNC_DEF_STATEMENT, "<func def statement>");
    r = func_def_statement_0(b, l + 1);
    r = r && consumeTokens(b, 1, FUNCTION, ID);
    p = r; // pin = 2
    r = r && func_body(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // attributes?
  private static boolean func_def_statement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_def_statement_0")) return false;
    attributes(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '<' generic_type_list '>'
  public static boolean func_type_params(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_type_params")) return false;
    if (!nextTokenIs(b, LT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FUNC_TYPE_PARAMS, null);
    r = consumeToken(b, LT);
    p = r; // pin = 1
    r = r && report_error_(b, generic_type_list(b, l + 1));
    r = p && consumeToken(b, GT) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // func_type_params? <<isFunctionType>> '(' bound_type_list? ')' '->' return_type
  public static boolean function_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_type")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_TYPE, "<function type>");
    r = function_type_0(b, l + 1);
    r = r && isFunctionType(b, l + 1);
    r = r && consumeToken(b, LPAREN);
    p = r; // pin = 3
    r = r && report_error_(b, function_type_3(b, l + 1));
    r = p && report_error_(b, consumeTokens(b, -1, RPAREN, ARROW)) && r;
    r = p && return_type(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // func_type_params?
  private static boolean function_type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_type_0")) return false;
    func_type_params(b, l + 1);
    return true;
  }

  // bound_type_list?
  private static boolean function_type_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_type_3")) return false;
    bound_type_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ID
  public static boolean generic_type_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_declaration")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, GENERIC_TYPE_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // (<<list (generic_type_declaration !'...')>> (',' | &'>'))? <<list generic_type_pack_parameter>>?
  public static boolean generic_type_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, GENERIC_TYPE_LIST, "<generic type list>");
    r = generic_type_list_0(b, l + 1);
    r = r && generic_type_list_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (<<list (generic_type_declaration !'...')>> (',' | &'>'))?
  private static boolean generic_type_list_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_0")) return false;
    generic_type_list_0_0(b, l + 1);
    return true;
  }

  // <<list (generic_type_declaration !'...')>> (',' | &'>')
  private static boolean generic_type_list_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = list(b, l + 1, LuauParser::generic_type_list_0_0_0_0);
    r = r && generic_type_list_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // generic_type_declaration !'...'
  private static boolean generic_type_list_0_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_0_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = generic_type_declaration(b, l + 1);
    r = r && generic_type_list_0_0_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !'...'
  private static boolean generic_type_list_0_0_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_0_0_0_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, ELLIPSIS);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ',' | &'>'
  private static boolean generic_type_list_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_0_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    if (!r) r = generic_type_list_0_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &'>'
  private static boolean generic_type_list_0_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_0_0_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, GT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // <<list generic_type_pack_parameter>>?
  private static boolean generic_type_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_1")) return false;
    list(b, l + 1, LuauParser::generic_type_pack_parameter);
    return true;
  }

  /* ********************************************************** */
  // (<<list (generic_type_declaration !('=' | '...'))>> (',' | &'>'))?
  //     (<<list generic_type_with_default_declaration>> (',' | &'>') (<<list generic_type_pack_parameter_with_default>> (',' | &'>'))?
  //     | (<<list (generic_type_pack_parameter !'=')>> (',' | &'>'))? (<<list generic_type_pack_parameter_with_default>> (',' | &'>'))?)
  public static boolean generic_type_list_with_defaults(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, GENERIC_TYPE_LIST_WITH_DEFAULTS, "<generic type list with defaults>");
    r = generic_type_list_with_defaults_0(b, l + 1);
    r = r && generic_type_list_with_defaults_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (<<list (generic_type_declaration !('=' | '...'))>> (',' | &'>'))?
  private static boolean generic_type_list_with_defaults_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_0")) return false;
    generic_type_list_with_defaults_0_0(b, l + 1);
    return true;
  }

  // <<list (generic_type_declaration !('=' | '...'))>> (',' | &'>')
  private static boolean generic_type_list_with_defaults_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = list(b, l + 1, LuauParser::generic_type_list_with_defaults_0_0_0_0);
    r = r && generic_type_list_with_defaults_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // generic_type_declaration !('=' | '...')
  private static boolean generic_type_list_with_defaults_0_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_0_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = generic_type_declaration(b, l + 1);
    r = r && generic_type_list_with_defaults_0_0_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !('=' | '...')
  private static boolean generic_type_list_with_defaults_0_0_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_0_0_0_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !generic_type_list_with_defaults_0_0_0_0_1_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '=' | '...'
  private static boolean generic_type_list_with_defaults_0_0_0_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_0_0_0_0_1_0")) return false;
    boolean r;
    r = consumeToken(b, ASSIGN);
    if (!r) r = consumeToken(b, ELLIPSIS);
    return r;
  }

  // ',' | &'>'
  private static boolean generic_type_list_with_defaults_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_0_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    if (!r) r = generic_type_list_with_defaults_0_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &'>'
  private static boolean generic_type_list_with_defaults_0_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_0_0_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, GT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // <<list generic_type_with_default_declaration>> (',' | &'>') (<<list generic_type_pack_parameter_with_default>> (',' | &'>'))?
  //     | (<<list (generic_type_pack_parameter !'=')>> (',' | &'>'))? (<<list generic_type_pack_parameter_with_default>> (',' | &'>'))?
  private static boolean generic_type_list_with_defaults_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = generic_type_list_with_defaults_1_0(b, l + 1);
    if (!r) r = generic_type_list_with_defaults_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // <<list generic_type_with_default_declaration>> (',' | &'>') (<<list generic_type_pack_parameter_with_default>> (',' | &'>'))?
  private static boolean generic_type_list_with_defaults_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = list(b, l + 1, LuauParser::generic_type_with_default_declaration);
    r = r && generic_type_list_with_defaults_1_0_1(b, l + 1);
    r = r && generic_type_list_with_defaults_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ',' | &'>'
  private static boolean generic_type_list_with_defaults_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    if (!r) r = generic_type_list_with_defaults_1_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &'>'
  private static boolean generic_type_list_with_defaults_1_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_0_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, GT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (<<list generic_type_pack_parameter_with_default>> (',' | &'>'))?
  private static boolean generic_type_list_with_defaults_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_0_2")) return false;
    generic_type_list_with_defaults_1_0_2_0(b, l + 1);
    return true;
  }

  // <<list generic_type_pack_parameter_with_default>> (',' | &'>')
  private static boolean generic_type_list_with_defaults_1_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = list(b, l + 1, LuauParser::generic_type_pack_parameter_with_default);
    r = r && generic_type_list_with_defaults_1_0_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ',' | &'>'
  private static boolean generic_type_list_with_defaults_1_0_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_0_2_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    if (!r) r = generic_type_list_with_defaults_1_0_2_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &'>'
  private static boolean generic_type_list_with_defaults_1_0_2_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_0_2_0_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, GT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (<<list (generic_type_pack_parameter !'=')>> (',' | &'>'))? (<<list generic_type_pack_parameter_with_default>> (',' | &'>'))?
  private static boolean generic_type_list_with_defaults_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = generic_type_list_with_defaults_1_1_0(b, l + 1);
    r = r && generic_type_list_with_defaults_1_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (<<list (generic_type_pack_parameter !'=')>> (',' | &'>'))?
  private static boolean generic_type_list_with_defaults_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_1_0")) return false;
    generic_type_list_with_defaults_1_1_0_0(b, l + 1);
    return true;
  }

  // <<list (generic_type_pack_parameter !'=')>> (',' | &'>')
  private static boolean generic_type_list_with_defaults_1_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = list(b, l + 1, LuauParser::generic_type_list_with_defaults_1_1_0_0_0_0);
    r = r && generic_type_list_with_defaults_1_1_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // generic_type_pack_parameter !'='
  private static boolean generic_type_list_with_defaults_1_1_0_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_1_0_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = generic_type_pack_parameter(b, l + 1);
    r = r && generic_type_list_with_defaults_1_1_0_0_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !'='
  private static boolean generic_type_list_with_defaults_1_1_0_0_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_1_0_0_0_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, ASSIGN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ',' | &'>'
  private static boolean generic_type_list_with_defaults_1_1_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_1_0_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    if (!r) r = generic_type_list_with_defaults_1_1_0_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &'>'
  private static boolean generic_type_list_with_defaults_1_1_0_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_1_0_0_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, GT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (<<list generic_type_pack_parameter_with_default>> (',' | &'>'))?
  private static boolean generic_type_list_with_defaults_1_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_1_1")) return false;
    generic_type_list_with_defaults_1_1_1_0(b, l + 1);
    return true;
  }

  // <<list generic_type_pack_parameter_with_default>> (',' | &'>')
  private static boolean generic_type_list_with_defaults_1_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = list(b, l + 1, LuauParser::generic_type_pack_parameter_with_default);
    r = r && generic_type_list_with_defaults_1_1_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ',' | &'>'
  private static boolean generic_type_list_with_defaults_1_1_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_1_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    if (!r) r = generic_type_list_with_defaults_1_1_1_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &'>'
  private static boolean generic_type_list_with_defaults_1_1_1_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_1_1_0_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, GT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ID '...'
  public static boolean generic_type_pack(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_pack")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, ELLIPSIS);
    exit_section_(b, m, GENERIC_TYPE_PACK, r);
    return r;
  }

  /* ********************************************************** */
  // ID '...'
  public static boolean generic_type_pack_parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_pack_parameter")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, ELLIPSIS);
    exit_section_(b, m, GENERIC_TYPE_PACK_PARAMETER, r);
    return r;
  }

  /* ********************************************************** */
  // ID '...' '=' (type_pack | variadic_type_pack | generic_type_pack)
  public static boolean generic_type_pack_parameter_with_default(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_pack_parameter_with_default")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, GENERIC_TYPE_PACK_PARAMETER_WITH_DEFAULT, null);
    r = consumeTokens(b, 3, ID, ELLIPSIS, ASSIGN);
    p = r; // pin = 3
    r = r && generic_type_pack_parameter_with_default_3(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // type_pack | variadic_type_pack | generic_type_pack
  private static boolean generic_type_pack_parameter_with_default_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_pack_parameter_with_default_3")) return false;
    boolean r;
    r = type_pack(b, l + 1);
    if (!r) r = variadic_type_pack(b, l + 1);
    if (!r) r = generic_type_pack(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // ID '=' type
  public static boolean generic_type_with_default_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_with_default_declaration")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, ASSIGN);
    r = r && type(b, l + 1);
    exit_section_(b, m, GENERIC_TYPE_WITH_DEFAULT_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // 'if' expression 'then' block ('elseif' expression 'then' block)* ('else' block)? 'end'
  public static boolean if_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_statement")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IF_STATEMENT, null);
    r = consumeToken(b, IF);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && report_error_(b, consumeToken(b, THEN)) && r;
    r = p && report_error_(b, block(b, l + 1)) && r;
    r = p && report_error_(b, if_statement_4(b, l + 1)) && r;
    r = p && report_error_(b, if_statement_5(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ('elseif' expression 'then' block)*
  private static boolean if_statement_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_statement_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!if_statement_4_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "if_statement_4", c)) break;
    }
    return true;
  }

  // 'elseif' expression 'then' block
  private static boolean if_statement_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_statement_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELSEIF);
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, THEN);
    r = r && block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('else' block)?
  private static boolean if_statement_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_statement_5")) return false;
    if_statement_5_0(b, l + 1);
    return true;
  }

  // 'else' block
  private static boolean if_statement_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_statement_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELSE);
    r = r && block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '[' expression ']' | '.' simple_reference
  public static boolean index_access(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "index_access")) return false;
    if (!nextTokenIs(b, "<index access>", DOT, LBRACK)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _LEFT_, INDEX_ACCESS, "<index access>");
    r = index_access_0(b, l + 1);
    if (!r) r = index_access_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '[' expression ']'
  private static boolean index_access_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "index_access_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LBRACK);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && consumeToken(b, RBRACK) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // '.' simple_reference
  private static boolean index_access_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "index_access_1")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, DOT);
    p = r; // pin = 1
    r = r && simple_reference(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // (simple_reference | paren_expr) (index_access | func_call)*
  static boolean index_or_call_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "index_or_call_expr")) return false;
    if (!nextTokenIsFast(b, ID, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = index_or_call_expr_0(b, l + 1);
    r = r && index_or_call_expr_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // simple_reference | paren_expr
  private static boolean index_or_call_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "index_or_call_expr_0")) return false;
    boolean r;
    r = simple_reference(b, l + 1);
    if (!r) r = paren_expr(b, l + 1);
    return r;
  }

  // (index_access | func_call)*
  private static boolean index_or_call_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "index_or_call_expr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!index_or_call_expr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "index_or_call_expr_1", c)) break;
    }
    return true;
  }

  // index_access | func_call
  private static boolean index_or_call_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "index_or_call_expr_1_0")) return false;
    boolean r;
    r = index_access(b, l + 1);
    if (!r) r = func_call(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // expression
  public static boolean indexed_field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "indexed_field")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, INDEXED_FIELD, "<indexed field>");
    r = expression(b, l + 1, -1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '&' simple_type
  public static boolean intersection_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "intersection_type")) return false;
    if (!nextTokenIs(b, INTERSECTION)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _UPPER_, INTERSECTION_TYPE, null);
    r = consumeToken(b, INTERSECTION);
    r = r && simple_type(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // computed_key '=' expression
  public static boolean keyed_field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keyed_field")) return false;
    if (!nextTokenIs(b, LBRACK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, KEYED_FIELD, null);
    r = computed_key(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, ASSIGN));
    r = p && expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // return_statement | 'break' | continue_soft_keyword
  public static boolean last_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "last_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAST_STATEMENT, "<last statement>");
    r = return_statement(b, l + 1);
    if (!r) r = consumeToken(b, BREAK);
    if (!r) r = continue_soft_keyword(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'return' | 'break' | continue_soft_keyword
  static boolean last_statement_first(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "last_statement_first")) return false;
    boolean r;
    r = consumeTokenFast(b, RETURN);
    if (!r) r = consumeTokenFast(b, BREAK);
    if (!r) r = continue_soft_keyword(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // <<p>> (',' <<p>>)*
  static boolean list(PsiBuilder b, int l, Parser _p) {
    if (!recursion_guard_(b, l, "list")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = _p.parse(b, l);
    r = r && list_1(b, l + 1, _p);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' <<p>>)*
  private static boolean list_1(PsiBuilder b, int l, Parser _p) {
    if (!recursion_guard_(b, l, "list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!list_1_0(b, l + 1, _p)) break;
      if (!empty_element_parsed_guard_(b, "list_1", c)) break;
    }
    return true;
  }

  // ',' <<p>>
  private static boolean list_1_0(PsiBuilder b, int l, Parser _p) {
    if (!recursion_guard_(b, l, "list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && _p.parse(b, l);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '(' arg_with_recover* ')'
  public static boolean list_args(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_args")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LIST_ARGS, null);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, list_args_1(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // arg_with_recover*
  private static boolean list_args_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_args_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!arg_with_recover(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "list_args_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // <<p>> (',' <<p>>)* ','?
  static boolean list_with_trailing_comma(PsiBuilder b, int l, Parser _p) {
    if (!recursion_guard_(b, l, "list_with_trailing_comma")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = _p.parse(b, l);
    r = r && list_with_trailing_comma_1(b, l + 1, _p);
    r = r && list_with_trailing_comma_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' <<p>>)*
  private static boolean list_with_trailing_comma_1(PsiBuilder b, int l, Parser _p) {
    if (!recursion_guard_(b, l, "list_with_trailing_comma_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!list_with_trailing_comma_1_0(b, l + 1, _p)) break;
      if (!empty_element_parsed_guard_(b, "list_with_trailing_comma_1", c)) break;
    }
    return true;
  }

  // ',' <<p>>
  private static boolean list_with_trailing_comma_1_0(PsiBuilder b, int l, Parser _p) {
    if (!recursion_guard_(b, l, "list_with_trailing_comma_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && _p.parse(b, l);
    exit_section_(b, m, null, r);
    return r;
  }

  // ','?
  private static boolean list_with_trailing_comma_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_with_trailing_comma_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // NUMBER | STRING | 'nil' | 'true' | 'false' | literal_table
  static boolean literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<literal>");
    r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NIL);
    if (!r) r = consumeToken(b, TRUE);
    if (!r) r = consumeToken(b, FALSE);
    if (!r) r = literal_table(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // NUMBER | STRING | 'nil' | 'true' | 'false' | '...'
  public static boolean literal_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LITERAL_EXPR, "<literal expr>");
    r = consumeTokenFast(b, NUMBER);
    if (!r) r = consumeTokenFast(b, STRING);
    if (!r) r = consumeTokenFast(b, NIL);
    if (!r) r = consumeTokenFast(b, TRUE);
    if (!r) r = consumeTokenFast(b, FALSE);
    if (!r) r = consumeTokenFast(b, ELLIPSIS);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (ID '=')? literal
  public static boolean literal_field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_field")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LITERAL_FIELD, "<literal field>");
    r = literal_field_0(b, l + 1);
    r = r && literal(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (ID '=')?
  private static boolean literal_field_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_field_0")) return false;
    literal_field_0_0(b, l + 1);
    return true;
  }

  // ID '='
  private static boolean literal_field_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_field_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, ASSIGN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // <<list_with_trailing_comma literal_field>>
  static boolean literal_field_list(PsiBuilder b, int l) {
    return list_with_trailing_comma(b, l + 1, LuauParser::literal_field);
  }

  /* ********************************************************** */
  // '{' literal_field_list? '}'
  public static boolean literal_table(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_table")) return false;
    if (!nextTokenIs(b, LCURLY)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LITERAL_TABLE, null);
    r = consumeToken(b, LCURLY);
    p = r; // pin = 1
    r = r && report_error_(b, literal_table_1(b, l + 1));
    r = p && consumeToken(b, RCURLY) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // literal_field_list?
  private static boolean literal_table_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_table_1")) return false;
    literal_field_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'local' binding_list ('=' exp_list)?
  public static boolean local_def_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_def_statement")) return false;
    if (!nextTokenIs(b, LOCAL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LOCAL_DEF_STATEMENT, null);
    r = consumeToken(b, LOCAL);
    p = r; // pin = 1
    r = r && report_error_(b, binding_list(b, l + 1));
    r = p && local_def_statement_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ('=' exp_list)?
  private static boolean local_def_statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_def_statement_2")) return false;
    local_def_statement_2_0(b, l + 1);
    return true;
  }

  // '=' exp_list
  private static boolean local_def_statement_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_def_statement_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ASSIGN);
    r = r && exp_list(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // attributes? 'local' 'function' ID func_body
  public static boolean local_func_def_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_func_def_statement")) return false;
    if (!nextTokenIs(b, "<local func def statement>", AT, LOCAL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LOCAL_FUNC_DEF_STATEMENT, "<local func def statement>");
    r = local_func_def_statement_0(b, l + 1);
    r = r && consumeTokens(b, 2, LOCAL, FUNCTION, ID);
    p = r; // pin = 3
    r = r && func_body(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // attributes?
  private static boolean local_func_def_statement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_func_def_statement_0")) return false;
    attributes(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // shebang_line? root_block
  static boolean luau_file(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "luau_file")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = luau_file_0(b, l + 1);
    r = r && root_block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // shebang_line?
  private static boolean luau_file_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "luau_file_0")) return false;
    shebang_line(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // <<parseLvalue index_or_call_expr>>
  public static boolean lvalue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lvalue")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LVALUE, "<lvalue>");
    r = parseLvalue(b, l + 1, LuauParser::index_or_call_expr);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // attributes? 'function' method_name func_body
  public static boolean method_def_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_def_statement")) return false;
    if (!nextTokenIs(b, "<method def statement>", AT, FUNCTION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, METHOD_DEF_STATEMENT, "<method def statement>");
    r = method_def_statement_0(b, l + 1);
    r = r && consumeToken(b, FUNCTION);
    r = r && method_name(b, l + 1);
    p = r; // pin = 3
    r = r && func_body(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // attributes?
  private static boolean method_def_statement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_def_statement_0")) return false;
    attributes(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // simple_reference ('.' simple_reference &('.' | ':'))* (':' ID | '.' ID)
  public static boolean method_name(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_name")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = simple_reference(b, l + 1);
    r = r && method_name_1(b, l + 1);
    r = r && method_name_2(b, l + 1);
    exit_section_(b, m, METHOD_NAME, r);
    return r;
  }

  // ('.' simple_reference &('.' | ':'))*
  private static boolean method_name_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_name_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!method_name_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "method_name_1", c)) break;
    }
    return true;
  }

  // '.' simple_reference &('.' | ':')
  private static boolean method_name_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_name_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && simple_reference(b, l + 1);
    r = r && method_name_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &('.' | ':')
  private static boolean method_name_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_name_1_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = method_name_1_0_2_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '.' | ':'
  private static boolean method_name_1_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_name_1_0_2_0")) return false;
    boolean r;
    r = consumeToken(b, DOT);
    if (!r) r = consumeToken(b, COLON);
    return r;
  }

  // ':' ID | '.' ID
  private static boolean method_name_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_name_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseTokens(b, 0, COLON, ID);
    if (!r) r = parseTokens(b, 0, DOT, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '-'
  public static boolean minus_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "minus_op")) return false;
    if (!nextTokenIs(b, MINUS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MINUS);
    exit_section_(b, m, OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // '%'
  public static boolean mod_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mod_op")) return false;
    if (!nextTokenIs(b, MOD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MOD);
    exit_section_(b, m, OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // '*'
  public static boolean mul_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mul_op")) return false;
    if (!nextTokenIs(b, MULT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MULT);
    exit_section_(b, m, OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // 'or'
  public static boolean or_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "or_op")) return false;
    if (!nextTokenIs(b, OR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OR);
    exit_section_(b, m, OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // '(' parameter_with_recover* type_pack_parameter? ')'
  public static boolean par_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "par_list")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PAR_LIST, null);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, par_list_1(b, l + 1));
    r = p && report_error_(b, par_list_2(b, l + 1)) && r;
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // parameter_with_recover*
  private static boolean par_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "par_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!parameter_with_recover(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "par_list_1", c)) break;
    }
    return true;
  }

  // type_pack_parameter?
  private static boolean par_list_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "par_list_2")) return false;
    type_pack_parameter(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // !(ID | '...' | ')' | statement_first | ';' | 'end' | 'until' | 'elseif' | 'else' | last_statement_first)
  static boolean parameter_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !parameter_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ID | '...' | ')' | statement_first | ';' | 'end' | 'until' | 'elseif' | 'else' | last_statement_first
  private static boolean parameter_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_recover_0")) return false;
    boolean r;
    r = consumeTokenFast(b, ID);
    if (!r) r = consumeTokenFast(b, ELLIPSIS);
    if (!r) r = consumeTokenFast(b, RPAREN);
    if (!r) r = statement_first(b, l + 1);
    if (!r) r = consumeTokenFast(b, SEMI);
    if (!r) r = consumeTokenFast(b, END);
    if (!r) r = consumeTokenFast(b, UNTIL);
    if (!r) r = consumeTokenFast(b, ELSEIF);
    if (!r) r = consumeTokenFast(b, ELSE);
    if (!r) r = last_statement_first(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // !(')' | '...') binding (',' !')' | &')')
  static boolean parameter_with_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_with_recover")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = parameter_with_recover_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, binding(b, l + 1));
    r = p && parameter_with_recover_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, LuauParser::parameter_recover);
    return r || p;
  }

  // !(')' | '...')
  private static boolean parameter_with_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_with_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !parameter_with_recover_0_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ')' | '...'
  private static boolean parameter_with_recover_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_with_recover_0_0")) return false;
    boolean r;
    r = consumeToken(b, RPAREN);
    if (!r) r = consumeToken(b, ELLIPSIS);
    return r;
  }

  // ',' !')' | &')'
  private static boolean parameter_with_recover_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_with_recover_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parameter_with_recover_2_0(b, l + 1);
    if (!r) r = parameter_with_recover_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ',' !')'
  private static boolean parameter_with_recover_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_with_recover_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && parameter_with_recover_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !')'
  private static boolean parameter_with_recover_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_with_recover_2_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RPAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // &')'
  private static boolean parameter_with_recover_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_with_recover_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RPAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // parenthesised_parameters | literal_table | STRING
  public static boolean parameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARAMETERS, "<parameters>");
    r = parenthesised_parameters(b, l + 1);
    if (!r) r = literal_table(b, l + 1);
    if (!r) r = consumeToken(b, STRING);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ID parameters? (',' !']' | &']')
  public static boolean parametrized_attribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parametrized_attribute")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PARAMETRIZED_ATTRIBUTE, "<parametrized attribute>");
    r = consumeToken(b, ID);
    p = r; // pin = 1
    r = r && report_error_(b, parametrized_attribute_1(b, l + 1));
    r = p && parametrized_attribute_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, LuauParser::parametrized_attribute_recover);
    return r || p;
  }

  // parameters?
  private static boolean parametrized_attribute_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parametrized_attribute_1")) return false;
    parameters(b, l + 1);
    return true;
  }

  // ',' !']' | &']'
  private static boolean parametrized_attribute_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parametrized_attribute_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parametrized_attribute_2_0(b, l + 1);
    if (!r) r = parametrized_attribute_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ',' !']'
  private static boolean parametrized_attribute_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parametrized_attribute_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && parametrized_attribute_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !']'
  private static boolean parametrized_attribute_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parametrized_attribute_2_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RBRACK);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // &']'
  private static boolean parametrized_attribute_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parametrized_attribute_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RBRACK);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !(']' | statement_first)
  static boolean parametrized_attribute_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parametrized_attribute_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !parametrized_attribute_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ']' | statement_first
  private static boolean parametrized_attribute_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parametrized_attribute_recover_0")) return false;
    boolean r;
    r = consumeTokenFast(b, RBRACK);
    if (!r) r = statement_first(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // '(' expression ')'
  public static boolean paren_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "paren_expr")) return false;
    if (!nextTokenIsFast(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, LPAREN);
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, PAREN_EXPR, r);
    return r;
  }

  /* ********************************************************** */
  // literal (',' !')' | &')')
  static boolean parenthesised_parameter_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesised_parameter_item")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = literal(b, l + 1);
    p = r; // pin = 1
    r = r && parenthesised_parameter_item_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ',' !')' | &')'
  private static boolean parenthesised_parameter_item_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesised_parameter_item_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parenthesised_parameter_item_1_0(b, l + 1);
    if (!r) r = parenthesised_parameter_item_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ',' !')'
  private static boolean parenthesised_parameter_item_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesised_parameter_item_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && parenthesised_parameter_item_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !')'
  private static boolean parenthesised_parameter_item_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesised_parameter_item_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RPAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // &')'
  private static boolean parenthesised_parameter_item_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesised_parameter_item_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RPAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !( ')' | ']' | statement_first)
  static boolean parenthesised_parameter_recovery(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesised_parameter_recovery")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !parenthesised_parameter_recovery_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ')' | ']' | statement_first
  private static boolean parenthesised_parameter_recovery_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesised_parameter_recovery_0")) return false;
    boolean r;
    r = consumeToken(b, RPAREN);
    if (!r) r = consumeToken(b, RBRACK);
    if (!r) r = statement_first(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // parenthesised_parameter_item*
  static boolean parenthesised_parameter_with_recovery(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesised_parameter_with_recovery")) return false;
    Marker m = enter_section_(b, l, _NONE_);
    while (true) {
      int c = current_position_(b);
      if (!parenthesised_parameter_item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "parenthesised_parameter_with_recovery", c)) break;
    }
    exit_section_(b, l, m, true, false, LuauParser::parenthesised_parameter_recovery);
    return true;
  }

  /* ********************************************************** */
  // '(' parenthesised_parameter_with_recovery ')'
  static boolean parenthesised_parameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesised_parameters")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, parenthesised_parameter_with_recovery(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '(' type ')'
  public static boolean parenthesised_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesised_type")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && type(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, PARENTHESISED_TYPE, r);
    return r;
  }

  /* ********************************************************** */
  // '+'
  public static boolean plus_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "plus_op")) return false;
    if (!nextTokenIs(b, PLUS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PLUS);
    exit_section_(b, m, OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // 'read'
  public static boolean read_soft_keyword(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "read_soft_keyword")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, READ_SOFT_KEYWORD, "<read soft keyword>");
    r = consumeToken(b, "read");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'repeat' block 'until' expression
  public static boolean repeat_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "repeat_statement")) return false;
    if (!nextTokenIs(b, REPEAT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, REPEAT_STATEMENT, null);
    r = consumeToken(b, REPEAT);
    p = r; // pin = 1
    r = r && report_error_(b, block(b, l + 1));
    r = p && report_error_(b, consumeToken(b, UNTIL)) && r;
    r = p && expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'return' exp_list?
  public static boolean return_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_statement")) return false;
    if (!nextTokenIs(b, RETURN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, RETURN_STATEMENT, null);
    r = consumeToken(b, RETURN);
    p = r; // pin = 1
    r = r && return_statement_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // exp_list?
  private static boolean return_statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_statement_1")) return false;
    exp_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // variadic_type_pack | generic_type_pack | type | type_pack
  public static boolean return_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RETURN_TYPE, "<return type>");
    r = variadic_type_pack(b, l + 1);
    if (!r) r = generic_type_pack(b, l + 1);
    if (!r) r = type(b, l + 1);
    if (!r) r = type_pack(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (root_statement_with_recover ';'?)* (return_statement ';'?)?
  public static boolean root_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_block")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ROOT_BLOCK, "<root block>");
    r = root_block_0(b, l + 1);
    r = r && root_block_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (root_statement_with_recover ';'?)*
  private static boolean root_block_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_block_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!root_block_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "root_block_0", c)) break;
    }
    return true;
  }

  // root_statement_with_recover ';'?
  private static boolean root_block_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_block_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = root_statement_with_recover(b, l + 1);
    r = r && root_block_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ';'?
  private static boolean root_block_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_block_0_0_1")) return false;
    consumeToken(b, SEMI);
    return true;
  }

  // (return_statement ';'?)?
  private static boolean root_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_block_1")) return false;
    root_block_1_0(b, l + 1);
    return true;
  }

  // return_statement ';'?
  private static boolean root_block_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_block_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = return_statement(b, l + 1);
    r = r && root_block_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ';'?
  private static boolean root_block_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_block_1_0_1")) return false;
    consumeToken(b, SEMI);
    return true;
  }

  /* ********************************************************** */
  // !('return' | <<eof>>) statement
  static boolean root_statement_with_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_statement_with_recover")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = root_statement_with_recover_0(b, l + 1);
    p = r; // pin = 1
    r = r && statement(b, l + 1);
    exit_section_(b, l, m, r, p, LuauParser::statement_recover);
    return r || p;
  }

  // !('return' | <<eof>>)
  private static boolean root_statement_with_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_statement_with_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !root_statement_with_recover_0_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // 'return' | <<eof>>
  private static boolean root_statement_with_recover_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_statement_with_recover_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RETURN);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SHEBANG SHEBANG_CONTENT
  public static boolean shebang_line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shebang_line")) return false;
    if (!nextTokenIs(b, SHEBANG)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SHEBANG, SHEBANG_CONTENT);
    exit_section_(b, m, SHEBANG_LINE, r);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean simple_reference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_reference")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, SIMPLE_REFERENCE, r);
    return r;
  }

  /* ********************************************************** */
  // singleton_type
  //     | typeof_type
  //     | type_reference
  //     | function_type
  //     | parenthesised_type
  //     | table_type
  static boolean simple_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_type")) return false;
    boolean r;
    r = singleton_type(b, l + 1);
    if (!r) r = typeof_type(b, l + 1);
    if (!r) r = type_reference(b, l + 1);
    if (!r) r = function_type(b, l + 1);
    if (!r) r = parenthesised_type(b, l + 1);
    if (!r) r = table_type(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean simple_type_reference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_type_reference")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, SIMPLE_TYPE_REFERENCE, r);
    return r;
  }

  /* ********************************************************** */
  // table_constructor | STRING
  public static boolean single_arg(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_arg")) return false;
    if (!nextTokenIs(b, "<single arg>", LCURLY, STRING)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SINGLE_ARG, "<single arg>");
    r = table_constructor(b, l + 1);
    if (!r) r = consumeToken(b, STRING);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // STRING | 'true' | 'false' | 'nil'
  public static boolean singleton_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "singleton_type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SINGLETON_TYPE, "<singleton type>");
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, TRUE);
    if (!r) r = consumeToken(b, FALSE);
    if (!r) r = consumeToken(b, NIL);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // type_function_declaration_statement
  //      | type_declaration_statement
  //      | do_statement
  //      | while_statement
  //      | repeat_statement
  //      | if_statement
  //      | classic_for_statement
  //      | foreach_statement
  //      | def_statement
  //      | compound_op_statement
  //      | assignment_statement
  //      | expression_statement
  static boolean statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<statement>");
    r = type_function_declaration_statement(b, l + 1);
    if (!r) r = type_declaration_statement(b, l + 1);
    if (!r) r = do_statement(b, l + 1);
    if (!r) r = while_statement(b, l + 1);
    if (!r) r = repeat_statement(b, l + 1);
    if (!r) r = if_statement(b, l + 1);
    if (!r) r = classic_for_statement(b, l + 1);
    if (!r) r = foreach_statement(b, l + 1);
    if (!r) r = def_statement(b, l + 1);
    if (!r) r = compound_op_statement(b, l + 1);
    if (!r) r = assignment_statement(b, l + 1);
    if (!r) r = expression_statement(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'local' | 'do' | 'while' | 'repeat' | 'function' | 'if' | 'for' | export_soft_keyword | type_soft_keyword | ID | '(' | '@'
  static boolean statement_first(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_first")) return false;
    boolean r;
    r = consumeTokenFast(b, LOCAL);
    if (!r) r = consumeTokenFast(b, DO);
    if (!r) r = consumeTokenFast(b, WHILE);
    if (!r) r = consumeTokenFast(b, REPEAT);
    if (!r) r = consumeTokenFast(b, FUNCTION);
    if (!r) r = consumeTokenFast(b, IF);
    if (!r) r = consumeTokenFast(b, FOR);
    if (!r) r = export_soft_keyword(b, l + 1);
    if (!r) r = type_soft_keyword(b, l + 1);
    if (!r) r = consumeTokenFast(b, ID);
    if (!r) r = consumeTokenFast(b, LPAREN);
    if (!r) r = consumeTokenFast(b, AT);
    return r;
  }

  /* ********************************************************** */
  // !(
  //    statement_first | ';' | 'end' | 'until' | 'elseif' | 'else' | last_statement_first
  // )
  static boolean statement_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !statement_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // statement_first | ';' | 'end' | 'until' | 'elseif' | 'else' | last_statement_first
  private static boolean statement_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_recover_0")) return false;
    boolean r;
    r = statement_first(b, l + 1);
    if (!r) r = consumeTokenFast(b, SEMI);
    if (!r) r = consumeTokenFast(b, END);
    if (!r) r = consumeTokenFast(b, UNTIL);
    if (!r) r = consumeTokenFast(b, ELSEIF);
    if (!r) r = consumeTokenFast(b, ELSE);
    if (!r) r = last_statement_first(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // !('end' | 'return' | continue_soft_keyword | 'break' | 'else' | 'elseif' | 'until' | <<eof>>) statement
  static boolean statement_with_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_with_recover")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = statement_with_recover_0(b, l + 1);
    p = r; // pin = 1
    r = r && statement(b, l + 1);
    exit_section_(b, l, m, r, p, LuauParser::statement_recover);
    return r || p;
  }

  // !('end' | 'return' | continue_soft_keyword | 'break' | 'else' | 'elseif' | 'until' | <<eof>>)
  private static boolean statement_with_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_with_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !statement_with_recover_0_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // 'end' | 'return' | continue_soft_keyword | 'break' | 'else' | 'elseif' | 'until' | <<eof>>
  private static boolean statement_with_recover_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_with_recover_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, END);
    if (!r) r = consumeToken(b, RETURN);
    if (!r) r = continue_soft_keyword(b, l + 1);
    if (!r) r = consumeToken(b, BREAK);
    if (!r) r = consumeToken(b, ELSE);
    if (!r) r = consumeToken(b, ELSEIF);
    if (!r) r = consumeToken(b, UNTIL);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ID '=' expression
  public static boolean string_keyed_field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_keyed_field")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, STRING_KEYED_FIELD, null);
    r = consumeTokens(b, 2, ID, ASSIGN);
    p = r; // pin = 2
    r = r && expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '{' field_with_recover* '}'
  public static boolean table_constructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_constructor")) return false;
    if (!nextTokenIs(b, LCURLY)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TABLE_CONSTRUCTOR, null);
    r = consumeToken(b, LCURLY);
    p = r; // pin = 1
    r = r && report_error_(b, table_constructor_1(b, l + 1));
    r = p && consumeToken(b, RCURLY) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // field_with_recover*
  private static boolean table_constructor_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_constructor_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!field_with_recover(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "table_constructor_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // table_constructor
  public static boolean table_constructor_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_constructor_expr")) return false;
    if (!nextTokenIsFast(b, LCURLY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = table_constructor(b, l + 1);
    exit_section_(b, m, TABLE_CONSTRUCTOR_EXPR, r);
    return r;
  }

  /* ********************************************************** */
  // '{' (type &'}' | table_type_field_with_recover*) '}'
  public static boolean table_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type")) return false;
    if (!nextTokenIs(b, LCURLY)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TABLE_TYPE, null);
    r = consumeToken(b, LCURLY);
    p = r; // pin = 1
    r = r && report_error_(b, table_type_1(b, l + 1));
    r = p && consumeToken(b, RCURLY) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // type &'}' | table_type_field_with_recover*
  private static boolean table_type_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = table_type_1_0(b, l + 1);
    if (!r) r = table_type_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // type &'}'
  private static boolean table_type_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type(b, l + 1);
    r = r && table_type_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &'}'
  private static boolean table_type_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RCURLY);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // table_type_field_with_recover*
  private static boolean table_type_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type_1_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!table_type_field_with_recover(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "table_type_1_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !('}' | ID | '[' | statement_first | 'end' | last_statement_first)
  static boolean table_type_field_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type_field_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !table_type_field_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '}' | ID | '[' | statement_first | 'end' | last_statement_first
  private static boolean table_type_field_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type_field_recover_0")) return false;
    boolean r;
    r = consumeTokenFast(b, RCURLY);
    if (!r) r = consumeTokenFast(b, ID);
    if (!r) r = consumeTokenFast(b, LBRACK);
    if (!r) r = statement_first(b, l + 1);
    if (!r) r = consumeTokenFast(b, END);
    if (!r) r = last_statement_first(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // !'}' type_field (field_sep | &'}')
  static boolean table_type_field_with_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type_field_with_recover")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = table_type_field_with_recover_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, type_field(b, l + 1));
    r = p && table_type_field_with_recover_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, LuauParser::table_type_field_recover);
    return r || p;
  }

  // !'}'
  private static boolean table_type_field_with_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type_field_with_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RCURLY);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // field_sep | &'}'
  private static boolean table_type_field_with_recover_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type_field_with_recover_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = field_sep(b, l + 1);
    if (!r) r = table_type_field_with_recover_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &'}'
  private static boolean table_type_field_with_recover_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type_field_with_recover_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RCURLY);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TEMPLATE_STRING_SQUOTE template_string_internals TEMPLATE_STRING_EQUOTE
  public static boolean template_string_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "template_string_expr")) return false;
    if (!nextTokenIsFast(b, TEMPLATE_STRING_SQUOTE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TEMPLATE_STRING_EXPR, null);
    r = consumeTokenFast(b, TEMPLATE_STRING_SQUOTE);
    p = r; // pin = 1
    r = r && report_error_(b, template_string_internals(b, l + 1));
    r = p && consumeToken(b, TEMPLATE_STRING_EQUOTE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // (STRING | '{' expression '}')*
  static boolean template_string_internals(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "template_string_internals")) return false;
    Marker m = enter_section_(b, l, _NONE_);
    while (true) {
      int c = current_position_(b);
      if (!template_string_internals_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "template_string_internals", c)) break;
    }
    exit_section_(b, l, m, true, false, LuauParser::template_string_internals_recover);
    return true;
  }

  // STRING | '{' expression '}'
  private static boolean template_string_internals_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "template_string_internals_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STRING);
    if (!r) r = template_string_internals_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '{' expression '}'
  private static boolean template_string_internals_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "template_string_internals_0_1")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LCURLY);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && consumeToken(b, RCURLY) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // !(TEMPLATE_STRING_EQUOTE | statement_first | last_statement_first)
  static boolean template_string_internals_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "template_string_internals_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !template_string_internals_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // TEMPLATE_STRING_EQUOTE | statement_first | last_statement_first
  private static boolean template_string_internals_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "template_string_internals_recover_0")) return false;
    boolean r;
    r = consumeTokenFast(b, TEMPLATE_STRING_EQUOTE);
    if (!r) r = statement_first(b, l + 1);
    if (!r) r = last_statement_first(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // simple_type ('?' union_type* | union_type+ | intersection_type+)?
  //   | union_type+
  //   | intersection_type+
  public static boolean type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE, "<type>");
    r = type_0(b, l + 1);
    if (!r) r = type_1(b, l + 1);
    if (!r) r = type_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // simple_type ('?' union_type* | union_type+ | intersection_type+)?
  private static boolean type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = simple_type(b, l + 1);
    r = r && type_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('?' union_type* | union_type+ | intersection_type+)?
  private static boolean type_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_0_1")) return false;
    type_0_1_0(b, l + 1);
    return true;
  }

  // '?' union_type* | union_type+ | intersection_type+
  private static boolean type_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type_0_1_0_0(b, l + 1);
    if (!r) r = type_0_1_0_1(b, l + 1);
    if (!r) r = type_0_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '?' union_type*
  private static boolean type_0_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_0_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, QUESTION);
    r = r && type_0_1_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // union_type*
  private static boolean type_0_1_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_0_1_0_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!union_type(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "type_0_1_0_0_1", c)) break;
    }
    return true;
  }

  // union_type+
  private static boolean type_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_0_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = union_type(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!union_type(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "type_0_1_0_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // intersection_type+
  private static boolean type_0_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_0_1_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = intersection_type(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!intersection_type(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "type_0_1_0_2", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // union_type+
  private static boolean type_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = union_type(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!union_type(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "type_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // intersection_type+
  private static boolean type_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = intersection_type(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!intersection_type(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "type_2", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '[' type ']'
  public static boolean type_computed_key(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_computed_key")) return false;
    if (!nextTokenIs(b, LBRACK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TYPE_COMPUTED_KEY, null);
    r = consumeToken(b, LBRACK);
    p = r; // pin = 1
    r = r && report_error_(b, type(b, l + 1));
    r = p && consumeToken(b, RBRACK) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // export_soft_keyword? type_soft_keyword ID ('<' generic_type_list_with_defaults '>')? '=' type
  public static boolean type_declaration_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_declaration_statement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TYPE_DECLARATION_STATEMENT, "<type declaration statement>");
    r = type_declaration_statement_0(b, l + 1);
    r = r && type_soft_keyword(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, ID));
    r = p && report_error_(b, type_declaration_statement_3(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, ASSIGN)) && r;
    r = p && type(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // export_soft_keyword?
  private static boolean type_declaration_statement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_declaration_statement_0")) return false;
    export_soft_keyword(b, l + 1);
    return true;
  }

  // ('<' generic_type_list_with_defaults '>')?
  private static boolean type_declaration_statement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_declaration_statement_3")) return false;
    type_declaration_statement_3_0(b, l + 1);
    return true;
  }

  // '<' generic_type_list_with_defaults '>'
  private static boolean type_declaration_statement_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_declaration_statement_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    r = r && generic_type_list_with_defaults(b, l + 1);
    r = r && consumeToken(b, GT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ((read_soft_keyword | write_soft_keyword) !':')? (type_string_keyed_field | type_keyed_field)
  public static boolean type_field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_field")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE_FIELD, "<type field>");
    r = type_field_0(b, l + 1);
    r = r && type_field_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((read_soft_keyword | write_soft_keyword) !':')?
  private static boolean type_field_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_field_0")) return false;
    type_field_0_0(b, l + 1);
    return true;
  }

  // (read_soft_keyword | write_soft_keyword) !':'
  private static boolean type_field_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_field_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type_field_0_0_0(b, l + 1);
    r = r && type_field_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // read_soft_keyword | write_soft_keyword
  private static boolean type_field_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_field_0_0_0")) return false;
    boolean r;
    r = read_soft_keyword(b, l + 1);
    if (!r) r = write_soft_keyword(b, l + 1);
    return r;
  }

  // !':'
  private static boolean type_field_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_field_0_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, COLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // type_string_keyed_field | type_keyed_field
  private static boolean type_field_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_field_1")) return false;
    boolean r;
    r = type_string_keyed_field(b, l + 1);
    if (!r) r = type_keyed_field(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // export_soft_keyword? type_soft_keyword 'function' ID func_body
  public static boolean type_function_declaration_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_function_declaration_statement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TYPE_FUNCTION_DECLARATION_STATEMENT, "<type function declaration statement>");
    r = type_function_declaration_statement_0(b, l + 1);
    r = r && type_soft_keyword(b, l + 1);
    r = r && consumeTokens(b, 1, FUNCTION, ID);
    p = r; // pin = 3
    r = r && func_body(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // export_soft_keyword?
  private static boolean type_function_declaration_statement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_function_declaration_statement_0")) return false;
    export_soft_keyword(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // type_computed_key ':' type
  public static boolean type_keyed_field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_keyed_field")) return false;
    if (!nextTokenIs(b, LBRACK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TYPE_KEYED_FIELD, null);
    r = type_computed_key(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COLON));
    r = p && type(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // generic_type_pack | <<list type>> (',' variadic_type_pack)? | variadic_type_pack
  public static boolean type_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE_LIST, "<type list>");
    r = generic_type_pack(b, l + 1);
    if (!r) r = type_list_1(b, l + 1);
    if (!r) r = variadic_type_pack(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // <<list type>> (',' variadic_type_pack)?
  private static boolean type_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_list_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = list(b, l + 1, LuauParser::type);
    r = r && type_list_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' variadic_type_pack)?
  private static boolean type_list_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_list_1_1")) return false;
    type_list_1_1_0(b, l + 1);
    return true;
  }

  // ',' variadic_type_pack
  private static boolean type_list_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_list_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && variadic_type_pack(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '(' type_list? ')'
  public static boolean type_pack(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_pack")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && type_pack_1(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, TYPE_PACK, r);
    return r;
  }

  // type_list?
  private static boolean type_pack_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_pack_1")) return false;
    type_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '...' (':' (generic_type_pack | type))?
  static boolean type_pack_parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_pack_parameter")) return false;
    if (!nextTokenIs(b, ELLIPSIS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, ELLIPSIS);
    p = r; // pin = 1
    r = r && type_pack_parameter_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (':' (generic_type_pack | type))?
  private static boolean type_pack_parameter_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_pack_parameter_1")) return false;
    type_pack_parameter_1_0(b, l + 1);
    return true;
  }

  // ':' (generic_type_pack | type)
  private static boolean type_pack_parameter_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_pack_parameter_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && type_pack_parameter_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // generic_type_pack | type
  private static boolean type_pack_parameter_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_pack_parameter_1_0_1")) return false;
    boolean r;
    r = generic_type_pack(b, l + 1);
    if (!r) r = type(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // <<list type_params_item>>
  public static boolean type_params(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_params")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE_PARAMS, "<type params>");
    r = list(b, l + 1, LuauParser::type_params_item);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // variadic_type_pack | generic_type_pack | type | type_pack
  static boolean type_params_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_params_item")) return false;
    boolean r;
    r = variadic_type_pack(b, l + 1);
    if (!r) r = generic_type_pack(b, l + 1);
    if (!r) r = type(b, l + 1);
    if (!r) r = type_pack(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // (simple_type_reference !('.') | simple_reference '.' simple_type_reference) ('<' type_params? '>')?
  public static boolean type_reference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_reference")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type_reference_0(b, l + 1);
    r = r && type_reference_1(b, l + 1);
    exit_section_(b, m, TYPE_REFERENCE, r);
    return r;
  }

  // simple_type_reference !('.') | simple_reference '.' simple_type_reference
  private static boolean type_reference_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_reference_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type_reference_0_0(b, l + 1);
    if (!r) r = type_reference_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // simple_type_reference !('.')
  private static boolean type_reference_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_reference_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = simple_type_reference(b, l + 1);
    r = r && type_reference_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !('.')
  private static boolean type_reference_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_reference_0_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, DOT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // simple_reference '.' simple_type_reference
  private static boolean type_reference_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_reference_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = simple_reference(b, l + 1);
    r = r && consumeToken(b, DOT);
    r = r && simple_type_reference(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('<' type_params? '>')?
  private static boolean type_reference_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_reference_1")) return false;
    type_reference_1_0(b, l + 1);
    return true;
  }

  // '<' type_params? '>'
  private static boolean type_reference_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_reference_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    r = r && type_reference_1_0_1(b, l + 1);
    r = r && consumeToken(b, GT);
    exit_section_(b, m, null, r);
    return r;
  }

  // type_params?
  private static boolean type_reference_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_reference_1_0_1")) return false;
    type_params(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'type'
  public static boolean type_soft_keyword(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_soft_keyword")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE_SOFT_KEYWORD, "<type soft keyword>");
    r = consumeToken(b, "type");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ID ':' type
  public static boolean type_string_keyed_field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_string_keyed_field")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TYPE_STRING_KEYED_FIELD, null);
    r = consumeTokens(b, 1, ID, COLON);
    p = r; // pin = 1
    r = r && type(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'typeof'
  public static boolean typeof_soft_keyword(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeof_soft_keyword")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPEOF_SOFT_KEYWORD, "<typeof soft keyword>");
    r = consumeToken(b, "typeof");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // typeof_soft_keyword '(' expression ')'
  public static boolean typeof_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeof_type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPEOF_TYPE, "<typeof type>");
    r = typeof_soft_keyword(b, l + 1);
    r = r && consumeToken(b, LPAREN);
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '#'
  public static boolean un_length_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "un_length_op")) return false;
    if (!nextTokenIs(b, GETN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, GETN);
    exit_section_(b, m, OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // '-'
  public static boolean un_minus_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "un_minus_op")) return false;
    if (!nextTokenIs(b, MINUS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MINUS);
    exit_section_(b, m, OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // 'not'
  public static boolean un_not_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "un_not_op")) return false;
    if (!nextTokenIs(b, NOT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NOT);
    exit_section_(b, m, OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // '|' simple_type '?'?
  public static boolean union_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "union_type")) return false;
    if (!nextTokenIs(b, UNION)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _UPPER_, UNION_TYPE, null);
    r = consumeToken(b, UNION);
    r = r && simple_type(b, l + 1);
    r = r && union_type_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '?'?
  private static boolean union_type_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "union_type_2")) return false;
    consumeToken(b, QUESTION);
    return true;
  }

  /* ********************************************************** */
  // <<list lvalue>>
  public static boolean var_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "var_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VAR_LIST, "<var list>");
    r = list(b, l + 1, LuauParser::lvalue);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '...' type
  public static boolean variadic_type_pack(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variadic_type_pack")) return false;
    if (!nextTokenIs(b, ELLIPSIS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, VARIADIC_TYPE_PACK, null);
    r = consumeToken(b, ELLIPSIS);
    p = r; // pin = 1
    r = r && type(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'while' expression 'do' block 'end'
  public static boolean while_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_statement")) return false;
    if (!nextTokenIs(b, WHILE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, WHILE_STATEMENT, null);
    r = consumeToken(b, WHILE);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && report_error_(b, consumeToken(b, DO)) && r;
    r = p && report_error_(b, block(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'write'
  public static boolean write_soft_keyword(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "write_soft_keyword")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, WRITE_SOFT_KEYWORD, "<write soft keyword>");
    r = consumeToken(b, "write");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Expression root: expression
  // Operator priority table:
  // 0: ATOM(ifelse_expr)
  // 1: BINARY(or_expr)
  // 2: BINARY(and_expr)
  // 3: BINARY(comparison_expr)
  // 4: BINARY(plus_expr) BINARY(minus_expr)
  // 5: BINARY(mul_expr) BINARY(div_expr) BINARY(floor_div_expr) BINARY(mod_expr)
  //    BINARY(concat_expr)
  // 6: PREFIX(length_expr) PREFIX(not_expr) PREFIX(unary_min_expr)
  // 7: N_ARY(exp_expr)
  // 8: ATOM(primary_group_expr)
  public static boolean expression(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expression")) return false;
    addVariant(b, "<expression>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<expression>");
    r = ifelse_expr(b, l + 1);
    if (!r) r = length_expr(b, l + 1);
    if (!r) r = not_expr(b, l + 1);
    if (!r) r = unary_min_expr(b, l + 1);
    if (!r) r = primary_group_expr(b, l + 1);
    p = r;
    r = r && expression_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean expression_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expression_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 1 && or_op(b, l + 1)) {
        r = expression(b, l, 1);
        exit_section_(b, l, m, OR_EXPR, r, true, null);
      }
      else if (g < 2 && and_op(b, l + 1)) {
        r = expression(b, l, 2);
        exit_section_(b, l, m, AND_EXPR, r, true, null);
      }
      else if (g < 3 && comparison_op(b, l + 1)) {
        r = expression(b, l, 3);
        exit_section_(b, l, m, COMPARISON_EXPR, r, true, null);
      }
      else if (g < 4 && plus_op(b, l + 1)) {
        r = expression(b, l, 4);
        exit_section_(b, l, m, PLUS_EXPR, r, true, null);
      }
      else if (g < 4 && minus_op(b, l + 1)) {
        r = expression(b, l, 4);
        exit_section_(b, l, m, MINUS_EXPR, r, true, null);
      }
      else if (g < 5 && mul_op(b, l + 1)) {
        r = expression(b, l, 5);
        exit_section_(b, l, m, MUL_EXPR, r, true, null);
      }
      else if (g < 5 && div_op(b, l + 1)) {
        r = expression(b, l, 5);
        exit_section_(b, l, m, DIV_EXPR, r, true, null);
      }
      else if (g < 5 && floor_div_op(b, l + 1)) {
        r = expression(b, l, 5);
        exit_section_(b, l, m, FLOOR_DIV_EXPR, r, true, null);
      }
      else if (g < 5 && mod_op(b, l + 1)) {
        r = expression(b, l, 5);
        exit_section_(b, l, m, MOD_EXPR, r, true, null);
      }
      else if (g < 5 && concat_op(b, l + 1)) {
        r = expression(b, l, 5);
        exit_section_(b, l, m, CONCAT_EXPR, r, true, null);
      }
      else if (g < 7 && consumeTokenSmart(b, EXP)) {
        while (true) {
          r = report_error_(b, expression(b, l, 7));
          if (!consumeTokenSmart(b, EXP)) break;
        }
        exit_section_(b, l, m, EXP_EXPR, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  // 'if' expression 'then' expression ('elseif' expression 'then' expression)* 'else' expression
  public static boolean ifelse_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifelse_expr")) return false;
    if (!nextTokenIsSmart(b, IF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFELSE_EXPR, null);
    r = consumeTokenSmart(b, IF);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && report_error_(b, consumeToken(b, THEN)) && r;
    r = p && report_error_(b, expression(b, l + 1, -1)) && r;
    r = p && report_error_(b, ifelse_expr_4(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, ELSE)) && r;
    r = p && expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ('elseif' expression 'then' expression)*
  private static boolean ifelse_expr_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifelse_expr_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ifelse_expr_4_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ifelse_expr_4", c)) break;
    }
    return true;
  }

  // 'elseif' expression 'then' expression
  private static boolean ifelse_expr_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifelse_expr_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, ELSEIF);
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, THEN);
    r = r && expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean length_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "length_expr")) return false;
    if (!nextTokenIsSmart(b, GETN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = un_length_op(b, l + 1);
    p = r;
    r = p && expression(b, l, 6);
    exit_section_(b, l, m, LENGTH_EXPR, r, p, null);
    return r || p;
  }

  public static boolean not_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_expr")) return false;
    if (!nextTokenIsSmart(b, NOT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = un_not_op(b, l + 1);
    p = r;
    r = p && expression(b, l, 6);
    exit_section_(b, l, m, NOT_EXPR, r, p, null);
    return r || p;
  }

  public static boolean unary_min_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_min_expr")) return false;
    if (!nextTokenIsSmart(b, MINUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = un_minus_op(b, l + 1);
    p = r;
    r = p && expression(b, l, 6);
    exit_section_(b, l, m, UNARY_MIN_EXPR, r, p, null);
    return r || p;
  }

  // (table_constructor_expr | literal_expr | closure_expr | template_string_expr | index_or_call_expr) as_expr?
  public static boolean primary_group_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary_group_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, PRIMARY_GROUP_EXPR, "<primary group expr>");
    r = primary_group_expr_0(b, l + 1);
    r = r && primary_group_expr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // table_constructor_expr | literal_expr | closure_expr | template_string_expr | index_or_call_expr
  private static boolean primary_group_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary_group_expr_0")) return false;
    boolean r;
    r = table_constructor_expr(b, l + 1);
    if (!r) r = literal_expr(b, l + 1);
    if (!r) r = closure_expr(b, l + 1);
    if (!r) r = template_string_expr(b, l + 1);
    if (!r) r = index_or_call_expr(b, l + 1);
    return r;
  }

  // as_expr?
  private static boolean primary_group_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary_group_expr_1")) return false;
    as_expr(b, l + 1);
    return true;
  }

  static final Parser expression_parser_ = (b, l) -> expression(b, l + 1, -1);
}
