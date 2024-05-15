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

  public ASTNode parse(IElementType root_, PsiBuilder builder_) {
    parseLight(root_, builder_);
    return builder_.getTreeBuilt();
  }

  public void parseLight(IElementType root_, PsiBuilder builder_) {
    boolean result_;
    builder_ = adapt_builder_(root_, builder_, this, EXTENDS_SETS_);
    Marker marker_ = enter_section_(builder_, 0, _COLLAPSE_, null);
    result_ = parse_root_(root_, builder_);
    exit_section_(builder_, 0, marker_, root_, result_, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType root_, PsiBuilder builder_) {
    return parse_root_(root_, builder_, 0);
  }

  static boolean parse_root_(IElementType root_, PsiBuilder builder_, int level_) {
    return luau_file(builder_, level_ + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(AS_EXP, BINARY_EXP, EXPRESSION, SIMPLE_EXP,
      UNARY_EXP),
  };

  /* ********************************************************** */
  // varList '=' exp_list | 'local' binding_list ('=' exp_list)? | var compound_op expression
  public static boolean assignment_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignment_statement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ASSIGNMENT_STATEMENT, "<assignment statement>");
    result_ = assignment_statement_0(builder_, level_ + 1);
    if (!result_) result_ = assignment_statement_1(builder_, level_ + 1);
    if (!result_) result_ = assignment_statement_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // varList '=' exp_list
  private static boolean assignment_statement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignment_statement_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = varList(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ASSIGN);
    result_ = result_ && exp_list(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // 'local' binding_list ('=' exp_list)?
  private static boolean assignment_statement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignment_statement_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LOCAL);
    result_ = result_ && binding_list(builder_, level_ + 1);
    result_ = result_ && assignment_statement_1_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ('=' exp_list)?
  private static boolean assignment_statement_1_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignment_statement_1_2")) return false;
    assignment_statement_1_2_0(builder_, level_ + 1);
    return true;
  }

  // '=' exp_list
  private static boolean assignment_statement_1_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignment_statement_1_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ASSIGN);
    result_ = result_ && exp_list(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // var compound_op expression
  private static boolean assignment_statement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignment_statement_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = var(builder_, level_ + 1);
    result_ = result_ && compound_op(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // '+' | '-' | '*' | '/' | '//' | '^' | '%' | '..' | '<' | '<=' | '>' | '>=' | '==' | '~=' | 'and' | 'or'
  public static boolean bin_op(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "bin_op")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, BIN_OP, "<bin op>");
    result_ = consumeToken(builder_, PLUS);
    if (!result_) result_ = consumeToken(builder_, MINUS);
    if (!result_) result_ = consumeToken(builder_, MULT);
    if (!result_) result_ = consumeToken(builder_, DIV);
    if (!result_) result_ = consumeToken(builder_, DOUBLE_DIV);
    if (!result_) result_ = consumeToken(builder_, EXP);
    if (!result_) result_ = consumeToken(builder_, MOD);
    if (!result_) result_ = consumeToken(builder_, CONCAT);
    if (!result_) result_ = consumeToken(builder_, LT);
    if (!result_) result_ = consumeToken(builder_, LE);
    if (!result_) result_ = consumeToken(builder_, GT);
    if (!result_) result_ = consumeToken(builder_, GE);
    if (!result_) result_ = consumeToken(builder_, EQ);
    if (!result_) result_ = consumeToken(builder_, NE);
    if (!result_) result_ = consumeToken(builder_, AND);
    if (!result_) result_ = consumeToken(builder_, OR);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // ID (':' type)?
  public static boolean binding(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "binding")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ID);
    result_ = result_ && binding_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, BINDING, result_);
    return result_;
  }

  // (':' type)?
  private static boolean binding_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "binding_1")) return false;
    binding_1_0(builder_, level_ + 1);
    return true;
  }

  // ':' type
  private static boolean binding_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "binding_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COLON);
    result_ = result_ && type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // binding (',' binding)*
  public static boolean binding_list(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "binding_list")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = binding(builder_, level_ + 1);
    result_ = result_ && binding_list_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, BINDING_LIST, result_);
    return result_;
  }

  // (',' binding)*
  private static boolean binding_list_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "binding_list_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!binding_list_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "binding_list_1", pos_)) break;
    }
    return true;
  }

  // ',' binding
  private static boolean binding_list_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "binding_list_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && binding(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // (statement ';'?)* (last_statement ';'?)?
  public static boolean block(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, BLOCK, "<block>");
    result_ = block_0(builder_, level_ + 1);
    result_ = result_ && block_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (statement ';'?)*
  private static boolean block_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!block_0_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "block_0", pos_)) break;
    }
    return true;
  }

  // statement ';'?
  private static boolean block_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = statement(builder_, level_ + 1);
    result_ = result_ && block_0_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ';'?
  private static boolean block_0_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_0_0_1")) return false;
    consumeToken(builder_, SEMI);
    return true;
  }

  // (last_statement ';'?)?
  private static boolean block_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_1")) return false;
    block_1_0(builder_, level_ + 1);
    return true;
  }

  // last_statement ';'?
  private static boolean block_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = last_statement(builder_, level_ + 1);
    result_ = result_ && block_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ';'?
  private static boolean block_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_1_0_1")) return false;
    consumeToken(builder_, SEMI);
    return true;
  }

  /* ********************************************************** */
  // (ID ':')? type (',' bound_type_list)? | '...' type
  public static boolean bound_type_list(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "bound_type_list")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, BOUND_TYPE_LIST, "<bound type list>");
    result_ = bound_type_list_0(builder_, level_ + 1);
    if (!result_) result_ = bound_type_list_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (ID ':')? type (',' bound_type_list)?
  private static boolean bound_type_list_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "bound_type_list_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = bound_type_list_0_0(builder_, level_ + 1);
    result_ = result_ && type(builder_, level_ + 1);
    result_ = result_ && bound_type_list_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (ID ':')?
  private static boolean bound_type_list_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "bound_type_list_0_0")) return false;
    bound_type_list_0_0_0(builder_, level_ + 1);
    return true;
  }

  // ID ':'
  private static boolean bound_type_list_0_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "bound_type_list_0_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, COLON);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' bound_type_list)?
  private static boolean bound_type_list_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "bound_type_list_0_2")) return false;
    bound_type_list_0_2_0(builder_, level_ + 1);
    return true;
  }

  // ',' bound_type_list
  private static boolean bound_type_list_0_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "bound_type_list_0_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && bound_type_list(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '...' type
  private static boolean bound_type_list_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "bound_type_list_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ELLIPSIS);
    result_ = result_ && type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'for' binding '=' expression ',' expression (',' expression)? 'do' block 'end'
  public static boolean classic_for_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "classic_for_statement")) return false;
    if (!nextTokenIs(builder_, FOR)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, FOR);
    result_ = result_ && binding(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ASSIGN);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, COMMA);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && classic_for_statement_6(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, DO);
    result_ = result_ && block(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    exit_section_(builder_, marker_, CLASSIC_FOR_STATEMENT, result_);
    return result_;
  }

  // (',' expression)?
  private static boolean classic_for_statement_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "classic_for_statement_6")) return false;
    classic_for_statement_6_0(builder_, level_ + 1);
    return true;
  }

  // ',' expression
  private static boolean classic_for_statement_6_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "classic_for_statement_6_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'function' func_body
  public static boolean closure_exp(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "closure_exp")) return false;
    if (!nextTokenIs(builder_, FUNCTION)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, FUNCTION);
    result_ = result_ && func_body(builder_, level_ + 1);
    exit_section_(builder_, marker_, CLOSURE_EXP, result_);
    return result_;
  }

  /* ********************************************************** */
  // '+=' | '-=' | '*=' | '/=' | '%=' | '^=' | '..=' | '//='
  public static boolean compound_op(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compound_op")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMPOUND_OP, "<compound op>");
    result_ = consumeToken(builder_, PLUS_EQ);
    if (!result_) result_ = consumeToken(builder_, MINUS_EQ);
    if (!result_) result_ = consumeToken(builder_, MULT_EQ);
    if (!result_) result_ = consumeToken(builder_, DIV_EQ);
    if (!result_) result_ = consumeToken(builder_, MOD_EQ);
    if (!result_) result_ = consumeToken(builder_, EXP_EQ);
    if (!result_) result_ = consumeToken(builder_, CONCAT_EQ);
    if (!result_) result_ = consumeToken(builder_, DOUBLE_DIV_EQ);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // 'continue'
  public static boolean continue_soft_keyword(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "continue_soft_keyword")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CONTINUE_SOFT_KEYWORD, "<continue soft keyword>");
    result_ = consumeToken(builder_, "continue");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // class_method_def_statement | func_def_statement | local_func_def_statement
  static boolean def_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "def_statement")) return false;
    boolean result_;
    result_ = consumeToken(builder_, CLASS_METHOD_DEF_STATEMENT);
    if (!result_) result_ = func_def_statement(builder_, level_ + 1);
    if (!result_) result_ = local_func_def_statement(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // 'do' block 'end'
  public static boolean do_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "do_statement")) return false;
    if (!nextTokenIs(builder_, DO)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, DO);
    result_ = result_ && block(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    exit_section_(builder_, marker_, DO_STATEMENT, result_);
    return result_;
  }

  /* ********************************************************** */
  // (expression ',')* expression
  public static boolean exp_list(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "exp_list")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, EXP_LIST, "<exp list>");
    result_ = exp_list_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (expression ',')*
  private static boolean exp_list_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "exp_list_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!exp_list_0_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "exp_list_0", pos_)) break;
    }
    return true;
  }

  // expression ','
  private static boolean exp_list_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "exp_list_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, COMMA);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'export'
  public static boolean export_soft_keyword(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "export_soft_keyword")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, EXPORT_SOFT_KEYWORD, "<export soft keyword>");
    result_ = consumeToken(builder_, "export");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // expression
  public static boolean expression_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expression_statement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, EXPRESSION_STATEMENT, "<expression statement>");
    result_ = expression(builder_, level_ + 1, -1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // '[' expression ']' '=' expression | ID '=' expression | expression
  public static boolean field(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "field")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FIELD, "<field>");
    result_ = field_0(builder_, level_ + 1);
    if (!result_) result_ = field_1(builder_, level_ + 1);
    if (!result_) result_ = expression(builder_, level_ + 1, -1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // '[' expression ']' '=' expression
  private static boolean field_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "field_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACK);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeTokens(builder_, 0, RBRACK, ASSIGN);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ID '=' expression
  private static boolean field_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "field_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, ASSIGN);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // field (field_sep field)* field_sep?
  public static boolean field_list(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "field_list")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FIELD_LIST, "<field list>");
    result_ = field(builder_, level_ + 1);
    result_ = result_ && field_list_1(builder_, level_ + 1);
    result_ = result_ && field_list_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (field_sep field)*
  private static boolean field_list_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "field_list_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!field_list_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "field_list_1", pos_)) break;
    }
    return true;
  }

  // field_sep field
  private static boolean field_list_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "field_list_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = field_sep(builder_, level_ + 1);
    result_ = result_ && field(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // field_sep?
  private static boolean field_list_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "field_list_2")) return false;
    field_sep(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // ',' | ';'
  public static boolean field_sep(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "field_sep")) return false;
    if (!nextTokenIs(builder_, "<field sep>", COMMA, SEMI)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FIELD_SEP, "<field sep>");
    result_ = consumeToken(builder_, COMMA);
    if (!result_) result_ = consumeToken(builder_, SEMI);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // 'for' binding_list 'in' exp_list 'do' block 'end'
  public static boolean foreach_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "foreach_statement")) return false;
    if (!nextTokenIs(builder_, FOR)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, FOR);
    result_ = result_ && binding_list(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, IN);
    result_ = result_ && exp_list(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, DO);
    result_ = result_ && block(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    exit_section_(builder_, marker_, FOREACH_STATEMENT, result_);
    return result_;
  }

  /* ********************************************************** */
  // '(' exp_list? ')' | table_constructor | STRING
  public static boolean func_args(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_args")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FUNC_ARGS, "<func args>");
    result_ = func_args_0(builder_, level_ + 1);
    if (!result_) result_ = table_constructor(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, STRING);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // '(' exp_list? ')'
  private static boolean func_args_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_args_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && func_args_0_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // exp_list?
  private static boolean func_args_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_args_0_1")) return false;
    exp_list(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // ('<' generic_type_list '>')? '(' par_list? ')' (':' return_type)? block 'end'
  public static boolean func_body(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_body")) return false;
    if (!nextTokenIs(builder_, "<func body>", LPAREN, LT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FUNC_BODY, "<func body>");
    result_ = func_body_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, LPAREN);
    result_ = result_ && func_body_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    result_ = result_ && func_body_4(builder_, level_ + 1);
    result_ = result_ && block(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ('<' generic_type_list '>')?
  private static boolean func_body_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_body_0")) return false;
    func_body_0_0(builder_, level_ + 1);
    return true;
  }

  // '<' generic_type_list '>'
  private static boolean func_body_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_body_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LT);
    result_ = result_ && generic_type_list(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, GT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // par_list?
  private static boolean func_body_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_body_2")) return false;
    par_list(builder_, level_ + 1);
    return true;
  }

  // (':' return_type)?
  private static boolean func_body_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_body_4")) return false;
    func_body_4_0(builder_, level_ + 1);
    return true;
  }

  // ':' return_type
  private static boolean func_body_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_body_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COLON);
    result_ = result_ && return_type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'function' func_name func_body
  public static boolean func_def_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_def_statement")) return false;
    if (!nextTokenIs(builder_, FUNCTION)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, FUNCTION);
    result_ = result_ && func_name(builder_, level_ + 1);
    result_ = result_ && func_body(builder_, level_ + 1);
    exit_section_(builder_, marker_, FUNC_DEF_STATEMENT, result_);
    return result_;
  }

  /* ********************************************************** */
  // ID ('.' ID)* (':' ID)?
  public static boolean func_name(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_name")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ID);
    result_ = result_ && func_name_1(builder_, level_ + 1);
    result_ = result_ && func_name_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, FUNC_NAME, result_);
    return result_;
  }

  // ('.' ID)*
  private static boolean func_name_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_name_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!func_name_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "func_name_1", pos_)) break;
    }
    return true;
  }

  // '.' ID
  private static boolean func_name_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_name_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, DOT, ID);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (':' ID)?
  private static boolean func_name_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_name_2")) return false;
    func_name_2_0(builder_, level_ + 1);
    return true;
  }

  // ':' ID
  private static boolean func_name_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_name_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COLON, ID);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // prefix_exp postfix_exp
  public static boolean function_call(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "function_call")) return false;
    if (!nextTokenIs(builder_, "<function call>", ID, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FUNCTION_CALL, "<function call>");
    result_ = prefix_exp(builder_, level_ + 1);
    result_ = result_ && postfix_exp(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // ('<' generic_type_list '>')? '(' bound_type_list? ')' '->' return_type
  public static boolean function_type(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "function_type")) return false;
    if (!nextTokenIs(builder_, "<function type>", LPAREN, LT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FUNCTION_TYPE, "<function type>");
    result_ = function_type_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, LPAREN);
    result_ = result_ && function_type_2(builder_, level_ + 1);
    result_ = result_ && consumeTokens(builder_, 0, RPAREN, ARROW);
    result_ = result_ && return_type(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ('<' generic_type_list '>')?
  private static boolean function_type_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "function_type_0")) return false;
    function_type_0_0(builder_, level_ + 1);
    return true;
  }

  // '<' generic_type_list '>'
  private static boolean function_type_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "function_type_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LT);
    result_ = result_ && generic_type_list(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, GT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // bound_type_list?
  private static boolean function_type_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "function_type_2")) return false;
    bound_type_list(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // ID (',' generic_type_list)? | generic_type_pack_parameter (',' generic_type_pack_parameter)*
  public static boolean generic_type_list(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = generic_type_list_0(builder_, level_ + 1);
    if (!result_) result_ = generic_type_list_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, GENERIC_TYPE_LIST, result_);
    return result_;
  }

  // ID (',' generic_type_list)?
  private static boolean generic_type_list_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ID);
    result_ = result_ && generic_type_list_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' generic_type_list)?
  private static boolean generic_type_list_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_0_1")) return false;
    generic_type_list_0_1_0(builder_, level_ + 1);
    return true;
  }

  // ',' generic_type_list
  private static boolean generic_type_list_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && generic_type_list(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // generic_type_pack_parameter (',' generic_type_pack_parameter)*
  private static boolean generic_type_list_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = generic_type_pack_parameter(builder_, level_ + 1);
    result_ = result_ && generic_type_list_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' generic_type_pack_parameter)*
  private static boolean generic_type_list_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_1_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!generic_type_list_1_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "generic_type_list_1_1", pos_)) break;
    }
    return true;
  }

  // ',' generic_type_pack_parameter
  private static boolean generic_type_list_1_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_1_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && generic_type_pack_parameter(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // generic_type_list (',' generic_type_pack_parameter_with_default)*
  //     | ID (',' ID)* (',' ID '=' type)* (',' generic_type_pack_parameter_with_default)*
  //     | ID '=' type (',' generic_type_pack_parameter_with_default)*
  //     | generic_type_pack_parameter_with_default (',' generic_type_pack_parameter_with_default)*
  public static boolean generic_type_list_with_defaults(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = generic_type_list_with_defaults_0(builder_, level_ + 1);
    if (!result_) result_ = generic_type_list_with_defaults_1(builder_, level_ + 1);
    if (!result_) result_ = generic_type_list_with_defaults_2(builder_, level_ + 1);
    if (!result_) result_ = generic_type_list_with_defaults_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, GENERIC_TYPE_LIST_WITH_DEFAULTS, result_);
    return result_;
  }

  // generic_type_list (',' generic_type_pack_parameter_with_default)*
  private static boolean generic_type_list_with_defaults_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = generic_type_list(builder_, level_ + 1);
    result_ = result_ && generic_type_list_with_defaults_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' generic_type_pack_parameter_with_default)*
  private static boolean generic_type_list_with_defaults_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_0_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!generic_type_list_with_defaults_0_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "generic_type_list_with_defaults_0_1", pos_)) break;
    }
    return true;
  }

  // ',' generic_type_pack_parameter_with_default
  private static boolean generic_type_list_with_defaults_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && generic_type_pack_parameter_with_default(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ID (',' ID)* (',' ID '=' type)* (',' generic_type_pack_parameter_with_default)*
  private static boolean generic_type_list_with_defaults_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ID);
    result_ = result_ && generic_type_list_with_defaults_1_1(builder_, level_ + 1);
    result_ = result_ && generic_type_list_with_defaults_1_2(builder_, level_ + 1);
    result_ = result_ && generic_type_list_with_defaults_1_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' ID)*
  private static boolean generic_type_list_with_defaults_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_1_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!generic_type_list_with_defaults_1_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "generic_type_list_with_defaults_1_1", pos_)) break;
    }
    return true;
  }

  // ',' ID
  private static boolean generic_type_list_with_defaults_1_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_1_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COMMA, ID);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' ID '=' type)*
  private static boolean generic_type_list_with_defaults_1_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_1_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!generic_type_list_with_defaults_1_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "generic_type_list_with_defaults_1_2", pos_)) break;
    }
    return true;
  }

  // ',' ID '=' type
  private static boolean generic_type_list_with_defaults_1_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_1_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COMMA, ID, ASSIGN);
    result_ = result_ && type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' generic_type_pack_parameter_with_default)*
  private static boolean generic_type_list_with_defaults_1_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_1_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!generic_type_list_with_defaults_1_3_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "generic_type_list_with_defaults_1_3", pos_)) break;
    }
    return true;
  }

  // ',' generic_type_pack_parameter_with_default
  private static boolean generic_type_list_with_defaults_1_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_1_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && generic_type_pack_parameter_with_default(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ID '=' type (',' generic_type_pack_parameter_with_default)*
  private static boolean generic_type_list_with_defaults_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, ASSIGN);
    result_ = result_ && type(builder_, level_ + 1);
    result_ = result_ && generic_type_list_with_defaults_2_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' generic_type_pack_parameter_with_default)*
  private static boolean generic_type_list_with_defaults_2_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_2_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!generic_type_list_with_defaults_2_3_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "generic_type_list_with_defaults_2_3", pos_)) break;
    }
    return true;
  }

  // ',' generic_type_pack_parameter_with_default
  private static boolean generic_type_list_with_defaults_2_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_2_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && generic_type_pack_parameter_with_default(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // generic_type_pack_parameter_with_default (',' generic_type_pack_parameter_with_default)*
  private static boolean generic_type_list_with_defaults_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = generic_type_pack_parameter_with_default(builder_, level_ + 1);
    result_ = result_ && generic_type_list_with_defaults_3_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' generic_type_pack_parameter_with_default)*
  private static boolean generic_type_list_with_defaults_3_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_3_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!generic_type_list_with_defaults_3_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "generic_type_list_with_defaults_3_1", pos_)) break;
    }
    return true;
  }

  // ',' generic_type_pack_parameter_with_default
  private static boolean generic_type_list_with_defaults_3_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_list_with_defaults_3_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && generic_type_pack_parameter_with_default(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // ID '...'
  public static boolean generic_type_pack(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_pack")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, ELLIPSIS);
    exit_section_(builder_, marker_, GENERIC_TYPE_PACK, result_);
    return result_;
  }

  /* ********************************************************** */
  // ID '...'
  public static boolean generic_type_pack_parameter(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_pack_parameter")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, ELLIPSIS);
    exit_section_(builder_, marker_, GENERIC_TYPE_PACK_PARAMETER, result_);
    return result_;
  }

  /* ********************************************************** */
  // ID '...' '=' (type_pack | variadic_type_pack | generic_type_pack)
  public static boolean generic_type_pack_parameter_with_default(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_pack_parameter_with_default")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, ELLIPSIS, ASSIGN);
    result_ = result_ && generic_type_pack_parameter_with_default_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, GENERIC_TYPE_PACK_PARAMETER_WITH_DEFAULT, result_);
    return result_;
  }

  // type_pack | variadic_type_pack | generic_type_pack
  private static boolean generic_type_pack_parameter_with_default_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_type_pack_parameter_with_default_3")) return false;
    boolean result_;
    result_ = type_pack(builder_, level_ + 1);
    if (!result_) result_ = variadic_type_pack(builder_, level_ + 1);
    if (!result_) result_ = generic_type_pack(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // 'if' expression 'then' block ('elseif' expression 'then' block)* ('else' block)? 'end'
  public static boolean if_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "if_statement")) return false;
    if (!nextTokenIs(builder_, IF)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IF);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, THEN);
    result_ = result_ && block(builder_, level_ + 1);
    result_ = result_ && if_statement_4(builder_, level_ + 1);
    result_ = result_ && if_statement_5(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    exit_section_(builder_, marker_, IF_STATEMENT, result_);
    return result_;
  }

  // ('elseif' expression 'then' block)*
  private static boolean if_statement_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "if_statement_4")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!if_statement_4_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "if_statement_4", pos_)) break;
    }
    return true;
  }

  // 'elseif' expression 'then' block
  private static boolean if_statement_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "if_statement_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ELSEIF);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, THEN);
    result_ = result_ && block(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ('else' block)?
  private static boolean if_statement_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "if_statement_5")) return false;
    if_statement_5_0(builder_, level_ + 1);
    return true;
  }

  // 'else' block
  private static boolean if_statement_5_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "if_statement_5_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ELSE);
    result_ = result_ && block(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'if' expression 'then' expression ('elseif' expression 'then' expression)* 'else' expression
  public static boolean ifelse_exp(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifelse_exp")) return false;
    if (!nextTokenIs(builder_, IF)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IF);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, THEN);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && ifelse_exp_4(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ELSE);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    exit_section_(builder_, marker_, IFELSE_EXP, result_);
    return result_;
  }

  // ('elseif' expression 'then' expression)*
  private static boolean ifelse_exp_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifelse_exp_4")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!ifelse_exp_4_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "ifelse_exp_4", pos_)) break;
    }
    return true;
  }

  // 'elseif' expression 'then' expression
  private static boolean ifelse_exp_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifelse_exp_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ELSEIF);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, THEN);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // ('&' simple_type)+
  public static boolean intersection_suffix(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "intersection_suffix")) return false;
    if (!nextTokenIs(builder_, INTERSECTION)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = intersection_suffix_0(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!intersection_suffix_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "intersection_suffix", pos_)) break;
    }
    exit_section_(builder_, marker_, INTERSECTION_SUFFIX, result_);
    return result_;
  }

  // '&' simple_type
  private static boolean intersection_suffix_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "intersection_suffix_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, INTERSECTION);
    result_ = result_ && simple_type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'return' exp_list? | 'break' | continue_soft_keyword
  public static boolean last_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "last_statement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, LAST_STATEMENT, "<last statement>");
    result_ = last_statement_0(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, BREAK);
    if (!result_) result_ = continue_soft_keyword(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // 'return' exp_list?
  private static boolean last_statement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "last_statement_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, RETURN);
    result_ = result_ && last_statement_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // exp_list?
  private static boolean last_statement_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "last_statement_0_1")) return false;
    exp_list(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // 'local' 'function' func_name func_body
  public static boolean local_func_def_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "local_func_def_statement")) return false;
    if (!nextTokenIs(builder_, LOCAL)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, LOCAL, FUNCTION);
    result_ = result_ && func_name(builder_, level_ + 1);
    result_ = result_ && func_body(builder_, level_ + 1);
    exit_section_(builder_, marker_, LOCAL_FUNC_DEF_STATEMENT, result_);
    return result_;
  }

  /* ********************************************************** */
  // shebang_line? block
  static boolean luau_file(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "luau_file")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = luau_file_0(builder_, level_ + 1);
    result_ = result_ && block(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // shebang_line?
  private static boolean luau_file_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "luau_file_0")) return false;
    shebang_line(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // binding_list (',' '...')? | '...' (':' (type | generic_type_pack))?
  public static boolean par_list(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "par_list")) return false;
    if (!nextTokenIs(builder_, "<par list>", ELLIPSIS, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PAR_LIST, "<par list>");
    result_ = par_list_0(builder_, level_ + 1);
    if (!result_) result_ = par_list_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // binding_list (',' '...')?
  private static boolean par_list_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "par_list_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = binding_list(builder_, level_ + 1);
    result_ = result_ && par_list_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' '...')?
  private static boolean par_list_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "par_list_0_1")) return false;
    par_list_0_1_0(builder_, level_ + 1);
    return true;
  }

  // ',' '...'
  private static boolean par_list_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "par_list_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COMMA, ELLIPSIS);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '...' (':' (type | generic_type_pack))?
  private static boolean par_list_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "par_list_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ELLIPSIS);
    result_ = result_ && par_list_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (':' (type | generic_type_pack))?
  private static boolean par_list_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "par_list_1_1")) return false;
    par_list_1_1_0(builder_, level_ + 1);
    return true;
  }

  // ':' (type | generic_type_pack)
  private static boolean par_list_1_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "par_list_1_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COLON);
    result_ = result_ && par_list_1_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // type | generic_type_pack
  private static boolean par_list_1_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "par_list_1_1_0_1")) return false;
    boolean result_;
    result_ = type(builder_, level_ + 1);
    if (!result_) result_ = generic_type_pack(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // func_args | ':' ID func_args | '[' expression ']' | '.' ID
  public static boolean postfix_exp(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "postfix_exp")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, POSTFIX_EXP, "<postfix exp>");
    result_ = func_args(builder_, level_ + 1);
    if (!result_) result_ = postfix_exp_1(builder_, level_ + 1);
    if (!result_) result_ = postfix_exp_2(builder_, level_ + 1);
    if (!result_) result_ = parseTokens(builder_, 0, DOT, ID);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ':' ID func_args
  private static boolean postfix_exp_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "postfix_exp_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COLON, ID);
    result_ = result_ && func_args(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '[' expression ']'
  private static boolean postfix_exp_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "postfix_exp_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACK);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, RBRACK);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // (var | '(' expression ')') postfix_exp*
  public static boolean prefix_exp(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prefix_exp")) return false;
    if (!nextTokenIs(builder_, "<prefix exp>", ID, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PREFIX_EXP, "<prefix exp>");
    result_ = prefix_exp_0(builder_, level_ + 1);
    result_ = result_ && prefix_exp_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // var | '(' expression ')'
  private static boolean prefix_exp_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prefix_exp_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = var(builder_, level_ + 1);
    if (!result_) result_ = prefix_exp_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '(' expression ')'
  private static boolean prefix_exp_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prefix_exp_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // postfix_exp*
  private static boolean prefix_exp_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prefix_exp_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!postfix_exp(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "prefix_exp_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // table_prop_or_indexer (field_sep table_prop_or_indexer)* field_sep?
  public static boolean prop_list(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prop_list")) return false;
    if (!nextTokenIs(builder_, "<prop list>", ID, LBRACK)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PROP_LIST, "<prop list>");
    result_ = table_prop_or_indexer(builder_, level_ + 1);
    result_ = result_ && prop_list_1(builder_, level_ + 1);
    result_ = result_ && prop_list_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (field_sep table_prop_or_indexer)*
  private static boolean prop_list_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prop_list_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!prop_list_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "prop_list_1", pos_)) break;
    }
    return true;
  }

  // field_sep table_prop_or_indexer
  private static boolean prop_list_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prop_list_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = field_sep(builder_, level_ + 1);
    result_ = result_ && table_prop_or_indexer(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // field_sep?
  private static boolean prop_list_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prop_list_2")) return false;
    field_sep(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // 'repeat' block 'until' expression
  public static boolean repeat_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "repeat_statement")) return false;
    if (!nextTokenIs(builder_, REPEAT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, REPEAT);
    result_ = result_ && block(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, UNTIL);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    exit_section_(builder_, marker_, REPEAT_STATEMENT, result_);
    return result_;
  }

  /* ********************************************************** */
  // type | type_pack
  public static boolean return_type(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "return_type")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RETURN_TYPE, "<return type>");
    result_ = type(builder_, level_ + 1);
    if (!result_) result_ = type_pack(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // SHEBANG SHEBANG_CONTENT
  public static boolean shebang_line(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "shebang_line")) return false;
    if (!nextTokenIs(builder_, SHEBANG)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, SHEBANG, SHEBANG_CONTENT);
    exit_section_(builder_, marker_, SHEBANG_LINE, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'nil'
  //     | singleton_type
  //     | typeof_soft_keyword '(' expression ')'
  //     | ID ('.' ID)? ('<' type_params? '>')?
  //     | table_type
  //     | function_type
  //     | '(' type ')'
  public static boolean simple_type(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_type")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SIMPLE_TYPE, "<simple type>");
    result_ = consumeToken(builder_, NIL);
    if (!result_) result_ = singleton_type(builder_, level_ + 1);
    if (!result_) result_ = simple_type_2(builder_, level_ + 1);
    if (!result_) result_ = simple_type_3(builder_, level_ + 1);
    if (!result_) result_ = table_type(builder_, level_ + 1);
    if (!result_) result_ = function_type(builder_, level_ + 1);
    if (!result_) result_ = simple_type_6(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // typeof_soft_keyword '(' expression ')'
  private static boolean simple_type_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_type_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = typeof_soft_keyword(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ID ('.' ID)? ('<' type_params? '>')?
  private static boolean simple_type_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_type_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ID);
    result_ = result_ && simple_type_3_1(builder_, level_ + 1);
    result_ = result_ && simple_type_3_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ('.' ID)?
  private static boolean simple_type_3_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_type_3_1")) return false;
    simple_type_3_1_0(builder_, level_ + 1);
    return true;
  }

  // '.' ID
  private static boolean simple_type_3_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_type_3_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, DOT, ID);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ('<' type_params? '>')?
  private static boolean simple_type_3_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_type_3_2")) return false;
    simple_type_3_2_0(builder_, level_ + 1);
    return true;
  }

  // '<' type_params? '>'
  private static boolean simple_type_3_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_type_3_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LT);
    result_ = result_ && simple_type_3_2_0_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, GT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // type_params?
  private static boolean simple_type_3_2_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_type_3_2_0_1")) return false;
    type_params(builder_, level_ + 1);
    return true;
  }

  // '(' type ')'
  private static boolean simple_type_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_type_6")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && type(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // ID | '(' expression ')'
  public static boolean simple_var(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_var")) return false;
    if (!nextTokenIs(builder_, "<simple var>", ID, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SIMPLE_VAR, "<simple var>");
    result_ = consumeToken(builder_, ID);
    if (!result_) result_ = simple_var_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // '(' expression ')'
  private static boolean simple_var_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_var_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // STRING | 'true' | 'false'
  public static boolean singleton_type(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "singleton_type")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SINGLETON_TYPE, "<singleton type>");
    result_ = consumeToken(builder_, STRING);
    if (!result_) result_ = consumeToken(builder_, TRUE);
    if (!result_) result_ = consumeToken(builder_, FALSE);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // assignment_statement
  //      | type_declaration_statement
  //      | function_call
  //      | do_statement
  //      | while_statement
  //      | repeat_statement
  //      | if_statement
  //      // Maybe not the best fix, but expressionStatement is moved below ifStatement on purpose
  //      // expressionStatement includes ifExpression but most of the time we want ifStatement to be matched first, e.g. to fix nested conditions
  //      | expression_statement
  //      | classic_for_statement
  //      | foreach_statement
  //      | def_statement
  public static boolean statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "statement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, STATEMENT, "<statement>");
    result_ = assignment_statement(builder_, level_ + 1);
    if (!result_) result_ = type_declaration_statement(builder_, level_ + 1);
    if (!result_) result_ = function_call(builder_, level_ + 1);
    if (!result_) result_ = do_statement(builder_, level_ + 1);
    if (!result_) result_ = while_statement(builder_, level_ + 1);
    if (!result_) result_ = repeat_statement(builder_, level_ + 1);
    if (!result_) result_ = if_statement(builder_, level_ + 1);
    if (!result_) result_ = expression_statement(builder_, level_ + 1);
    if (!result_) result_ = classic_for_statement(builder_, level_ + 1);
    if (!result_) result_ = foreach_statement(builder_, level_ + 1);
    if (!result_) result_ = def_statement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // '{' field_list? '}'
  public static boolean table_constructor(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "table_constructor")) return false;
    if (!nextTokenIs(builder_, LCURLY)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LCURLY);
    result_ = result_ && table_constructor_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RCURLY);
    exit_section_(builder_, marker_, TABLE_CONSTRUCTOR, result_);
    return result_;
  }

  // field_list?
  private static boolean table_constructor_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "table_constructor_1")) return false;
    field_list(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // '[' type ']' ':' type
  public static boolean table_indexer(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "table_indexer")) return false;
    if (!nextTokenIs(builder_, LBRACK)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACK);
    result_ = result_ && type(builder_, level_ + 1);
    result_ = result_ && consumeTokens(builder_, 0, RBRACK, COLON);
    result_ = result_ && type(builder_, level_ + 1);
    exit_section_(builder_, marker_, TABLE_INDEXER, result_);
    return result_;
  }

  /* ********************************************************** */
  // ID ':' type
  public static boolean table_prop(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "table_prop")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, COLON);
    result_ = result_ && type(builder_, level_ + 1);
    exit_section_(builder_, marker_, TABLE_PROP, result_);
    return result_;
  }

  /* ********************************************************** */
  // table_prop | table_indexer
  static boolean table_prop_or_indexer(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "table_prop_or_indexer")) return false;
    if (!nextTokenIs(builder_, "", ID, LBRACK)) return false;
    boolean result_;
    result_ = table_prop(builder_, level_ + 1);
    if (!result_) result_ = table_indexer(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // '{' type '}' | '{' prop_list? '}'
  public static boolean table_type(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "table_type")) return false;
    if (!nextTokenIs(builder_, LCURLY)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = table_type_0(builder_, level_ + 1);
    if (!result_) result_ = table_type_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, TABLE_TYPE, result_);
    return result_;
  }

  // '{' type '}'
  private static boolean table_type_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "table_type_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LCURLY);
    result_ = result_ && type(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RCURLY);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '{' prop_list? '}'
  private static boolean table_type_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "table_type_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LCURLY);
    result_ = result_ && table_type_1_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RCURLY);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // prop_list?
  private static boolean table_type_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "table_type_1_1")) return false;
    prop_list(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // TEMPLATE_STRING_SQUOTE (STRING? '{' expression '}' STRING?)* TEMPLATE_STRING_EQUOTE
  public static boolean template_string(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "template_string")) return false;
    if (!nextTokenIs(builder_, TEMPLATE_STRING_SQUOTE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, TEMPLATE_STRING_SQUOTE);
    result_ = result_ && template_string_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, TEMPLATE_STRING_EQUOTE);
    exit_section_(builder_, marker_, TEMPLATE_STRING, result_);
    return result_;
  }

  // (STRING? '{' expression '}' STRING?)*
  private static boolean template_string_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "template_string_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!template_string_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "template_string_1", pos_)) break;
    }
    return true;
  }

  // STRING? '{' expression '}' STRING?
  private static boolean template_string_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "template_string_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = template_string_1_0_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, LCURLY);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, RCURLY);
    result_ = result_ && template_string_1_0_4(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // STRING?
  private static boolean template_string_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "template_string_1_0_0")) return false;
    consumeToken(builder_, STRING);
    return true;
  }

  // STRING?
  private static boolean template_string_1_0_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "template_string_1_0_4")) return false;
    consumeToken(builder_, STRING);
    return true;
  }

  /* ********************************************************** */
  // simple_type '?'? (union_suffix | intersection_suffix)*
  public static boolean type(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TYPE, "<type>");
    result_ = simple_type(builder_, level_ + 1);
    result_ = result_ && type_1(builder_, level_ + 1);
    result_ = result_ && type_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // '?'?
  private static boolean type_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_1")) return false;
    consumeToken(builder_, QUESTION);
    return true;
  }

  // (union_suffix | intersection_suffix)*
  private static boolean type_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!type_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "type_2", pos_)) break;
    }
    return true;
  }

  // union_suffix | intersection_suffix
  private static boolean type_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_2_0")) return false;
    boolean result_;
    result_ = union_suffix(builder_, level_ + 1);
    if (!result_) result_ = intersection_suffix(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // export_soft_keyword ? type_soft_keyword ID ('<' generic_type_list_with_defaults '>')? '=' type
  public static boolean type_declaration_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_declaration_statement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TYPE_DECLARATION_STATEMENT, "<type declaration statement>");
    result_ = type_declaration_statement_0(builder_, level_ + 1);
    result_ = result_ && type_soft_keyword(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ID);
    result_ = result_ && type_declaration_statement_3(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ASSIGN);
    result_ = result_ && type(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // export_soft_keyword ?
  private static boolean type_declaration_statement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_declaration_statement_0")) return false;
    export_soft_keyword(builder_, level_ + 1);
    return true;
  }

  // ('<' generic_type_list_with_defaults '>')?
  private static boolean type_declaration_statement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_declaration_statement_3")) return false;
    type_declaration_statement_3_0(builder_, level_ + 1);
    return true;
  }

  // '<' generic_type_list_with_defaults '>'
  private static boolean type_declaration_statement_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_declaration_statement_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LT);
    result_ = result_ && generic_type_list_with_defaults(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, GT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // type (',' type_list)? | '...' type
  public static boolean type_list(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_list")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TYPE_LIST, "<type list>");
    result_ = type_list_0(builder_, level_ + 1);
    if (!result_) result_ = type_list_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // type (',' type_list)?
  private static boolean type_list_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_list_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = type(builder_, level_ + 1);
    result_ = result_ && type_list_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' type_list)?
  private static boolean type_list_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_list_0_1")) return false;
    type_list_0_1_0(builder_, level_ + 1);
    return true;
  }

  // ',' type_list
  private static boolean type_list_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_list_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && type_list(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '...' type
  private static boolean type_list_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_list_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ELLIPSIS);
    result_ = result_ && type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // '(' type_list? ')'
  public static boolean type_pack(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_pack")) return false;
    if (!nextTokenIs(builder_, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && type_pack_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, TYPE_PACK, result_);
    return result_;
  }

  // type_list?
  private static boolean type_pack_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_pack_1")) return false;
    type_list(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // (type | type_pack | variadic_type_pack | generic_type_pack) (',' type_params)?
  public static boolean type_params(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_params")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _COLLAPSE_, TYPE_PARAMS, "<type params>");
    result_ = type_params_0(builder_, level_ + 1);
    result_ = result_ && type_params_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // type | type_pack | variadic_type_pack | generic_type_pack
  private static boolean type_params_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_params_0")) return false;
    boolean result_;
    result_ = type(builder_, level_ + 1);
    if (!result_) result_ = type_pack(builder_, level_ + 1);
    if (!result_) result_ = variadic_type_pack(builder_, level_ + 1);
    if (!result_) result_ = generic_type_pack(builder_, level_ + 1);
    return result_;
  }

  // (',' type_params)?
  private static boolean type_params_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_params_1")) return false;
    type_params_1_0(builder_, level_ + 1);
    return true;
  }

  // ',' type_params
  private static boolean type_params_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_params_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && type_params(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'type'
  public static boolean type_soft_keyword(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_soft_keyword")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TYPE_SOFT_KEYWORD, "<type soft keyword>");
    result_ = consumeToken(builder_, "type");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // 'typeof'
  public static boolean typeof_soft_keyword(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "typeof_soft_keyword")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TYPEOF_SOFT_KEYWORD, "<typeof soft keyword>");
    result_ = consumeToken(builder_, "typeof");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // '-' | 'not' | '#'
  public static boolean un_op(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "un_op")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, UN_OP, "<un op>");
    result_ = consumeToken(builder_, MINUS);
    if (!result_) result_ = consumeToken(builder_, NOT);
    if (!result_) result_ = consumeToken(builder_, GETN);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // ('|' simple_type '?'?)+
  public static boolean union_suffix(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "union_suffix")) return false;
    if (!nextTokenIs(builder_, UNION)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = union_suffix_0(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!union_suffix_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "union_suffix", pos_)) break;
    }
    exit_section_(builder_, marker_, UNION_SUFFIX, result_);
    return result_;
  }

  // '|' simple_type '?'?
  private static boolean union_suffix_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "union_suffix_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, UNION);
    result_ = result_ && simple_type(builder_, level_ + 1);
    result_ = result_ && union_suffix_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '?'?
  private static boolean union_suffix_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "union_suffix_0_2")) return false;
    consumeToken(builder_, QUESTION);
    return true;
  }

  /* ********************************************************** */
  // simple_var postfix_exp*
  public static boolean var(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "var")) return false;
    if (!nextTokenIs(builder_, "<var>", ID, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, VAR, "<var>");
    result_ = simple_var(builder_, level_ + 1);
    result_ = result_ && var_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // postfix_exp*
  private static boolean var_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "var_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!postfix_exp(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "var_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // var (',' var)*
  public static boolean varList(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "varList")) return false;
    if (!nextTokenIs(builder_, "<var list>", ID, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, VAR_LIST, "<var list>");
    result_ = var(builder_, level_ + 1);
    result_ = result_ && varList_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (',' var)*
  private static boolean varList_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "varList_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!varList_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "varList_1", pos_)) break;
    }
    return true;
  }

  // ',' var
  private static boolean varList_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "varList_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && var(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // '...' type
  public static boolean variadic_type_pack(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variadic_type_pack")) return false;
    if (!nextTokenIs(builder_, ELLIPSIS)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ELLIPSIS);
    result_ = result_ && type(builder_, level_ + 1);
    exit_section_(builder_, marker_, VARIADIC_TYPE_PACK, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'while' expression 'do' block 'end'
  public static boolean while_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "while_statement")) return false;
    if (!nextTokenIs(builder_, WHILE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, WHILE);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, DO);
    result_ = result_ && block(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    exit_section_(builder_, marker_, WHILE_STATEMENT, result_);
    return result_;
  }

  /* ********************************************************** */
  // Expression root: expression
  // Operator priority table:
  // 0: ATOM(simple_exp)
  // 1: POSTFIX(as_exp)
  // 2: PREFIX(unary_exp)
  // 3: BINARY(binary_exp)
  public static boolean expression(PsiBuilder builder_, int level_, int priority_) {
    if (!recursion_guard_(builder_, level_, "expression")) return false;
    addVariant(builder_, "<expression>");
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, "<expression>");
    result_ = simple_exp(builder_, level_ + 1);
    if (!result_) result_ = unary_exp(builder_, level_ + 1);
    pinned_ = result_;
    result_ = result_ && expression_0(builder_, level_ + 1, priority_);
    exit_section_(builder_, level_, marker_, null, result_, pinned_, null);
    return result_ || pinned_;
  }

  public static boolean expression_0(PsiBuilder builder_, int level_, int priority_) {
    if (!recursion_guard_(builder_, level_, "expression_0")) return false;
    boolean result_ = true;
    while (true) {
      Marker marker_ = enter_section_(builder_, level_, _LEFT_, null);
      if (priority_ < 1 && leftMarkerIs(builder_, SIMPLE_EXP) && as_exp_0(builder_, level_ + 1)) {
        result_ = true;
        exit_section_(builder_, level_, marker_, AS_EXP, result_, true, null);
      }
      else if (priority_ < 3 && bin_op(builder_, level_ + 1)) {
        result_ = expression(builder_, level_, 3);
        exit_section_(builder_, level_, marker_, BINARY_EXP, result_, true, null);
      }
      else {
        exit_section_(builder_, level_, marker_, null, false, false, null);
        break;
      }
    }
    return result_;
  }

  // NUMBER | STRING | 'nil' | 'true' | 'false' | '...' | table_constructor | closure_exp | prefix_exp | ifelse_exp | template_string
  public static boolean simple_exp(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_exp")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SIMPLE_EXP, "<simple exp>");
    result_ = consumeTokenSmart(builder_, NUMBER);
    if (!result_) result_ = consumeTokenSmart(builder_, STRING);
    if (!result_) result_ = consumeTokenSmart(builder_, NIL);
    if (!result_) result_ = consumeTokenSmart(builder_, TRUE);
    if (!result_) result_ = consumeTokenSmart(builder_, FALSE);
    if (!result_) result_ = consumeTokenSmart(builder_, ELLIPSIS);
    if (!result_) result_ = table_constructor(builder_, level_ + 1);
    if (!result_) result_ = closure_exp(builder_, level_ + 1);
    if (!result_) result_ = prefix_exp(builder_, level_ + 1);
    if (!result_) result_ = ifelse_exp(builder_, level_ + 1);
    if (!result_) result_ = template_string(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // '::' type
  private static boolean as_exp_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "as_exp_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokenSmart(builder_, DOUBLE_COLON);
    result_ = result_ && type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  public static boolean unary_exp(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "unary_exp")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = un_op(builder_, level_ + 1);
    pinned_ = result_;
    result_ = pinned_ && expression(builder_, level_, 2);
    exit_section_(builder_, level_, marker_, UNARY_EXP, result_, pinned_, null);
    return result_ || pinned_;
  }

}
