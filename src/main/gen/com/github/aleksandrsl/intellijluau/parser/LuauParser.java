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
    return luaFile(builder_, level_ + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(AS_EXP, BINARY_EXP, EXPRESSION, SIMPLE_EXP,
      UNARY_EXP),
  };

  /* ********************************************************** */
  // (ID ':')? Type (',' BoundTypeList)? | '...' Type
  public static boolean BoundTypeList(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "BoundTypeList")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, BOUND_TYPE_LIST, "<bound type list>");
    result_ = BoundTypeList_0(builder_, level_ + 1);
    if (!result_) result_ = BoundTypeList_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (ID ':')? Type (',' BoundTypeList)?
  private static boolean BoundTypeList_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "BoundTypeList_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = BoundTypeList_0_0(builder_, level_ + 1);
    result_ = result_ && Type(builder_, level_ + 1);
    result_ = result_ && BoundTypeList_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (ID ':')?
  private static boolean BoundTypeList_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "BoundTypeList_0_0")) return false;
    BoundTypeList_0_0_0(builder_, level_ + 1);
    return true;
  }

  // ID ':'
  private static boolean BoundTypeList_0_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "BoundTypeList_0_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, COLON);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' BoundTypeList)?
  private static boolean BoundTypeList_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "BoundTypeList_0_2")) return false;
    BoundTypeList_0_2_0(builder_, level_ + 1);
    return true;
  }

  // ',' BoundTypeList
  private static boolean BoundTypeList_0_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "BoundTypeList_0_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && BoundTypeList(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '...' Type
  private static boolean BoundTypeList_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "BoundTypeList_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ELLIPSIS);
    result_ = result_ && Type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'continue'
  public static boolean ContinueSoftKeyword(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ContinueSoftKeyword")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CONTINUE_SOFT_KEYWORD, "<continue soft keyword>");
    result_ = consumeToken(builder_, "continue");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // 'export'
  public static boolean ExportSoftKeyword(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ExportSoftKeyword")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, EXPORT_SOFT_KEYWORD, "<export soft keyword>");
    result_ = consumeToken(builder_, "export");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // ('<' GenericTypeList '>')? '(' BoundTypeList? ')' '->' ReturnType
  public static boolean FunctionType(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "FunctionType")) return false;
    if (!nextTokenIs(builder_, "<function type>", LPAREN, LT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FUNCTION_TYPE, "<function type>");
    result_ = FunctionType_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, LPAREN);
    result_ = result_ && FunctionType_2(builder_, level_ + 1);
    result_ = result_ && consumeTokens(builder_, 0, RPAREN, ARROW);
    result_ = result_ && ReturnType(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ('<' GenericTypeList '>')?
  private static boolean FunctionType_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "FunctionType_0")) return false;
    FunctionType_0_0(builder_, level_ + 1);
    return true;
  }

  // '<' GenericTypeList '>'
  private static boolean FunctionType_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "FunctionType_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LT);
    result_ = result_ && GenericTypeList(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, GT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // BoundTypeList?
  private static boolean FunctionType_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "FunctionType_2")) return false;
    BoundTypeList(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // ID (',' GenericTypeList)? | GenericTypePackParameter (',' GenericTypePackParameter)*
  public static boolean GenericTypeList(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeList")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = GenericTypeList_0(builder_, level_ + 1);
    if (!result_) result_ = GenericTypeList_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, GENERIC_TYPE_LIST, result_);
    return result_;
  }

  // ID (',' GenericTypeList)?
  private static boolean GenericTypeList_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeList_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ID);
    result_ = result_ && GenericTypeList_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' GenericTypeList)?
  private static boolean GenericTypeList_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeList_0_1")) return false;
    GenericTypeList_0_1_0(builder_, level_ + 1);
    return true;
  }

  // ',' GenericTypeList
  private static boolean GenericTypeList_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeList_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && GenericTypeList(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // GenericTypePackParameter (',' GenericTypePackParameter)*
  private static boolean GenericTypeList_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeList_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = GenericTypePackParameter(builder_, level_ + 1);
    result_ = result_ && GenericTypeList_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' GenericTypePackParameter)*
  private static boolean GenericTypeList_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeList_1_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!GenericTypeList_1_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "GenericTypeList_1_1", pos_)) break;
    }
    return true;
  }

  // ',' GenericTypePackParameter
  private static boolean GenericTypeList_1_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeList_1_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && GenericTypePackParameter(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // GenericTypeList (',' GenericTypePackParameterWithDefault)*
  //     | ID (',' ID)* (',' ID '=' Type)* (',' GenericTypePackParameterWithDefault)*
  //     | ID '=' Type (',' GenericTypePackParameterWithDefault)*
  //     | GenericTypePackParameterWithDefault (',' GenericTypePackParameterWithDefault)*
  public static boolean GenericTypeListWithDefaults(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = GenericTypeListWithDefaults_0(builder_, level_ + 1);
    if (!result_) result_ = GenericTypeListWithDefaults_1(builder_, level_ + 1);
    if (!result_) result_ = GenericTypeListWithDefaults_2(builder_, level_ + 1);
    if (!result_) result_ = GenericTypeListWithDefaults_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, GENERIC_TYPE_LIST_WITH_DEFAULTS, result_);
    return result_;
  }

  // GenericTypeList (',' GenericTypePackParameterWithDefault)*
  private static boolean GenericTypeListWithDefaults_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = GenericTypeList(builder_, level_ + 1);
    result_ = result_ && GenericTypeListWithDefaults_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' GenericTypePackParameterWithDefault)*
  private static boolean GenericTypeListWithDefaults_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_0_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!GenericTypeListWithDefaults_0_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "GenericTypeListWithDefaults_0_1", pos_)) break;
    }
    return true;
  }

  // ',' GenericTypePackParameterWithDefault
  private static boolean GenericTypeListWithDefaults_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && GenericTypePackParameterWithDefault(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ID (',' ID)* (',' ID '=' Type)* (',' GenericTypePackParameterWithDefault)*
  private static boolean GenericTypeListWithDefaults_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ID);
    result_ = result_ && GenericTypeListWithDefaults_1_1(builder_, level_ + 1);
    result_ = result_ && GenericTypeListWithDefaults_1_2(builder_, level_ + 1);
    result_ = result_ && GenericTypeListWithDefaults_1_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' ID)*
  private static boolean GenericTypeListWithDefaults_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_1_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!GenericTypeListWithDefaults_1_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "GenericTypeListWithDefaults_1_1", pos_)) break;
    }
    return true;
  }

  // ',' ID
  private static boolean GenericTypeListWithDefaults_1_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_1_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COMMA, ID);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' ID '=' Type)*
  private static boolean GenericTypeListWithDefaults_1_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_1_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!GenericTypeListWithDefaults_1_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "GenericTypeListWithDefaults_1_2", pos_)) break;
    }
    return true;
  }

  // ',' ID '=' Type
  private static boolean GenericTypeListWithDefaults_1_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_1_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COMMA, ID, ASSIGN);
    result_ = result_ && Type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' GenericTypePackParameterWithDefault)*
  private static boolean GenericTypeListWithDefaults_1_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_1_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!GenericTypeListWithDefaults_1_3_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "GenericTypeListWithDefaults_1_3", pos_)) break;
    }
    return true;
  }

  // ',' GenericTypePackParameterWithDefault
  private static boolean GenericTypeListWithDefaults_1_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_1_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && GenericTypePackParameterWithDefault(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ID '=' Type (',' GenericTypePackParameterWithDefault)*
  private static boolean GenericTypeListWithDefaults_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, ASSIGN);
    result_ = result_ && Type(builder_, level_ + 1);
    result_ = result_ && GenericTypeListWithDefaults_2_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' GenericTypePackParameterWithDefault)*
  private static boolean GenericTypeListWithDefaults_2_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_2_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!GenericTypeListWithDefaults_2_3_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "GenericTypeListWithDefaults_2_3", pos_)) break;
    }
    return true;
  }

  // ',' GenericTypePackParameterWithDefault
  private static boolean GenericTypeListWithDefaults_2_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_2_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && GenericTypePackParameterWithDefault(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // GenericTypePackParameterWithDefault (',' GenericTypePackParameterWithDefault)*
  private static boolean GenericTypeListWithDefaults_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = GenericTypePackParameterWithDefault(builder_, level_ + 1);
    result_ = result_ && GenericTypeListWithDefaults_3_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' GenericTypePackParameterWithDefault)*
  private static boolean GenericTypeListWithDefaults_3_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_3_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!GenericTypeListWithDefaults_3_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "GenericTypeListWithDefaults_3_1", pos_)) break;
    }
    return true;
  }

  // ',' GenericTypePackParameterWithDefault
  private static boolean GenericTypeListWithDefaults_3_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypeListWithDefaults_3_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && GenericTypePackParameterWithDefault(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // ID '...'
  public static boolean GenericTypePack(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypePack")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, ELLIPSIS);
    exit_section_(builder_, marker_, GENERIC_TYPE_PACK, result_);
    return result_;
  }

  /* ********************************************************** */
  // ID '...'
  public static boolean GenericTypePackParameter(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypePackParameter")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, ELLIPSIS);
    exit_section_(builder_, marker_, GENERIC_TYPE_PACK_PARAMETER, result_);
    return result_;
  }

  /* ********************************************************** */
  // ID '...' '=' (TypePack | VariadicTypePack | GenericTypePack)
  public static boolean GenericTypePackParameterWithDefault(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypePackParameterWithDefault")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, ELLIPSIS, ASSIGN);
    result_ = result_ && GenericTypePackParameterWithDefault_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, GENERIC_TYPE_PACK_PARAMETER_WITH_DEFAULT, result_);
    return result_;
  }

  // TypePack | VariadicTypePack | GenericTypePack
  private static boolean GenericTypePackParameterWithDefault_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "GenericTypePackParameterWithDefault_3")) return false;
    boolean result_;
    result_ = TypePack(builder_, level_ + 1);
    if (!result_) result_ = VariadicTypePack(builder_, level_ + 1);
    if (!result_) result_ = GenericTypePack(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // ('&' SimpleType)+
  public static boolean IntersectionSuffix(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IntersectionSuffix")) return false;
    if (!nextTokenIs(builder_, INTERSECTION)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = IntersectionSuffix_0(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!IntersectionSuffix_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "IntersectionSuffix", pos_)) break;
    }
    exit_section_(builder_, marker_, INTERSECTION_SUFFIX, result_);
    return result_;
  }

  // '&' SimpleType
  private static boolean IntersectionSuffix_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "IntersectionSuffix_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, INTERSECTION);
    result_ = result_ && SimpleType(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // TablePropOrIndexer (fieldsep TablePropOrIndexer)* fieldsep?
  public static boolean PropList(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PropList")) return false;
    if (!nextTokenIs(builder_, "<prop list>", ID, LBRACK)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PROP_LIST, "<prop list>");
    result_ = TablePropOrIndexer(builder_, level_ + 1);
    result_ = result_ && PropList_1(builder_, level_ + 1);
    result_ = result_ && PropList_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (fieldsep TablePropOrIndexer)*
  private static boolean PropList_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PropList_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!PropList_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "PropList_1", pos_)) break;
    }
    return true;
  }

  // fieldsep TablePropOrIndexer
  private static boolean PropList_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PropList_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = fieldsep(builder_, level_ + 1);
    result_ = result_ && TablePropOrIndexer(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // fieldsep?
  private static boolean PropList_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "PropList_2")) return false;
    fieldsep(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // Type | TypePack
  public static boolean ReturnType(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ReturnType")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RETURN_TYPE, "<return type>");
    result_ = Type(builder_, level_ + 1);
    if (!result_) result_ = TypePack(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // 'nil' |
  //     SingletonType |
  //     ID ('.' ID)? ('<' TypeParams? '>')? |
  //     'typeof' '(' expression ')' |
  //     TableType |
  //     FunctionType |
  //     '(' Type ')'
  public static boolean SimpleType(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "SimpleType")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SIMPLE_TYPE, "<simple type>");
    result_ = consumeToken(builder_, NIL);
    if (!result_) result_ = SingletonType(builder_, level_ + 1);
    if (!result_) result_ = SimpleType_2(builder_, level_ + 1);
    if (!result_) result_ = SimpleType_3(builder_, level_ + 1);
    if (!result_) result_ = TableType(builder_, level_ + 1);
    if (!result_) result_ = FunctionType(builder_, level_ + 1);
    if (!result_) result_ = SimpleType_6(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ID ('.' ID)? ('<' TypeParams? '>')?
  private static boolean SimpleType_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "SimpleType_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ID);
    result_ = result_ && SimpleType_2_1(builder_, level_ + 1);
    result_ = result_ && SimpleType_2_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ('.' ID)?
  private static boolean SimpleType_2_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "SimpleType_2_1")) return false;
    SimpleType_2_1_0(builder_, level_ + 1);
    return true;
  }

  // '.' ID
  private static boolean SimpleType_2_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "SimpleType_2_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, DOT, ID);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ('<' TypeParams? '>')?
  private static boolean SimpleType_2_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "SimpleType_2_2")) return false;
    SimpleType_2_2_0(builder_, level_ + 1);
    return true;
  }

  // '<' TypeParams? '>'
  private static boolean SimpleType_2_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "SimpleType_2_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LT);
    result_ = result_ && SimpleType_2_2_0_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, GT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // TypeParams?
  private static boolean SimpleType_2_2_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "SimpleType_2_2_0_1")) return false;
    TypeParams(builder_, level_ + 1);
    return true;
  }

  // 'typeof' '(' expression ')'
  private static boolean SimpleType_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "SimpleType_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, "typeof");
    result_ = result_ && consumeToken(builder_, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '(' Type ')'
  private static boolean SimpleType_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "SimpleType_6")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && Type(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // STRING | 'true' | 'false'
  public static boolean SingletonType(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "SingletonType")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SINGLETON_TYPE, "<singleton type>");
    result_ = consumeToken(builder_, STRING);
    if (!result_) result_ = consumeToken(builder_, TRUE);
    if (!result_) result_ = consumeToken(builder_, FALSE);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // '[' Type ']' ':' Type
  public static boolean TableIndexer(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TableIndexer")) return false;
    if (!nextTokenIs(builder_, LBRACK)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACK);
    result_ = result_ && Type(builder_, level_ + 1);
    result_ = result_ && consumeTokens(builder_, 0, RBRACK, COLON);
    result_ = result_ && Type(builder_, level_ + 1);
    exit_section_(builder_, marker_, TABLE_INDEXER, result_);
    return result_;
  }

  /* ********************************************************** */
  // ID ':' Type
  public static boolean TableProp(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TableProp")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, COLON);
    result_ = result_ && Type(builder_, level_ + 1);
    exit_section_(builder_, marker_, TABLE_PROP, result_);
    return result_;
  }

  /* ********************************************************** */
  // TableProp | TableIndexer
  public static boolean TablePropOrIndexer(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TablePropOrIndexer")) return false;
    if (!nextTokenIs(builder_, "<table prop or indexer>", ID, LBRACK)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TABLE_PROP_OR_INDEXER, "<table prop or indexer>");
    result_ = TableProp(builder_, level_ + 1);
    if (!result_) result_ = TableIndexer(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // '{' Type '}' | '{' PropList? '}'
  public static boolean TableType(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TableType")) return false;
    if (!nextTokenIs(builder_, LCURLY)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = TableType_0(builder_, level_ + 1);
    if (!result_) result_ = TableType_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, TABLE_TYPE, result_);
    return result_;
  }

  // '{' Type '}'
  private static boolean TableType_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TableType_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LCURLY);
    result_ = result_ && Type(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RCURLY);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '{' PropList? '}'
  private static boolean TableType_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TableType_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LCURLY);
    result_ = result_ && TableType_1_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RCURLY);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // PropList?
  private static boolean TableType_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TableType_1_1")) return false;
    PropList(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // SimpleType '?'? (UnionSuffix | IntersectionSuffix)*
  public static boolean Type(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "Type")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TYPE, "<type>");
    result_ = SimpleType(builder_, level_ + 1);
    result_ = result_ && Type_1(builder_, level_ + 1);
    result_ = result_ && Type_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // '?'?
  private static boolean Type_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "Type_1")) return false;
    consumeToken(builder_, QUESTION);
    return true;
  }

  // (UnionSuffix | IntersectionSuffix)*
  private static boolean Type_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "Type_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!Type_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "Type_2", pos_)) break;
    }
    return true;
  }

  // UnionSuffix | IntersectionSuffix
  private static boolean Type_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "Type_2_0")) return false;
    boolean result_;
    result_ = UnionSuffix(builder_, level_ + 1);
    if (!result_) result_ = IntersectionSuffix(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // Type (',' TypeList)? | '...' Type
  public static boolean TypeList(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TypeList")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TYPE_LIST, "<type list>");
    result_ = TypeList_0(builder_, level_ + 1);
    if (!result_) result_ = TypeList_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // Type (',' TypeList)?
  private static boolean TypeList_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TypeList_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = Type(builder_, level_ + 1);
    result_ = result_ && TypeList_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' TypeList)?
  private static boolean TypeList_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TypeList_0_1")) return false;
    TypeList_0_1_0(builder_, level_ + 1);
    return true;
  }

  // ',' TypeList
  private static boolean TypeList_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TypeList_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && TypeList(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '...' Type
  private static boolean TypeList_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TypeList_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ELLIPSIS);
    result_ = result_ && Type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // '(' TypeList? ')'
  public static boolean TypePack(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TypePack")) return false;
    if (!nextTokenIs(builder_, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && TypePack_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, TYPE_PACK, result_);
    return result_;
  }

  // TypeList?
  private static boolean TypePack_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TypePack_1")) return false;
    TypeList(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // (Type | TypePack | VariadicTypePack | GenericTypePack) (',' TypeParams)?
  public static boolean TypeParams(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TypeParams")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _COLLAPSE_, TYPE_PARAMS, "<type params>");
    result_ = TypeParams_0(builder_, level_ + 1);
    result_ = result_ && TypeParams_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // Type | TypePack | VariadicTypePack | GenericTypePack
  private static boolean TypeParams_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TypeParams_0")) return false;
    boolean result_;
    result_ = Type(builder_, level_ + 1);
    if (!result_) result_ = TypePack(builder_, level_ + 1);
    if (!result_) result_ = VariadicTypePack(builder_, level_ + 1);
    if (!result_) result_ = GenericTypePack(builder_, level_ + 1);
    return result_;
  }

  // (',' TypeParams)?
  private static boolean TypeParams_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TypeParams_1")) return false;
    TypeParams_1_0(builder_, level_ + 1);
    return true;
  }

  // ',' TypeParams
  private static boolean TypeParams_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TypeParams_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && TypeParams(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'type'
  public static boolean TypeSoftKeyword(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TypeSoftKeyword")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TYPE_SOFT_KEYWORD, "<type soft keyword>");
    result_ = consumeToken(builder_, "type");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // ('|' SimpleType '?'?)+
  public static boolean UnionSuffix(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "UnionSuffix")) return false;
    if (!nextTokenIs(builder_, UNION)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = UnionSuffix_0(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!UnionSuffix_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "UnionSuffix", pos_)) break;
    }
    exit_section_(builder_, marker_, UNION_SUFFIX, result_);
    return result_;
  }

  // '|' SimpleType '?'?
  private static boolean UnionSuffix_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "UnionSuffix_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, UNION);
    result_ = result_ && SimpleType(builder_, level_ + 1);
    result_ = result_ && UnionSuffix_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '?'?
  private static boolean UnionSuffix_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "UnionSuffix_0_2")) return false;
    consumeToken(builder_, QUESTION);
    return true;
  }

  /* ********************************************************** */
  // '...' Type
  public static boolean VariadicTypePack(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "VariadicTypePack")) return false;
    if (!nextTokenIs(builder_, ELLIPSIS)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ELLIPSIS);
    result_ = result_ && Type(builder_, level_ + 1);
    exit_section_(builder_, marker_, VARIADIC_TYPE_PACK, result_);
    return result_;
  }

  /* ********************************************************** */
  // varList '=' expList | 'local' bindingList ('=' expList)? | var compoundop expression
  public static boolean assignmentStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignmentStatement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ASSIGNMENT_STATEMENT, "<assignment statement>");
    result_ = assignmentStatement_0(builder_, level_ + 1);
    if (!result_) result_ = assignmentStatement_1(builder_, level_ + 1);
    if (!result_) result_ = assignmentStatement_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // varList '=' expList
  private static boolean assignmentStatement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignmentStatement_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = varList(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ASSIGN);
    result_ = result_ && expList(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // 'local' bindingList ('=' expList)?
  private static boolean assignmentStatement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignmentStatement_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LOCAL);
    result_ = result_ && bindingList(builder_, level_ + 1);
    result_ = result_ && assignmentStatement_1_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ('=' expList)?
  private static boolean assignmentStatement_1_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignmentStatement_1_2")) return false;
    assignmentStatement_1_2_0(builder_, level_ + 1);
    return true;
  }

  // '=' expList
  private static boolean assignmentStatement_1_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignmentStatement_1_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ASSIGN);
    result_ = result_ && expList(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // var compoundop expression
  private static boolean assignmentStatement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignmentStatement_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = var(builder_, level_ + 1);
    result_ = result_ && compoundop(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // ID (':' Type)?
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

  // (':' Type)?
  private static boolean binding_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "binding_1")) return false;
    binding_1_0(builder_, level_ + 1);
    return true;
  }

  // ':' Type
  private static boolean binding_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "binding_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COLON);
    result_ = result_ && Type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // binding (',' bindingList)?
  public static boolean bindingList(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "bindingList")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = binding(builder_, level_ + 1);
    result_ = result_ && bindingList_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, BINDING_LIST, result_);
    return result_;
  }

  // (',' bindingList)?
  private static boolean bindingList_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "bindingList_1")) return false;
    bindingList_1_0(builder_, level_ + 1);
    return true;
  }

  // ',' bindingList
  private static boolean bindingList_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "bindingList_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && bindingList(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // '+' | '-' | '*' | '/' | '//' | '^' | '%' | '..' | '<' | '<=' | '>' | '>=' | '==' | '~=' | 'and' | 'or'
  public static boolean binop(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "binop")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, BINOP, "<binop>");
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
  // (statement ';'?)* (lastStatement ';'?)?
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

  // (lastStatement ';'?)?
  private static boolean block_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_1")) return false;
    block_1_0(builder_, level_ + 1);
    return true;
  }

  // lastStatement ';'?
  private static boolean block_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = lastStatement(builder_, level_ + 1);
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
  // 'for' binding '=' expression ',' expression (',' expression)? 'do' block 'end'
  public static boolean classicForStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "classicForStatement")) return false;
    if (!nextTokenIs(builder_, FOR)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, FOR);
    result_ = result_ && binding(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ASSIGN);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, COMMA);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && classicForStatement_6(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, DO);
    result_ = result_ && block(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    exit_section_(builder_, marker_, CLASSIC_FOR_STATEMENT, result_);
    return result_;
  }

  // (',' expression)?
  private static boolean classicForStatement_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "classicForStatement_6")) return false;
    classicForStatement_6_0(builder_, level_ + 1);
    return true;
  }

  // ',' expression
  private static boolean classicForStatement_6_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "classicForStatement_6_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'function' funcBody
  public static boolean closureExpr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "closureExpr")) return false;
    if (!nextTokenIs(builder_, FUNCTION)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, FUNCTION);
    result_ = result_ && funcBody(builder_, level_ + 1);
    exit_section_(builder_, marker_, CLOSURE_EXPR, result_);
    return result_;
  }

  /* ********************************************************** */
  // '+=' | '-=' | '*=' | '/=' | '%=' | '^=' | '..=' | '//='
  public static boolean compoundop(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "compoundop")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMPOUNDOP, "<compoundop>");
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
  // classMethodDefStat | funcDefStat | localFuncDefStat
  static boolean defStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "defStatement")) return false;
    boolean result_;
    result_ = consumeToken(builder_, CLASSMETHODDEFSTAT);
    if (!result_) result_ = funcDefStat(builder_, level_ + 1);
    if (!result_) result_ = localFuncDefStat(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // 'do' block 'end'
  public static boolean doStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "doStatement")) return false;
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
  public static boolean expList(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expList")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, EXP_LIST, "<exp list>");
    result_ = expList_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (expression ',')*
  private static boolean expList_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expList_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!expList_0_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "expList_0", pos_)) break;
    }
    return true;
  }

  // expression ','
  private static boolean expList_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expList_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, COMMA);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // expression
  public static boolean expressionStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expressionStatement")) return false;
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
  // field (fieldsep field)* fieldsep?
  public static boolean fieldlist(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fieldlist")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FIELDLIST, "<fieldlist>");
    result_ = field(builder_, level_ + 1);
    result_ = result_ && fieldlist_1(builder_, level_ + 1);
    result_ = result_ && fieldlist_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (fieldsep field)*
  private static boolean fieldlist_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fieldlist_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!fieldlist_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "fieldlist_1", pos_)) break;
    }
    return true;
  }

  // fieldsep field
  private static boolean fieldlist_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fieldlist_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = fieldsep(builder_, level_ + 1);
    result_ = result_ && field(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // fieldsep?
  private static boolean fieldlist_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fieldlist_2")) return false;
    fieldsep(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // ',' | ';'
  public static boolean fieldsep(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fieldsep")) return false;
    if (!nextTokenIs(builder_, "<fieldsep>", COMMA, SEMI)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FIELDSEP, "<fieldsep>");
    result_ = consumeToken(builder_, COMMA);
    if (!result_) result_ = consumeToken(builder_, SEMI);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // 'for' bindingList 'in' expList 'do' block 'end'
  public static boolean foreachStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "foreachStatement")) return false;
    if (!nextTokenIs(builder_, FOR)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, FOR);
    result_ = result_ && bindingList(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, IN);
    result_ = result_ && expList(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, DO);
    result_ = result_ && block(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    exit_section_(builder_, marker_, FOREACH_STATEMENT, result_);
    return result_;
  }

  /* ********************************************************** */
  // ('<' GenericTypeList '>')? '(' parlist? ')' (':' ReturnType)? block 'end'
  public static boolean funcBody(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcBody")) return false;
    if (!nextTokenIs(builder_, "<func body>", LPAREN, LT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FUNC_BODY, "<func body>");
    result_ = funcBody_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, LPAREN);
    result_ = result_ && funcBody_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    result_ = result_ && funcBody_4(builder_, level_ + 1);
    result_ = result_ && block(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ('<' GenericTypeList '>')?
  private static boolean funcBody_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcBody_0")) return false;
    funcBody_0_0(builder_, level_ + 1);
    return true;
  }

  // '<' GenericTypeList '>'
  private static boolean funcBody_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcBody_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LT);
    result_ = result_ && GenericTypeList(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, GT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // parlist?
  private static boolean funcBody_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcBody_2")) return false;
    parlist(builder_, level_ + 1);
    return true;
  }

  // (':' ReturnType)?
  private static boolean funcBody_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcBody_4")) return false;
    funcBody_4_0(builder_, level_ + 1);
    return true;
  }

  // ':' ReturnType
  private static boolean funcBody_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcBody_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COLON);
    result_ = result_ && ReturnType(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'function' funcname funcBody
  public static boolean funcDefStat(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcDefStat")) return false;
    if (!nextTokenIs(builder_, FUNCTION)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, FUNCTION);
    result_ = result_ && funcname(builder_, level_ + 1);
    result_ = result_ && funcBody(builder_, level_ + 1);
    exit_section_(builder_, marker_, FUNC_DEF_STAT, result_);
    return result_;
  }

  /* ********************************************************** */
  // '(' expList? ')' | tableconstructor | STRING
  public static boolean funcargs(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcargs")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FUNCARGS, "<funcargs>");
    result_ = funcargs_0(builder_, level_ + 1);
    if (!result_) result_ = tableconstructor(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, STRING);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // '(' expList? ')'
  private static boolean funcargs_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcargs_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && funcargs_0_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // expList?
  private static boolean funcargs_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcargs_0_1")) return false;
    expList(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // ID ('.' ID)* (':' ID)?
  public static boolean funcname(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcname")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ID);
    result_ = result_ && funcname_1(builder_, level_ + 1);
    result_ = result_ && funcname_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, FUNCNAME, result_);
    return result_;
  }

  // ('.' ID)*
  private static boolean funcname_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcname_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!funcname_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "funcname_1", pos_)) break;
    }
    return true;
  }

  // '.' ID
  private static boolean funcname_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcname_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, DOT, ID);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (':' ID)?
  private static boolean funcname_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcname_2")) return false;
    funcname_2_0(builder_, level_ + 1);
    return true;
  }

  // ':' ID
  private static boolean funcname_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "funcname_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COLON, ID);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // prefixExp postfixExp
  public static boolean functioncall(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "functioncall")) return false;
    if (!nextTokenIs(builder_, "<functioncall>", ID, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FUNCTIONCALL, "<functioncall>");
    result_ = prefixExp(builder_, level_ + 1);
    result_ = result_ && postfixExp(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // 'if' expression 'then' block ('elseif' expression 'then' block)* ('else' block)? 'end'
  public static boolean ifStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifStatement")) return false;
    if (!nextTokenIs(builder_, IF)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IF);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, THEN);
    result_ = result_ && block(builder_, level_ + 1);
    result_ = result_ && ifStatement_4(builder_, level_ + 1);
    result_ = result_ && ifStatement_5(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    exit_section_(builder_, marker_, IF_STATEMENT, result_);
    return result_;
  }

  // ('elseif' expression 'then' block)*
  private static boolean ifStatement_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifStatement_4")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!ifStatement_4_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "ifStatement_4", pos_)) break;
    }
    return true;
  }

  // 'elseif' expression 'then' block
  private static boolean ifStatement_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifStatement_4_0")) return false;
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
  private static boolean ifStatement_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifStatement_5")) return false;
    ifStatement_5_0(builder_, level_ + 1);
    return true;
  }

  // 'else' block
  private static boolean ifStatement_5_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifStatement_5_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ELSE);
    result_ = result_ && block(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'if' expression 'then' expression ('elseif' expression 'then' expression)* 'else' expression
  public static boolean ifelseexp(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifelseexp")) return false;
    if (!nextTokenIs(builder_, IF)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IF);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, THEN);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && ifelseexp_4(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ELSE);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    exit_section_(builder_, marker_, IFELSEEXP, result_);
    return result_;
  }

  // ('elseif' expression 'then' expression)*
  private static boolean ifelseexp_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifelseexp_4")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!ifelseexp_4_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "ifelseexp_4", pos_)) break;
    }
    return true;
  }

  // 'elseif' expression 'then' expression
  private static boolean ifelseexp_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ifelseexp_4_0")) return false;
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
  // 'return' expList? | 'break' | ContinueSoftKeyword
  public static boolean lastStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "lastStatement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, LAST_STATEMENT, "<last statement>");
    result_ = lastStatement_0(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, BREAK);
    if (!result_) result_ = ContinueSoftKeyword(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // 'return' expList?
  private static boolean lastStatement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "lastStatement_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, RETURN);
    result_ = result_ && lastStatement_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // expList?
  private static boolean lastStatement_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "lastStatement_0_1")) return false;
    expList(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // 'local' 'function' funcname funcBody
  public static boolean localFuncDefStat(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "localFuncDefStat")) return false;
    if (!nextTokenIs(builder_, LOCAL)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, LOCAL, FUNCTION);
    result_ = result_ && funcname(builder_, level_ + 1);
    result_ = result_ && funcBody(builder_, level_ + 1);
    exit_section_(builder_, marker_, LOCAL_FUNC_DEF_STAT, result_);
    return result_;
  }

  /* ********************************************************** */
  // shebang_line? block
  static boolean luaFile(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "luaFile")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = luaFile_0(builder_, level_ + 1);
    result_ = result_ && block(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // shebang_line?
  private static boolean luaFile_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "luaFile_0")) return false;
    shebang_line(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // bindingList (',' '...')? | '...' (':' (Type | GenericTypePack))?
  public static boolean parlist(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "parlist")) return false;
    if (!nextTokenIs(builder_, "<parlist>", ELLIPSIS, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PARLIST, "<parlist>");
    result_ = parlist_0(builder_, level_ + 1);
    if (!result_) result_ = parlist_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // bindingList (',' '...')?
  private static boolean parlist_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "parlist_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = bindingList(builder_, level_ + 1);
    result_ = result_ && parlist_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' '...')?
  private static boolean parlist_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "parlist_0_1")) return false;
    parlist_0_1_0(builder_, level_ + 1);
    return true;
  }

  // ',' '...'
  private static boolean parlist_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "parlist_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COMMA, ELLIPSIS);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '...' (':' (Type | GenericTypePack))?
  private static boolean parlist_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "parlist_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ELLIPSIS);
    result_ = result_ && parlist_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (':' (Type | GenericTypePack))?
  private static boolean parlist_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "parlist_1_1")) return false;
    parlist_1_1_0(builder_, level_ + 1);
    return true;
  }

  // ':' (Type | GenericTypePack)
  private static boolean parlist_1_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "parlist_1_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COLON);
    result_ = result_ && parlist_1_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // Type | GenericTypePack
  private static boolean parlist_1_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "parlist_1_1_0_1")) return false;
    boolean result_;
    result_ = Type(builder_, level_ + 1);
    if (!result_) result_ = GenericTypePack(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // funcargs | ':' ID funcargs | '[' expression ']' | '.' ID
  public static boolean postfixExp(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "postfixExp")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, POSTFIX_EXP, "<postfix exp>");
    result_ = funcargs(builder_, level_ + 1);
    if (!result_) result_ = postfixExp_1(builder_, level_ + 1);
    if (!result_) result_ = postfixExp_2(builder_, level_ + 1);
    if (!result_) result_ = parseTokens(builder_, 0, DOT, ID);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ':' ID funcargs
  private static boolean postfixExp_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "postfixExp_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COLON, ID);
    result_ = result_ && funcargs(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '[' expression ']'
  private static boolean postfixExp_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "postfixExp_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACK);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, RBRACK);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // (var | '(' expression ')') postfixExp*
  public static boolean prefixExp(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prefixExp")) return false;
    if (!nextTokenIs(builder_, "<prefix exp>", ID, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PREFIX_EXP, "<prefix exp>");
    result_ = prefixExp_0(builder_, level_ + 1);
    result_ = result_ && prefixExp_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // var | '(' expression ')'
  private static boolean prefixExp_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prefixExp_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = var(builder_, level_ + 1);
    if (!result_) result_ = prefixExp_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // '(' expression ')'
  private static boolean prefixExp_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prefixExp_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // postfixExp*
  private static boolean prefixExp_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prefixExp_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!postfixExp(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "prefixExp_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // 'repeat' block 'until' expression
  public static boolean repeatStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "repeatStatement")) return false;
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
  // ID | '(' expression ')'
  public static boolean simpleVar(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simpleVar")) return false;
    if (!nextTokenIs(builder_, "<simple var>", ID, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SIMPLE_VAR, "<simple var>");
    result_ = consumeToken(builder_, ID);
    if (!result_) result_ = simpleVar_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // '(' expression ')'
  private static boolean simpleVar_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simpleVar_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // assignmentStatement
  //      | typeDeclarationStatement
  //      | functioncall
  //      | doStatement
  //      | whileStatement
  //      | repeatStatement
  //      | ifStatement
  //      // Maybe not the best fix, but expressionStatement is moved below ifStatement on purpose
  //      // expressionStatement includes ifExpression but most of the time we want ifStatement to be matched first, e.g. to fix nested conditions
  //      | expressionStatement
  //      | classicForStatement
  //      | foreachStatement
  //      | defStatement
  public static boolean statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "statement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, STATEMENT, "<statement>");
    result_ = assignmentStatement(builder_, level_ + 1);
    if (!result_) result_ = typeDeclarationStatement(builder_, level_ + 1);
    if (!result_) result_ = functioncall(builder_, level_ + 1);
    if (!result_) result_ = doStatement(builder_, level_ + 1);
    if (!result_) result_ = whileStatement(builder_, level_ + 1);
    if (!result_) result_ = repeatStatement(builder_, level_ + 1);
    if (!result_) result_ = ifStatement(builder_, level_ + 1);
    if (!result_) result_ = expressionStatement(builder_, level_ + 1);
    if (!result_) result_ = classicForStatement(builder_, level_ + 1);
    if (!result_) result_ = foreachStatement(builder_, level_ + 1);
    if (!result_) result_ = defStatement(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // '{' fieldlist? '}'
  public static boolean tableconstructor(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "tableconstructor")) return false;
    if (!nextTokenIs(builder_, LCURLY)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LCURLY);
    result_ = result_ && tableconstructor_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RCURLY);
    exit_section_(builder_, marker_, TABLECONSTRUCTOR, result_);
    return result_;
  }

  // fieldlist?
  private static boolean tableconstructor_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "tableconstructor_1")) return false;
    fieldlist(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // TEMPLATE_STRING_SQUOTE (STRING? '{' expression '}' STRING?)* TEMPLATE_STRING_EQUOTE
  public static boolean templateString(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "templateString")) return false;
    if (!nextTokenIs(builder_, TEMPLATE_STRING_SQUOTE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, TEMPLATE_STRING_SQUOTE);
    result_ = result_ && templateString_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, TEMPLATE_STRING_EQUOTE);
    exit_section_(builder_, marker_, TEMPLATE_STRING, result_);
    return result_;
  }

  // (STRING? '{' expression '}' STRING?)*
  private static boolean templateString_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "templateString_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!templateString_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "templateString_1", pos_)) break;
    }
    return true;
  }

  // STRING? '{' expression '}' STRING?
  private static boolean templateString_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "templateString_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = templateString_1_0_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, LCURLY);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    result_ = result_ && consumeToken(builder_, RCURLY);
    result_ = result_ && templateString_1_0_4(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // STRING?
  private static boolean templateString_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "templateString_1_0_0")) return false;
    consumeToken(builder_, STRING);
    return true;
  }

  // STRING?
  private static boolean templateString_1_0_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "templateString_1_0_4")) return false;
    consumeToken(builder_, STRING);
    return true;
  }

  /* ********************************************************** */
  // ExportSoftKeyword ? TypeSoftKeyword ID ('<' GenericTypeListWithDefaults '>')? '=' Type
  public static boolean typeDeclarationStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "typeDeclarationStatement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TYPE_DECLARATION_STATEMENT, "<type declaration statement>");
    result_ = typeDeclarationStatement_0(builder_, level_ + 1);
    result_ = result_ && TypeSoftKeyword(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ID);
    result_ = result_ && typeDeclarationStatement_3(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ASSIGN);
    result_ = result_ && Type(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ExportSoftKeyword ?
  private static boolean typeDeclarationStatement_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "typeDeclarationStatement_0")) return false;
    ExportSoftKeyword(builder_, level_ + 1);
    return true;
  }

  // ('<' GenericTypeListWithDefaults '>')?
  private static boolean typeDeclarationStatement_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "typeDeclarationStatement_3")) return false;
    typeDeclarationStatement_3_0(builder_, level_ + 1);
    return true;
  }

  // '<' GenericTypeListWithDefaults '>'
  private static boolean typeDeclarationStatement_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "typeDeclarationStatement_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LT);
    result_ = result_ && GenericTypeListWithDefaults(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, GT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // '-' | 'not' | '#'
  public static boolean unop(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "unop")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, UNOP, "<unop>");
    result_ = consumeToken(builder_, MINUS);
    if (!result_) result_ = consumeToken(builder_, NOT);
    if (!result_) result_ = consumeToken(builder_, GETN);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // simpleVar postfixExp*
  public static boolean var(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "var")) return false;
    if (!nextTokenIs(builder_, "<var>", ID, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, VAR, "<var>");
    result_ = simpleVar(builder_, level_ + 1);
    result_ = result_ && var_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // postfixExp*
  private static boolean var_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "var_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!postfixExp(builder_, level_ + 1)) break;
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
  // 'while' expression 'do' block 'end'
  public static boolean whileStatement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "whileStatement")) return false;
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
  // 0: ATOM(simpleExp)
  // 1: POSTFIX(asExp)
  // 2: PREFIX(unaryExp)
  // 3: BINARY(binaryExp)
  public static boolean expression(PsiBuilder builder_, int level_, int priority_) {
    if (!recursion_guard_(builder_, level_, "expression")) return false;
    addVariant(builder_, "<expression>");
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, "<expression>");
    result_ = simpleExp(builder_, level_ + 1);
    if (!result_) result_ = unaryExp(builder_, level_ + 1);
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
      if (priority_ < 1 && leftMarkerIs(builder_, SIMPLE_EXP) && asExp_0(builder_, level_ + 1)) {
        result_ = true;
        exit_section_(builder_, level_, marker_, AS_EXP, result_, true, null);
      }
      else if (priority_ < 3 && binop(builder_, level_ + 1)) {
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

  // NUMBER | STRING | 'nil' | 'true' | 'false' | '...' | tableconstructor | closureExpr | prefixExp | ifelseexp | templateString
  public static boolean simpleExp(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simpleExp")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SIMPLE_EXP, "<simple exp>");
    result_ = consumeTokenSmart(builder_, NUMBER);
    if (!result_) result_ = consumeTokenSmart(builder_, STRING);
    if (!result_) result_ = consumeTokenSmart(builder_, NIL);
    if (!result_) result_ = consumeTokenSmart(builder_, TRUE);
    if (!result_) result_ = consumeTokenSmart(builder_, FALSE);
    if (!result_) result_ = consumeTokenSmart(builder_, ELLIPSIS);
    if (!result_) result_ = tableconstructor(builder_, level_ + 1);
    if (!result_) result_ = closureExpr(builder_, level_ + 1);
    if (!result_) result_ = prefixExp(builder_, level_ + 1);
    if (!result_) result_ = ifelseexp(builder_, level_ + 1);
    if (!result_) result_ = templateString(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // '::' Type
  private static boolean asExp_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "asExp_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokenSmart(builder_, DOUBLE_COLON);
    result_ = result_ && Type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  public static boolean unaryExp(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "unaryExp")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = unop(builder_, level_ + 1);
    pinned_ = result_;
    result_ = pinned_ && expression(builder_, level_, 2);
    exit_section_(builder_, level_, marker_, UNARY_EXP, result_, pinned_, null);
    return result_ || pinned_;
  }

}
