// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.aleksandrsl.intellijluau.psi.LuauTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
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
    return luau_file(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(FUNC_ARGS, LIST_ARGS, SINGLE_ARG),
    create_token_set_(AS_EXP, BINARY_EXP, EXPRESSION, SIMPLE_EXP,
      UNARY_EXP),
  };

  /* ********************************************************** */
  // expression (',' expression)*
  static boolean arg_expr_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arg_expr_list")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1, -1);
    r = r && arg_expr_list_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' expression)*
  private static boolean arg_expr_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arg_expr_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!arg_expr_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "arg_expr_list_1", c)) break;
    }
    return true;
  }

  // ',' expression
  private static boolean arg_expr_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arg_expr_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
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
  // '+' | '-' | '*' | '/' | '//' | '^' | '%' | '..' | '<' | '<=' | '>' | '>=' | '==' | '~=' | 'and' | 'or'
  public static boolean bin_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bin_op")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BIN_OP, "<bin op>");
    r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    if (!r) r = consumeToken(b, MULT);
    if (!r) r = consumeToken(b, DIV);
    if (!r) r = consumeToken(b, DOUBLE_DIV);
    if (!r) r = consumeToken(b, EXP);
    if (!r) r = consumeToken(b, MOD);
    if (!r) r = consumeToken(b, CONCAT);
    if (!r) r = consumeToken(b, LT);
    if (!r) r = consumeToken(b, LE);
    if (!r) r = consumeToken(b, GT);
    if (!r) r = consumeToken(b, GE);
    if (!r) r = consumeToken(b, EQ);
    if (!r) r = consumeToken(b, NE);
    if (!r) r = consumeToken(b, AND);
    if (!r) r = consumeToken(b, OR);
    exit_section_(b, l, m, r, false, null);
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
  // binding (',' binding)*
  public static boolean binding_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binding_list")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = binding(b, l + 1);
    r = r && binding_list_1(b, l + 1);
    exit_section_(b, m, BINDING_LIST, r);
    return r;
  }

  // (',' binding)*
  private static boolean binding_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binding_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!binding_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "binding_list_1", c)) break;
    }
    return true;
  }

  // ',' binding
  private static boolean binding_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binding_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && binding(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (statement ';'?)* (last_statement ';'?)?
  public static boolean block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BLOCK, "<block>");
    r = block_0(b, l + 1);
    r = r && block_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (statement ';'?)*
  private static boolean block_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!block_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "block_0", c)) break;
    }
    return true;
  }

  // statement ';'?
  private static boolean block_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = statement(b, l + 1);
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
  // variadic_type_pack | generic_type_pack | (ID ':')? type (',' bound_type_list)?
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

  // (ID ':')? type (',' bound_type_list)?
  private static boolean bound_type_list_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bound_type_list_2_0(b, l + 1);
    r = r && type(b, l + 1);
    r = r && bound_type_list_2_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (ID ':')?
  private static boolean bound_type_list_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_2_0")) return false;
    bound_type_list_2_0_0(b, l + 1);
    return true;
  }

  // ID ':'
  private static boolean bound_type_list_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_2_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, COLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' bound_type_list)?
  private static boolean bound_type_list_2_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_2_2")) return false;
    bound_type_list_2_2_0(b, l + 1);
    return true;
  }

  // ',' bound_type_list
  private static boolean bound_type_list_2_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_2_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && bound_type_list(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(')')
  static boolean bound_type_list_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bound_type_list_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RPAREN);
    exit_section_(b, l, m, r, false, null);
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
  // 'function' func_body
  public static boolean closure_exp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "closure_exp")) return false;
    if (!nextTokenIs(b, FUNCTION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CLOSURE_EXP, null);
    r = consumeToken(b, FUNCTION);
    p = r; // pin = 1
    r = r && func_body(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '+=' | '-=' | '*=' | '/=' | '%=' | '^=' | '..=' | '//='
  public static boolean compound_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compound_op")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMPOUND_OP, "<compound op>");
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
  // var compound_op expression
  public static boolean compound_op_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compound_op_statement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMPOUND_OP_STATEMENT, "<compound op statement>");
    r = var(b, l + 1);
    r = r && compound_op(b, l + 1);
    p = r; // pin = 2
    r = r && expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
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
    if (!nextTokenIs(b, "", FUNCTION, LOCAL)) return false;
    boolean r;
    r = local_func_def_statement(b, l + 1);
    if (!r) r = method_def_statement(b, l + 1);
    if (!r) r = func_def_statement(b, l + 1);
    if (!r) r = local_def_statement(b, l + 1);
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
  // expression (',' expression)*
  public static boolean exp_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exp_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXP_LIST, "<exp list>");
    r = expression(b, l + 1, -1);
    r = r && exp_list_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (',' expression)*
  private static boolean exp_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exp_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!exp_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "exp_list_1", c)) break;
    }
    return true;
  }

  // ',' expression
  private static boolean exp_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exp_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
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
  // expression
  public static boolean expression_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPRESSION_STATEMENT, "<expression statement>");
    r = expression(b, l + 1, -1);
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
    exit_section_(b, l, m, r, false, LuauParser::field_recover);
    return r;
  }

  /* ********************************************************** */
  // field (field_sep field)* field_sep?
  public static boolean field_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FIELD_LIST, "<field list>");
    r = field(b, l + 1);
    r = r && field_list_1(b, l + 1);
    r = r && field_list_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (field_sep field)*
  private static boolean field_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!field_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "field_list_1", c)) break;
    }
    return true;
  }

  // field_sep field
  private static boolean field_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = field_sep(b, l + 1);
    r = r && field(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // field_sep?
  private static boolean field_list_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_list_2")) return false;
    field_sep(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // !(',' | ';' | '}' | '[')
  static boolean field_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !field_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ',' | ';' | '}' | '['
  private static boolean field_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_recover_0")) return false;
    boolean r;
    r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, SEMI);
    if (!r) r = consumeToken(b, RCURLY);
    if (!r) r = consumeToken(b, LBRACK);
    return r;
  }

  /* ********************************************************** */
  // ',' | ';'
  public static boolean field_sep(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_sep")) return false;
    if (!nextTokenIs(b, "<field sep>", COMMA, SEMI)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FIELD_SEP, "<field sep>");
    r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, SEMI);
    exit_section_(b, l, m, r, false, null);
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
  // ('<' generic_type_list '>')? '(' par_list? ')' (':' return_type)? block 'end'
  public static boolean func_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_body")) return false;
    if (!nextTokenIs(b, "<func body>", LPAREN, LT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FUNC_BODY, "<func body>");
    r = func_body_0(b, l + 1);
    r = r && consumeToken(b, LPAREN);
    r = r && func_body_2(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && func_body_4(b, l + 1);
    r = r && block(b, l + 1);
    r = r && consumeToken(b, END);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ('<' generic_type_list '>')?
  private static boolean func_body_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_body_0")) return false;
    func_body_0_0(b, l + 1);
    return true;
  }

  // '<' generic_type_list '>'
  private static boolean func_body_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_body_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    r = r && generic_type_list(b, l + 1);
    r = r && consumeToken(b, GT);
    exit_section_(b, m, null, r);
    return r;
  }

  // par_list?
  private static boolean func_body_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_body_2")) return false;
    par_list(b, l + 1);
    return true;
  }

  // (':' return_type)?
  private static boolean func_body_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_body_4")) return false;
    func_body_4_0(b, l + 1);
    return true;
  }

  // ':' return_type
  private static boolean func_body_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_body_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && return_type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'function' ID func_body
  public static boolean func_def_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_def_statement")) return false;
    if (!nextTokenIs(b, FUNCTION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FUNC_DEF_STATEMENT, null);
    r = consumeTokens(b, 1, FUNCTION, ID);
    p = r; // pin = 1
    r = r && func_body(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ('<' generic_type_list '>')? '(' bound_type_list? ')' '->' return_type
  public static boolean function_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_type")) return false;
    if (!nextTokenIs(b, "<function type>", LPAREN, LT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_TYPE, "<function type>");
    r = function_type_0(b, l + 1);
    r = r && consumeToken(b, LPAREN);
    r = r && function_type_2(b, l + 1);
    r = r && consumeTokens(b, 2, RPAREN, ARROW);
    p = r; // pin = 5
    r = r && return_type(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ('<' generic_type_list '>')?
  private static boolean function_type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_type_0")) return false;
    function_type_0_0(b, l + 1);
    return true;
  }

  // '<' generic_type_list '>'
  private static boolean function_type_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_type_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    r = r && generic_type_list(b, l + 1);
    r = r && consumeToken(b, GT);
    exit_section_(b, m, null, r);
    return r;
  }

  // bound_type_list?
  private static boolean function_type_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_type_2")) return false;
    bound_type_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // generic_type_pack_parameter (',' generic_type_pack_parameter)* | ID (',' generic_type_list)?
  public static boolean generic_type_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = generic_type_list_0(b, l + 1);
    if (!r) r = generic_type_list_1(b, l + 1);
    exit_section_(b, m, GENERIC_TYPE_LIST, r);
    return r;
  }

  // generic_type_pack_parameter (',' generic_type_pack_parameter)*
  private static boolean generic_type_list_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = generic_type_pack_parameter(b, l + 1);
    r = r && generic_type_list_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' generic_type_pack_parameter)*
  private static boolean generic_type_list_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!generic_type_list_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "generic_type_list_0_1", c)) break;
    }
    return true;
  }

  // ',' generic_type_pack_parameter
  private static boolean generic_type_list_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && generic_type_pack_parameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ID (',' generic_type_list)?
  private static boolean generic_type_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    r = r && generic_type_list_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' generic_type_list)?
  private static boolean generic_type_list_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_1_1")) return false;
    generic_type_list_1_1_0(b, l + 1);
    return true;
  }

  // ',' generic_type_list
  private static boolean generic_type_list_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && generic_type_list(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // generic_type_list (',' generic_type_list_with_defaults_item)* | generic_type_list_with_defaults_item (',' generic_type_list_with_defaults_item)*
  public static boolean generic_type_list_with_defaults(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = generic_type_list_with_defaults_0(b, l + 1);
    if (!r) r = generic_type_list_with_defaults_1(b, l + 1);
    exit_section_(b, m, GENERIC_TYPE_LIST_WITH_DEFAULTS, r);
    return r;
  }

  // generic_type_list (',' generic_type_list_with_defaults_item)*
  private static boolean generic_type_list_with_defaults_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = generic_type_list(b, l + 1);
    r = r && generic_type_list_with_defaults_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' generic_type_list_with_defaults_item)*
  private static boolean generic_type_list_with_defaults_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!generic_type_list_with_defaults_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "generic_type_list_with_defaults_0_1", c)) break;
    }
    return true;
  }

  // ',' generic_type_list_with_defaults_item
  private static boolean generic_type_list_with_defaults_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && generic_type_list_with_defaults_item(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // generic_type_list_with_defaults_item (',' generic_type_list_with_defaults_item)*
  private static boolean generic_type_list_with_defaults_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = generic_type_list_with_defaults_item(b, l + 1);
    r = r && generic_type_list_with_defaults_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' generic_type_list_with_defaults_item)*
  private static boolean generic_type_list_with_defaults_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!generic_type_list_with_defaults_1_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "generic_type_list_with_defaults_1_1", c)) break;
    }
    return true;
  }

  // ',' generic_type_list_with_defaults_item
  private static boolean generic_type_list_with_defaults_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && generic_type_list_with_defaults_item(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // generic_type_pack_parameter_with_default
  //     | ID '=' type
  static boolean generic_type_list_with_defaults_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_item")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = generic_type_pack_parameter_with_default(b, l + 1);
    if (!r) r = generic_type_list_with_defaults_item_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ID '=' type
  private static boolean generic_type_list_with_defaults_item_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_type_list_with_defaults_item_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, ASSIGN);
    r = r && type(b, l + 1);
    exit_section_(b, m, null, r);
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
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, ELLIPSIS, ASSIGN);
    r = r && generic_type_pack_parameter_with_default_3(b, l + 1);
    exit_section_(b, m, GENERIC_TYPE_PACK_PARAMETER_WITH_DEFAULT, r);
    return r;
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
  // 'if' expression 'then' expression ('elseif' expression 'then' expression)* 'else' expression
  public static boolean ifelse_exp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifelse_exp")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFELSE_EXP, null);
    r = consumeToken(b, IF);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && report_error_(b, consumeToken(b, THEN)) && r;
    r = p && report_error_(b, expression(b, l + 1, -1)) && r;
    r = p && report_error_(b, ifelse_exp_4(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, ELSE)) && r;
    r = p && expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ('elseif' expression 'then' expression)*
  private static boolean ifelse_exp_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifelse_exp_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ifelse_exp_4_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ifelse_exp_4", c)) break;
    }
    return true;
  }

  // 'elseif' expression 'then' expression
  private static boolean ifelse_exp_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifelse_exp_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELSEIF);
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, THEN);
    r = r && expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '[' expression ']' | '.' ID | ':' ID
  public static boolean index_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "index_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, INDEX_EXPR, "<index expr>");
    r = index_expr_0(b, l + 1);
    if (!r) r = parseTokens(b, 1, DOT, ID);
    if (!r) r = parseTokens(b, 1, COLON, ID);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '[' expression ']'
  private static boolean index_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "index_expr_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LBRACK);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && consumeToken(b, RBRACK) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
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
  // ('&' simple_type)+
  public static boolean intersection_suffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "intersection_suffix")) return false;
    if (!nextTokenIs(b, INTERSECTION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = intersection_suffix_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!intersection_suffix_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "intersection_suffix", c)) break;
    }
    exit_section_(b, m, INTERSECTION_SUFFIX, r);
    return r;
  }

  // '&' simple_type
  private static boolean intersection_suffix_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "intersection_suffix_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INTERSECTION);
    r = r && simple_type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '[' expression ']' '=' expression
  public static boolean keyed_field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keyed_field")) return false;
    if (!nextTokenIs(b, LBRACK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, KEYED_FIELD, null);
    r = consumeToken(b, LBRACK);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && report_error_(b, consumeTokens(b, -1, RBRACK, ASSIGN)) && r;
    r = p && expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'return' exp_list? | 'break' | continue_soft_keyword
  public static boolean last_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "last_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAST_STATEMENT, "<last statement>");
    r = last_statement_0(b, l + 1);
    if (!r) r = consumeToken(b, BREAK);
    if (!r) r = continue_soft_keyword(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // 'return' exp_list?
  private static boolean last_statement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "last_statement_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RETURN);
    r = r && last_statement_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // exp_list?
  private static boolean last_statement_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "last_statement_0_1")) return false;
    exp_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '(' arg_expr_list? ')'
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

  // arg_expr_list?
  private static boolean list_args_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_args_1")) return false;
    arg_expr_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // NUMBER | STRING | 'nil' | 'true' | 'false' | '...'
  static boolean literal_exp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_exp")) return false;
    boolean r;
    r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NIL);
    if (!r) r = consumeToken(b, TRUE);
    if (!r) r = consumeToken(b, FALSE);
    if (!r) r = consumeToken(b, ELLIPSIS);
    return r;
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
  // 'local' 'function' ID func_body
  public static boolean local_func_def_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_func_def_statement")) return false;
    if (!nextTokenIs(b, LOCAL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LOCAL_FUNC_DEF_STATEMENT, null);
    r = consumeTokens(b, 2, LOCAL, FUNCTION, ID);
    p = r; // pin = 2
    r = r && func_body(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // shebang_line? block
  static boolean luau_file(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "luau_file")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = luau_file_0(b, l + 1);
    r = r && block(b, l + 1);
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
  // 'function' method_name func_body
  public static boolean method_def_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_def_statement")) return false;
    if (!nextTokenIs(b, FUNCTION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, METHOD_DEF_STATEMENT, null);
    r = consumeToken(b, FUNCTION);
    r = r && method_name(b, l + 1);
    p = r; // pin = 2
    r = r && func_body(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ID (('.' ID)* ':' ID | ('.' ID)+)
  public static boolean method_name(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_name")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    r = r && method_name_1(b, l + 1);
    exit_section_(b, m, METHOD_NAME, r);
    return r;
  }

  // ('.' ID)* ':' ID | ('.' ID)+
  private static boolean method_name_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_name_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = method_name_1_0(b, l + 1);
    if (!r) r = method_name_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('.' ID)* ':' ID
  private static boolean method_name_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_name_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = method_name_1_0_0(b, l + 1);
    r = r && consumeTokens(b, 0, COLON, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('.' ID)*
  private static boolean method_name_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_name_1_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!method_name_1_0_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "method_name_1_0_0", c)) break;
    }
    return true;
  }

  // '.' ID
  private static boolean method_name_1_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_name_1_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DOT, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('.' ID)+
  private static boolean method_name_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_name_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = method_name_1_1_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!method_name_1_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "method_name_1_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // '.' ID
  private static boolean method_name_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_name_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DOT, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // binding_list (',' type_pack_parameter)? | type_pack_parameter
  public static boolean par_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "par_list")) return false;
    if (!nextTokenIs(b, "<par list>", ELLIPSIS, ID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PAR_LIST, "<par list>");
    r = par_list_0(b, l + 1);
    if (!r) r = type_pack_parameter(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // binding_list (',' type_pack_parameter)?
  private static boolean par_list_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "par_list_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = binding_list(b, l + 1);
    r = r && par_list_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' type_pack_parameter)?
  private static boolean par_list_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "par_list_0_1")) return false;
    par_list_0_1_0(b, l + 1);
    return true;
  }

  // ',' type_pack_parameter
  private static boolean par_list_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "par_list_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && type_pack_parameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // func_args | index_expr
  public static boolean postfix_exp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "postfix_exp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, POSTFIX_EXP, "<postfix exp>");
    r = func_args(b, l + 1);
    if (!r) r = index_expr(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // var_reference | '(' expression ')' | table_constructor
  public static boolean prefix_exp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prefix_exp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PREFIX_EXP, "<prefix exp>");
    r = var_reference(b, l + 1);
    if (!r) r = prefix_exp_1(b, l + 1);
    if (!r) r = table_constructor(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '(' expression ')'
  private static boolean prefix_exp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prefix_exp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // prefix_exp postfix_exp*
  public static boolean primary_exp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary_exp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PRIMARY_EXP, "<primary exp>");
    r = prefix_exp(b, l + 1);
    r = r && primary_exp_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // postfix_exp*
  private static boolean primary_exp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary_exp_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!postfix_exp(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "primary_exp_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // table_prop_or_indexer (field_sep table_prop_or_indexer)* field_sep?
  public static boolean prop_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_list")) return false;
    if (!nextTokenIs(b, "<prop list>", ID, LBRACK)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PROP_LIST, "<prop list>");
    r = table_prop_or_indexer(b, l + 1);
    r = r && prop_list_1(b, l + 1);
    r = r && prop_list_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (field_sep table_prop_or_indexer)*
  private static boolean prop_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!prop_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "prop_list_1", c)) break;
    }
    return true;
  }

  // field_sep table_prop_or_indexer
  private static boolean prop_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = field_sep(b, l + 1);
    r = r && table_prop_or_indexer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // field_sep?
  private static boolean prop_list_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_list_2")) return false;
    field_sep(b, l + 1);
    return true;
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
  // type | type_pack
  public static boolean return_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RETURN_TYPE, "<return type>");
    r = type(b, l + 1);
    if (!r) r = type_pack(b, l + 1);
    exit_section_(b, l, m, r, false, null);
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
  // 'nil'
  //     | singleton_type
  //     | typeof_soft_keyword '(' expression ')'
  //     | ID ('.' ID)? ('<' type_params? '>')?
  //     | table_type
  //     | function_type
  //     | '(' type ')'
  public static boolean simple_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SIMPLE_TYPE, "<simple type>");
    r = consumeToken(b, NIL);
    if (!r) r = singleton_type(b, l + 1);
    if (!r) r = simple_type_2(b, l + 1);
    if (!r) r = simple_type_3(b, l + 1);
    if (!r) r = table_type(b, l + 1);
    if (!r) r = function_type(b, l + 1);
    if (!r) r = simple_type_6(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // typeof_soft_keyword '(' expression ')'
  private static boolean simple_type_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_type_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = typeof_soft_keyword(b, l + 1);
    r = r && consumeToken(b, LPAREN);
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // ID ('.' ID)? ('<' type_params? '>')?
  private static boolean simple_type_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_type_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    r = r && simple_type_3_1(b, l + 1);
    r = r && simple_type_3_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('.' ID)?
  private static boolean simple_type_3_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_type_3_1")) return false;
    simple_type_3_1_0(b, l + 1);
    return true;
  }

  // '.' ID
  private static boolean simple_type_3_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_type_3_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DOT, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('<' type_params? '>')?
  private static boolean simple_type_3_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_type_3_2")) return false;
    simple_type_3_2_0(b, l + 1);
    return true;
  }

  // '<' type_params? '>'
  private static boolean simple_type_3_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_type_3_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    r = r && simple_type_3_2_0_1(b, l + 1);
    r = r && consumeToken(b, GT);
    exit_section_(b, m, null, r);
    return r;
  }

  // type_params?
  private static boolean simple_type_3_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_type_3_2_0_1")) return false;
    type_params(b, l + 1);
    return true;
  }

  // '(' type ')'
  private static boolean simple_type_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_type_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && type(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
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
  // STRING | 'true' | 'false'
  public static boolean singleton_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "singleton_type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SINGLETON_TYPE, "<singleton type>");
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, TRUE);
    if (!r) r = consumeToken(b, FALSE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // type_declaration_statement
  //      | do_statement
  //      | while_statement
  //      | repeat_statement
  //      | if_statement
  //      | classic_for_statement
  //      | foreach_statement
  //      | def_statement
  //      | compound_op_statement
  //      | assignment_statement
  //      // Maybe not the best fix, but expressionStatement is moved below ifStatement on purpose
  //      // expressionStatement includes ifExpression but most of the time we want ifStatement to be matched first, e.g. to fix nested conditions
  //      | expression_statement
  public static boolean statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STATEMENT, "<statement>");
    r = type_declaration_statement(b, l + 1);
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
    exit_section_(b, l, m, r, false, LuauParser::statement_recover);
    return r;
  }

  /* ********************************************************** */
  // !(
  //   ID
  //   | ',' | ';' | 'end' | 'until' | continue_soft_keyword | 'elseif' | 'else' | '(' | TEMPLATE_STRING_EQUOTE | '}'
  //   | 'local' | 'do' | 'while' | 'repeat' | 'function' | 'if' | 'for' | 'return' | 'break'
  //   | 'nil' | 'true' | 'false' | STRING | NUMBER | export_soft_keyword | type_soft_keyword
  //   | un_op
  // )
  static boolean statement_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !statement_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ID
  //   | ',' | ';' | 'end' | 'until' | continue_soft_keyword | 'elseif' | 'else' | '(' | TEMPLATE_STRING_EQUOTE | '}'
  //   | 'local' | 'do' | 'while' | 'repeat' | 'function' | 'if' | 'for' | 'return' | 'break'
  //   | 'nil' | 'true' | 'false' | STRING | NUMBER | export_soft_keyword | type_soft_keyword
  //   | un_op
  private static boolean statement_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_recover_0")) return false;
    boolean r;
    r = consumeToken(b, ID);
    if (!r) r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, SEMI);
    if (!r) r = consumeToken(b, END);
    if (!r) r = consumeToken(b, UNTIL);
    if (!r) r = continue_soft_keyword(b, l + 1);
    if (!r) r = consumeToken(b, ELSEIF);
    if (!r) r = consumeToken(b, ELSE);
    if (!r) r = consumeToken(b, LPAREN);
    if (!r) r = consumeToken(b, TEMPLATE_STRING_EQUOTE);
    if (!r) r = consumeToken(b, RCURLY);
    if (!r) r = consumeToken(b, LOCAL);
    if (!r) r = consumeToken(b, DO);
    if (!r) r = consumeToken(b, WHILE);
    if (!r) r = consumeToken(b, REPEAT);
    if (!r) r = consumeToken(b, FUNCTION);
    if (!r) r = consumeToken(b, IF);
    if (!r) r = consumeToken(b, FOR);
    if (!r) r = consumeToken(b, RETURN);
    if (!r) r = consumeToken(b, BREAK);
    if (!r) r = consumeToken(b, NIL);
    if (!r) r = consumeToken(b, TRUE);
    if (!r) r = consumeToken(b, FALSE);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = export_soft_keyword(b, l + 1);
    if (!r) r = type_soft_keyword(b, l + 1);
    if (!r) r = un_op(b, l + 1);
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
  // '{' field_list? '}'
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

  // field_list?
  private static boolean table_constructor_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_constructor_1")) return false;
    field_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '[' type ']' ':' type
  public static boolean table_indexer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_indexer")) return false;
    if (!nextTokenIs(b, LBRACK)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACK);
    r = r && type(b, l + 1);
    r = r && consumeTokens(b, 0, RBRACK, COLON);
    r = r && type(b, l + 1);
    exit_section_(b, m, TABLE_INDEXER, r);
    return r;
  }

  /* ********************************************************** */
  // ID ':' type
  public static boolean table_prop(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_prop")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, COLON);
    r = r && type(b, l + 1);
    exit_section_(b, m, TABLE_PROP, r);
    return r;
  }

  /* ********************************************************** */
  // table_prop | table_indexer
  static boolean table_prop_or_indexer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_prop_or_indexer")) return false;
    if (!nextTokenIs(b, "", ID, LBRACK)) return false;
    boolean r;
    r = table_prop(b, l + 1);
    if (!r) r = table_indexer(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // '{' type '}' | '{' prop_list? '}'
  public static boolean table_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type")) return false;
    if (!nextTokenIs(b, LCURLY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = table_type_0(b, l + 1);
    if (!r) r = table_type_1(b, l + 1);
    exit_section_(b, m, TABLE_TYPE, r);
    return r;
  }

  // '{' type '}'
  private static boolean table_type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LCURLY);
    r = r && type(b, l + 1);
    r = r && consumeToken(b, RCURLY);
    exit_section_(b, m, null, r);
    return r;
  }

  // '{' prop_list? '}'
  private static boolean table_type_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LCURLY);
    r = r && table_type_1_1(b, l + 1);
    r = r && consumeToken(b, RCURLY);
    exit_section_(b, m, null, r);
    return r;
  }

  // prop_list?
  private static boolean table_type_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_type_1_1")) return false;
    prop_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TEMPLATE_STRING_SQUOTE (STRING | '{' expression '}')* TEMPLATE_STRING_EQUOTE
  public static boolean template_string(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "template_string")) return false;
    if (!nextTokenIs(b, TEMPLATE_STRING_SQUOTE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TEMPLATE_STRING, null);
    r = consumeToken(b, TEMPLATE_STRING_SQUOTE);
    p = r; // pin = 1
    r = r && report_error_(b, template_string_1(b, l + 1));
    r = p && consumeToken(b, TEMPLATE_STRING_EQUOTE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (STRING | '{' expression '}')*
  private static boolean template_string_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "template_string_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!template_string_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "template_string_1", c)) break;
    }
    return true;
  }

  // STRING | '{' expression '}'
  private static boolean template_string_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "template_string_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STRING);
    if (!r) r = template_string_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '{' expression '}'
  private static boolean template_string_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "template_string_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LCURLY);
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, RCURLY);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // simple_type '?'? (union_suffix | intersection_suffix)*
  public static boolean type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE, "<type>");
    r = simple_type(b, l + 1);
    r = r && type_1(b, l + 1);
    r = r && type_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '?'?
  private static boolean type_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_1")) return false;
    consumeToken(b, QUESTION);
    return true;
  }

  // (union_suffix | intersection_suffix)*
  private static boolean type_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!type_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "type_2", c)) break;
    }
    return true;
  }

  // union_suffix | intersection_suffix
  private static boolean type_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_2_0")) return false;
    boolean r;
    r = union_suffix(b, l + 1);
    if (!r) r = intersection_suffix(b, l + 1);
    return r;
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
  // type (',' type_list)? | '...' type
  public static boolean type_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE_LIST, "<type list>");
    r = type_list_0(b, l + 1);
    if (!r) r = type_list_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // type (',' type_list)?
  private static boolean type_list_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_list_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type(b, l + 1);
    r = r && type_list_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' type_list)?
  private static boolean type_list_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_list_0_1")) return false;
    type_list_0_1_0(b, l + 1);
    return true;
  }

  // ',' type_list
  private static boolean type_list_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_list_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && type_list(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '...' type
  private static boolean type_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_list_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELLIPSIS);
    r = r && type(b, l + 1);
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
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELLIPSIS);
    r = r && type_pack_parameter_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
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
  // (variadic_type_pack | generic_type_pack | type_pack | type) (',' type_params)?
  public static boolean type_params(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_params")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, TYPE_PARAMS, "<type params>");
    r = type_params_0(b, l + 1);
    r = r && type_params_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // variadic_type_pack | generic_type_pack | type_pack | type
  private static boolean type_params_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_params_0")) return false;
    boolean r;
    r = variadic_type_pack(b, l + 1);
    if (!r) r = generic_type_pack(b, l + 1);
    if (!r) r = type_pack(b, l + 1);
    if (!r) r = type(b, l + 1);
    return r;
  }

  // (',' type_params)?
  private static boolean type_params_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_params_1")) return false;
    type_params_1_0(b, l + 1);
    return true;
  }

  // ',' type_params
  private static boolean type_params_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_params_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && type_params(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
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
  // '-' | 'not' | '#'
  public static boolean un_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "un_op")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, UN_OP, "<un op>");
    r = consumeToken(b, MINUS);
    if (!r) r = consumeToken(b, NOT);
    if (!r) r = consumeToken(b, GETN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ('|' simple_type '?'?)+
  public static boolean union_suffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "union_suffix")) return false;
    if (!nextTokenIs(b, UNION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = union_suffix_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!union_suffix_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "union_suffix", c)) break;
    }
    exit_section_(b, m, UNION_SUFFIX, r);
    return r;
  }

  // '|' simple_type '?'?
  private static boolean union_suffix_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "union_suffix_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, UNION);
    r = r && simple_type(b, l + 1);
    r = r && union_suffix_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '?'?
  private static boolean union_suffix_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "union_suffix_0_2")) return false;
    consumeToken(b, QUESTION);
    return true;
  }

  /* ********************************************************** */
  // primary_exp
  public static boolean var(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "var")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VAR, "<var>");
    r = primary_exp(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // var (',' var)*
  public static boolean var_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "var_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VAR_LIST, "<var list>");
    r = var(b, l + 1);
    r = r && var_list_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (',' var)*
  private static boolean var_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "var_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!var_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "var_list_1", c)) break;
    }
    return true;
  }

  // ',' var
  private static boolean var_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "var_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && var(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean var_reference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "var_reference")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, VAR_REFERENCE, r);
    return r;
  }

  /* ********************************************************** */
  // '...' type
  public static boolean variadic_type_pack(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variadic_type_pack")) return false;
    if (!nextTokenIs(b, ELLIPSIS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELLIPSIS);
    r = r && type(b, l + 1);
    exit_section_(b, m, VARIADIC_TYPE_PACK, r);
    return r;
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
  // Expression root: expression
  // Operator priority table:
  // 0: ATOM(simple_exp)
  // 1: POSTFIX(as_exp)
  // 2: ATOM(unary_exp)
  // 3: BINARY(binary_exp)
  public static boolean expression(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expression")) return false;
    addVariant(b, "<expression>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<expression>");
    r = simple_exp(b, l + 1);
    if (!r) r = unary_exp(b, l + 1);
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
      if (g < 1 && leftMarkerIs(b, SIMPLE_EXP) && as_exp_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, AS_EXP, r, true, null);
      }
      else if (g < 3 && bin_op(b, l + 1)) {
        r = expression(b, l, 3);
        exit_section_(b, l, m, BINARY_EXP, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  // table_constructor | closure_exp | ifelse_exp | literal_exp | template_string | primary_exp
  public static boolean simple_exp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_exp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SIMPLE_EXP, "<simple exp>");
    r = table_constructor(b, l + 1);
    if (!r) r = closure_exp(b, l + 1);
    if (!r) r = ifelse_exp(b, l + 1);
    if (!r) r = literal_exp(b, l + 1);
    if (!r) r = template_string(b, l + 1);
    if (!r) r = primary_exp(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '::' type
  private static boolean as_exp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "as_exp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, DOUBLE_COLON);
    r = r && type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // un_op expression
  public static boolean unary_exp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_exp")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, UNARY_EXP, "<unary exp>");
    r = un_op(b, l + 1);
    p = r; // pin = 1
    r = r && expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

}
