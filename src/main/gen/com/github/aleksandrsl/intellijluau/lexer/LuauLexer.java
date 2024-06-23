// Generated by JFlex 1.9.2 http://jflex.de/  (tweaked for IntelliJ platform)
// source: src/main/grammar/Luau.flex

package com.github.aleksandrsl.intellijluau.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static com.github.aleksandrsl.intellijluau.psi.LuauTypes.*;
import com.intellij.psi.TokenType;


class LuauLexer implements FlexLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int xSHEBANG = 2;
  public static final int xDOUBLE_QUOTED_STRING = 4;
  public static final int xSINGLE_QUOTED_STRING = 6;
  public static final int xBLOCK_STRING = 8;
  public static final int xCOMMENT = 10;
  public static final int xBLOCK_COMMENT = 12;
  public static final int xTEMPLATE_STRING = 14;
  public static final int xTEMPLATE_STRING_EXPRESSION = 16;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5,  5,  4,  4,  6,  6, 
     7, 7
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\25\u0100\1\u0200\11\u0100\1\u0300\17\u0100\1\u0400\247\u0100"+
    "\10\u0500\40\u0100\u1000\u0600";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\1\3\1\4\1\5\22\0\1\1"+
    "\1\6\1\7\1\10\1\0\1\11\1\12\1\13\1\14"+
    "\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24"+
    "\1\25\10\26\1\27\1\30\1\31\1\32\1\33\1\34"+
    "\1\0\1\35\1\36\2\35\1\37\1\35\5\40\1\41"+
    "\3\40\1\42\4\40\1\43\2\40\1\44\2\40\1\45"+
    "\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55"+
    "\1\56\1\57\1\60\1\61\1\62\1\63\1\40\1\64"+
    "\1\65\1\40\1\66\1\67\1\70\1\40\1\71\1\72"+
    "\1\73\1\74\1\40\1\75\1\44\1\40\1\76\1\77"+
    "\1\100\1\101\1\102\6\0\1\3\32\0\1\103\136\0"+
    "\u0181\40\1\104\177\40\13\104\35\40\2\105\5\40\1\104"+
    "\57\40\1\104\240\40\1\104\377\40\u0100\106\u0100\0";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[1792];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\2\2\2\0\1\3\1\0\1\4\1\5"+
    "\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15"+
    "\1\16\1\17\1\20\1\21\1\22\2\23\1\24\1\25"+
    "\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35"+
    "\1\36\15\32\1\37\1\40\1\41\1\4\1\2\1\42"+
    "\1\43\1\0\1\3\1\4\1\44\1\45\1\7\1\20"+
    "\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55"+
    "\1\23\1\56\1\57\1\23\1\0\1\23\3\0\1\60"+
    "\1\61\1\62\1\63\1\64\2\32\1\65\5\32\1\66"+
    "\1\67\3\32\1\70\5\32\1\71\1\42\1\0\1\43"+
    "\1\0\1\72\1\0\1\3\4\0\1\73\1\74\1\75"+
    "\2\23\1\0\2\23\1\76\2\32\1\77\1\32\1\100"+
    "\2\32\1\101\1\102\6\32\1\42\1\43\1\72\1\103"+
    "\5\0\1\23\1\0\1\32\1\104\5\32\1\105\1\106"+
    "\2\32\4\0\1\107\1\110\1\23\1\111\1\32\1\112"+
    "\1\32\1\113\2\32\1\114\1\115\3\0\1\23\1\116"+
    "\1\32\1\117\1\120\3\0\1\32\1\0\1\121\2\0";

  private static int [] zzUnpackAction() {
    int [] result = new int[194];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\107\0\216\0\325\0\u011c\0\u0163\0\u01aa\0\u01f1"+
    "\0\u011c\0\u0238\0\u011c\0\u027f\0\u02c6\0\u011c\0\u011c\0\u011c"+
    "\0\u011c\0\u030d\0\u0354\0\u011c\0\u039b\0\u03e2\0\u0429\0\u0470"+
    "\0\u04b7\0\u04fe\0\u011c\0\u0545\0\u058c\0\u05d3\0\u011c\0\u061a"+
    "\0\u011c\0\u011c\0\u0661\0\u011c\0\u06a8\0\u06ef\0\u0736\0\u077d"+
    "\0\u07c4\0\u080b\0\u0852\0\u0899\0\u08e0\0\u0927\0\u096e\0\u09b5"+
    "\0\u09fc\0\u011c\0\u011c\0\u011c\0\u0a43\0\u0a8a\0\u0ad1\0\u0b18"+
    "\0\u0b5f\0\u0ba6\0\u0bed\0\u011c\0\u011c\0\u011c\0\u0c34\0\u011c"+
    "\0\u011c\0\u011c\0\u011c\0\u0c7b\0\u011c\0\u011c\0\u0cc2\0\u0d09"+
    "\0\u0d50\0\u011c\0\u0d97\0\u0dde\0\u0e25\0\u0e6c\0\u0eb3\0\u0efa"+
    "\0\u011c\0\u011c\0\u011c\0\u011c\0\u011c\0\u0f41\0\u0f88\0\u061a"+
    "\0\u0fcf\0\u1016\0\u105d\0\u10a4\0\u10eb\0\u061a\0\u061a\0\u1132"+
    "\0\u1179\0\u11c0\0\u061a\0\u1207\0\u124e\0\u1295\0\u12dc\0\u1323"+
    "\0\u011c\0\u011c\0\u136a\0\u011c\0\u13b1\0\u13f8\0\u0bed\0\u143f"+
    "\0\u1486\0\u14cd\0\u1514\0\u155b\0\u011c\0\u011c\0\u011c\0\u15a2"+
    "\0\u15e9\0\u1630\0\u011c\0\u1677\0\u061a\0\u16be\0\u1705\0\u061a"+
    "\0\u174c\0\u061a\0\u1793\0\u17da\0\u061a\0\u061a\0\u1821\0\u1868"+
    "\0\u18af\0\u18f6\0\u193d\0\u1984\0\u19cb\0\u1a12\0\u1a59\0\u1aa0"+
    "\0\u1ae7\0\u1b2e\0\u1b75\0\u1bbc\0\u1c03\0\u1c4a\0\u1c91\0\u1cd8"+
    "\0\u1d1f\0\u1d66\0\u1dad\0\u1df4\0\u1e3b\0\u1e82\0\u061a\0\u061a"+
    "\0\u1ec9\0\u1f10\0\u1f57\0\u1f9e\0\u1fe5\0\u202c\0\u2073\0\u20ba"+
    "\0\u2101\0\u061a\0\u2148\0\u061a\0\u218f\0\u061a\0\u21d6\0\u221d"+
    "\0\u061a\0\u061a\0\u2264\0\u22ab\0\u22f2\0\u2339\0\u061a\0\u2380"+
    "\0\u061a\0\u061a\0\u23c7\0\u240e\0\u2455\0\u249c\0\u24e3\0\u061a"+
    "\0\u252a\0\u2571";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[194];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length() - 1;
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpacktrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\11\2\12\1\11\2\12\1\11\1\13\1\14\1\15"+
    "\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25"+
    "\1\26\1\27\1\30\2\31\1\32\1\33\1\34\1\35"+
    "\1\36\1\37\10\40\1\41\1\11\1\42\1\43\1\40"+
    "\1\44\1\45\1\46\1\40\1\47\1\50\1\51\2\40"+
    "\1\52\1\40\1\53\1\54\1\55\1\40\1\56\1\40"+
    "\1\57\1\60\1\61\1\40\1\62\1\63\1\64\1\65"+
    "\1\11\3\40\2\2\1\0\2\2\1\0\101\2\2\11"+
    "\1\66\2\11\1\66\1\11\1\67\101\11\1\66\2\11"+
    "\1\66\5\11\1\70\73\11\130\0\1\71\65\0\2\72"+
    "\1\11\2\72\1\11\40\72\1\73\3\72\1\74\24\72"+
    "\1\75\7\72\1\11\2\12\1\11\2\12\1\11\1\13"+
    "\1\76\1\15\1\16\1\17\1\20\1\21\1\22\1\23"+
    "\1\24\1\77\1\26\1\27\1\30\2\31\1\32\1\33"+
    "\1\34\1\35\1\36\1\37\10\40\1\41\1\11\1\42"+
    "\1\43\1\40\1\44\1\45\1\46\1\40\1\47\1\50"+
    "\1\51\2\40\1\52\1\40\1\53\1\54\1\55\1\40"+
    "\1\56\1\40\1\57\1\60\1\61\1\40\1\62\1\63"+
    "\1\64\1\65\1\11\3\40\1\0\2\12\1\0\2\12"+
    "\107\0\1\100\132\0\1\101\106\0\1\102\106\0\1\103"+
    "\75\0\1\104\10\0\1\105\1\106\75\0\1\107\1\0"+
    "\3\110\103\0\1\111\6\0\1\112\76\0\1\113\1\0"+
    "\3\31\7\0\1\114\1\115\1\0\1\116\1\0\1\117"+
    "\1\120\4\0\1\31\2\0\1\114\2\0\1\115\51\0"+
    "\1\113\1\0\3\31\10\0\1\115\1\0\1\116\1\0"+
    "\1\117\5\0\1\31\5\0\1\115\56\0\1\121\111\0"+
    "\1\122\106\0\1\123\106\0\1\124\100\0\3\40\6\0"+
    "\10\40\4\0\1\40\1\0\24\40\5\0\3\40\32\0"+
    "\1\125\100\0\3\40\6\0\10\40\4\0\1\40\1\0"+
    "\13\40\1\126\10\40\5\0\3\40\24\0\3\40\6\0"+
    "\10\40\4\0\1\40\1\0\16\40\1\127\5\40\5\0"+
    "\3\40\24\0\3\40\6\0\10\40\4\0\1\40\1\0"+
    "\14\40\1\130\7\40\5\0\3\40\24\0\3\40\6\0"+
    "\10\40\4\0\1\40\1\0\12\40\1\131\1\132\10\40"+
    "\5\0\3\40\24\0\3\40\6\0\10\40\4\0\1\40"+
    "\1\0\1\133\13\40\1\134\4\40\1\135\2\40\5\0"+
    "\3\40\24\0\3\40\6\0\10\40\4\0\1\40\1\0"+
    "\5\40\1\136\5\40\1\137\10\40\5\0\3\40\24\0"+
    "\3\40\6\0\10\40\4\0\1\40\1\0\14\40\1\140"+
    "\7\40\5\0\3\40\24\0\3\40\6\0\10\40\4\0"+
    "\1\40\1\0\10\40\1\141\3\40\1\142\7\40\5\0"+
    "\3\40\24\0\3\40\6\0\10\40\4\0\1\40\1\0"+
    "\16\40\1\143\5\40\5\0\3\40\24\0\3\40\6\0"+
    "\10\40\4\0\1\40\1\0\4\40\1\144\17\40\5\0"+
    "\3\40\24\0\3\40\6\0\10\40\4\0\1\40\1\0"+
    "\7\40\1\145\6\40\1\146\5\40\5\0\3\40\24\0"+
    "\3\40\6\0\10\40\4\0\1\40\1\0\13\40\1\147"+
    "\10\40\5\0\3\40\24\0\3\40\6\0\10\40\4\0"+
    "\1\40\1\0\7\40\1\150\14\40\5\0\3\40\32\0"+
    "\1\151\56\0\1\66\2\0\1\66\101\0\2\67\1\0"+
    "\2\67\1\0\1\67\1\152\36\67\1\153\40\67\2\70"+
    "\1\0\2\70\1\0\5\70\1\154\32\70\1\155\40\70"+
    "\21\0\1\156\65\0\2\72\1\0\2\72\1\0\40\72"+
    "\1\157\3\72\1\0\24\72\1\0\12\72\2\0\71\72"+
    "\1\160\6\72\34\0\1\105\1\106\132\0\1\161\11\0"+
    "\1\162\5\0\1\163\1\0\1\164\27\0\1\165\7\0"+
    "\1\166\100\0\3\110\22\0\1\110\67\0\1\167\100\0"+
    "\3\170\104\0\2\171\100\0\1\172\1\0\1\172\2\0"+
    "\3\110\121\0\1\173\106\0\1\116\71\0\3\174\6\0"+
    "\3\174\13\0\6\174\52\0\3\40\6\0\10\40\4\0"+
    "\1\40\1\0\3\40\1\175\20\40\5\0\3\40\24\0"+
    "\3\40\6\0\10\40\4\0\1\40\1\0\4\40\1\176"+
    "\17\40\5\0\3\40\24\0\3\40\6\0\10\40\4\0"+
    "\1\40\1\0\17\40\1\177\4\40\5\0\3\40\24\0"+
    "\3\40\6\0\10\40\4\0\1\40\1\0\3\40\1\200"+
    "\20\40\5\0\3\40\24\0\3\40\6\0\10\40\4\0"+
    "\1\40\1\0\12\40\1\201\11\40\5\0\3\40\24\0"+
    "\3\40\6\0\10\40\4\0\1\40\1\0\16\40\1\202"+
    "\5\40\5\0\3\40\24\0\3\40\6\0\10\40\4\0"+
    "\1\40\1\0\13\40\1\203\10\40\5\0\3\40\24\0"+
    "\3\40\6\0\10\40\4\0\1\40\1\0\2\40\1\204"+
    "\21\40\5\0\3\40\24\0\3\40\6\0\10\40\4\0"+
    "\1\40\1\0\12\40\1\205\11\40\5\0\3\40\24\0"+
    "\3\40\6\0\10\40\4\0\1\40\1\0\20\40\1\206"+
    "\3\40\5\0\3\40\24\0\3\40\6\0\10\40\4\0"+
    "\1\40\1\0\15\40\1\207\2\40\1\210\3\40\5\0"+
    "\3\40\24\0\3\40\6\0\10\40\4\0\1\40\1\0"+
    "\4\40\1\211\17\40\5\0\3\40\24\0\3\40\6\0"+
    "\10\40\4\0\1\40\1\0\21\40\1\212\2\40\5\0"+
    "\3\40\24\0\3\40\6\0\10\40\4\0\1\40\1\0"+
    "\20\40\1\213\3\40\5\0\3\40\24\0\3\40\6\0"+
    "\10\40\4\0\1\40\1\0\10\40\1\214\13\40\5\0"+
    "\3\40\3\67\2\0\71\67\1\215\6\67\2\0\3\70"+
    "\2\0\71\70\1\216\6\70\2\0\2\217\1\0\2\217"+
    "\1\0\13\217\1\220\65\217\1\72\5\160\40\72\1\157"+
    "\3\72\1\0\24\72\1\0\3\72\3\160\1\72\66\0"+
    "\1\221\77\0\1\222\126\0\1\223\110\0\1\224\31\0"+
    "\3\170\10\0\1\115\11\0\1\170\5\0\1\115\53\0"+
    "\2\171\23\0\1\171\61\0\3\110\102\0\1\225\1\0"+
    "\3\174\6\0\2\174\1\226\1\0\1\116\1\227\1\117"+
    "\5\0\1\174\1\0\4\174\1\226\1\174\7\0\1\227"+
    "\42\0\3\40\6\0\10\40\4\0\1\40\1\0\1\230"+
    "\23\40\5\0\3\40\24\0\3\40\6\0\10\40\4\0"+
    "\1\40\1\0\4\40\1\231\17\40\5\0\3\40\24\0"+
    "\3\40\6\0\10\40\4\0\1\40\1\0\17\40\1\232"+
    "\4\40\5\0\3\40\24\0\3\40\6\0\10\40\4\0"+
    "\1\40\1\0\2\40\1\233\21\40\5\0\3\40\24\0"+
    "\3\40\6\0\10\40\4\0\1\40\1\0\1\234\23\40"+
    "\5\0\3\40\24\0\3\40\6\0\10\40\4\0\1\40"+
    "\1\0\4\40\1\235\17\40\5\0\3\40\24\0\3\40"+
    "\6\0\10\40\4\0\1\40\1\0\21\40\1\236\2\40"+
    "\5\0\3\40\24\0\3\40\6\0\10\40\4\0\1\40"+
    "\1\0\13\40\1\237\10\40\5\0\3\40\24\0\3\40"+
    "\6\0\10\40\4\0\1\40\1\0\4\40\1\240\17\40"+
    "\5\0\3\40\24\0\3\40\6\0\10\40\4\0\1\40"+
    "\1\0\10\40\1\241\13\40\5\0\3\40\24\0\3\40"+
    "\6\0\10\40\4\0\1\40\1\0\12\40\1\242\11\40"+
    "\5\0\3\40\1\67\5\215\1\67\1\152\36\67\1\153"+
    "\34\67\3\215\1\67\1\70\5\216\5\70\1\154\32\70"+
    "\1\155\34\70\3\216\1\70\2\217\1\0\2\217\1\0"+
    "\101\217\2\220\1\243\2\220\1\244\101\220\56\0\1\245"+
    "\111\0\1\246\124\0\1\247\110\0\1\250\31\0\3\251"+
    "\6\0\3\251\13\0\6\251\45\0\1\172\1\0\1\172"+
    "\1\225\1\0\3\174\6\0\2\174\1\226\1\0\1\116"+
    "\1\227\1\117\5\0\1\174\1\0\4\174\1\226\1\174"+
    "\7\0\1\227\35\0\1\172\1\0\1\172\111\0\3\40"+
    "\6\0\10\40\4\0\1\40\1\0\11\40\1\252\12\40"+
    "\5\0\3\40\24\0\3\40\6\0\10\40\4\0\1\40"+
    "\1\0\10\40\1\253\13\40\5\0\3\40\24\0\3\40"+
    "\6\0\10\40\4\0\1\40\1\0\4\40\1\254\17\40"+
    "\5\0\3\40\24\0\3\40\6\0\10\40\4\0\1\40"+
    "\1\0\20\40\1\255\3\40\5\0\3\40\24\0\3\40"+
    "\6\0\10\40\4\0\1\40\1\0\12\40\1\256\11\40"+
    "\5\0\3\40\24\0\3\40\6\0\10\40\4\0\1\40"+
    "\1\0\1\257\23\40\5\0\3\40\24\0\3\40\6\0"+
    "\10\40\4\0\1\40\1\0\16\40\1\260\5\40\5\0"+
    "\3\40\24\0\3\40\6\0\10\40\4\0\1\40\1\0"+
    "\12\40\1\261\11\40\5\0\3\40\24\0\3\40\6\0"+
    "\10\40\4\0\1\40\1\0\4\40\1\262\17\40\5\0"+
    "\3\40\1\0\1\243\2\0\1\243\14\0\1\263\67\0"+
    "\1\243\175\0\1\264\100\0\1\265\23\0\2\247\1\0"+
    "\2\247\1\0\101\247\2\250\1\0\2\250\1\0\101\250"+
    "\24\0\3\251\6\0\2\251\1\266\2\0\1\227\6\0"+
    "\1\251\1\0\4\251\1\266\1\251\7\0\1\227\42\0"+
    "\3\40\6\0\10\40\4\0\1\40\1\0\5\40\1\267"+
    "\16\40\5\0\3\40\24\0\3\40\6\0\10\40\4\0"+
    "\1\40\1\0\10\40\1\270\13\40\5\0\3\40\24\0"+
    "\3\40\6\0\10\40\4\0\1\40\1\0\20\40\1\271"+
    "\3\40\5\0\3\40\24\0\3\40\6\0\10\40\4\0"+
    "\1\40\1\0\13\40\1\272\10\40\5\0\3\40\21\0"+
    "\1\273\144\0\1\274\116\0\1\275\36\0\1\172\1\0"+
    "\1\172\2\0\3\251\6\0\2\251\1\266\2\0\1\227"+
    "\6\0\1\251\1\0\4\251\1\266\1\251\7\0\1\227"+
    "\42\0\3\40\6\0\10\40\4\0\1\40\1\0\14\40"+
    "\1\276\7\40\5\0\3\40\21\0\1\220\146\0\1\277"+
    "\113\0\1\247\44\0\3\40\6\0\10\40\4\0\1\40"+
    "\1\0\13\40\1\300\10\40\5\0\3\40\63\0\1\301"+
    "\112\0\1\302\105\0\1\250\20\0";

  private static int [] zzUnpacktrans() {
    int [] result = new int[9656];
    int offset = 0;
    offset = zzUnpacktrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpacktrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String[] ZZ_ERROR_MSG = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\3\1\1\10\1\0\1\1\1\0\1\11\1\1"+
    "\1\11\2\1\4\11\2\1\1\11\6\1\1\11\3\1"+
    "\1\11\1\1\2\11\1\1\1\11\15\1\3\11\4\1"+
    "\1\0\2\1\3\11\1\1\4\11\1\1\2\11\3\1"+
    "\1\11\1\1\1\0\1\1\3\0\5\11\23\1\2\11"+
    "\1\0\1\11\1\0\1\1\1\0\1\1\4\0\3\11"+
    "\2\1\1\0\1\11\25\1\5\0\1\1\1\0\13\1"+
    "\4\0\14\1\3\0\5\1\3\0\1\1\1\0\1\1"+
    "\2\0";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[194];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** Number of newlines encountered up to the start of the matched text. */
  @SuppressWarnings("unused")
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  @SuppressWarnings("unused")
  protected int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  @SuppressWarnings("unused")
  private boolean zzEOFDone;

  /* user code: */
  public LuauLexer() {
    this((java.io.Reader)null);
  }
  private int templateStack = 0;
  private void pushState(int state) {
      yybegin(state);
      if (state == xTEMPLATE_STRING) {
        templateStack += 1;
      }
  }
  private void popState() {
    if (yystate() == xTEMPLATE_STRING) {
        templateStack -= 1;
    }
    yybegin(templateStack > 0 ? xTEMPLATE_STRING_EXPRESSION : YYINITIAL);
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


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  LuauLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** Returns the maximum size of the scanner buffer, which limits the size of tokens. */
  private int zzMaxBufferLen() {
    return Integer.MAX_VALUE;
  }

  /**  Whether the scanner buffer can grow to accommodate a larger token. */
  private boolean zzCanGrow() {
    return true;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  public final int getTokenStart() {
    return zzStartRead;
  }

  public final int getTokenEnd() {
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end, int initialState) {
    zzBuffer = buffer;
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      {@code false}, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position {@code pos} from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException
  {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { popState(); return SHEBANG_CONTENT;
            }
          // fall through
          case 82: break;
          case 2:
            { popState(); return TokenType.BAD_CHARACTER;
            }
          // fall through
          case 83: break;
          case 3:
            { return STRING;
            }
          // fall through
          case 84: break;
          case 4:
            { return TokenType.BAD_CHARACTER;
            }
          // fall through
          case 85: break;
          case 5:
            { return TokenType.WHITE_SPACE;
            }
          // fall through
          case 86: break;
          case 6:
            { pushState(xDOUBLE_QUOTED_STRING); yypushback(yylength());
            }
          // fall through
          case 87: break;
          case 7:
            { return GETN;
            }
          // fall through
          case 88: break;
          case 8:
            { return MOD;
            }
          // fall through
          case 89: break;
          case 9:
            { return INTERSECTION;
            }
          // fall through
          case 90: break;
          case 10:
            { pushState(xSINGLE_QUOTED_STRING); yypushback(yylength());
            }
          // fall through
          case 91: break;
          case 11:
            { return LPAREN;
            }
          // fall through
          case 92: break;
          case 12:
            { return RPAREN;
            }
          // fall through
          case 93: break;
          case 13:
            { return MULT;
            }
          // fall through
          case 94: break;
          case 14:
            { return PLUS;
            }
          // fall through
          case 95: break;
          case 15:
            { return COMMA;
            }
          // fall through
          case 96: break;
          case 16:
            { return MINUS;
            }
          // fall through
          case 97: break;
          case 17:
            { return DOT;
            }
          // fall through
          case 98: break;
          case 18:
            { return DIV;
            }
          // fall through
          case 99: break;
          case 19:
            { return NUMBER;
            }
          // fall through
          case 100: break;
          case 20:
            { return COLON;
            }
          // fall through
          case 101: break;
          case 21:
            { return SEMI;
            }
          // fall through
          case 102: break;
          case 22:
            { return LT;
            }
          // fall through
          case 103: break;
          case 23:
            { return ASSIGN;
            }
          // fall through
          case 104: break;
          case 24:
            { return GT;
            }
          // fall through
          case 105: break;
          case 25:
            { return QUESTION;
            }
          // fall through
          case 106: break;
          case 26:
            { return ID;
            }
          // fall through
          case 107: break;
          case 27:
            { if (checkAhead('=', 0) || checkAhead('[', 0)) {
             yypushback(yylength());
             checkBlock();
             zzMarkedPos += checkBlockEnd();
             return STRING;
         } else {
             return LBRACK;
         }
            }
          // fall through
          case 108: break;
          case 28:
            { return RBRACK;
            }
          // fall through
          case 109: break;
          case 29:
            { return EXP;
            }
          // fall through
          case 110: break;
          case 30:
            { pushState(xTEMPLATE_STRING); return TEMPLATE_STRING_SQUOTE;
            }
          // fall through
          case 111: break;
          case 31:
            { return LCURLY;
            }
          // fall through
          case 112: break;
          case 32:
            { return UNION;
            }
          // fall through
          case 113: break;
          case 33:
            { popState(); return RCURLY;
            }
          // fall through
          case 114: break;
          case 34:
            { if (yycharat(yylength() - 1) == '"') { popState(); }; return STRING;
            }
          // fall through
          case 115: break;
          case 35:
            { if (yycharat(yylength() - 1) == '\'') { popState(); }; return STRING;
            }
          // fall through
          case 116: break;
          case 36:
            { popState(); return TEMPLATE_STRING_EQUOTE;
            }
          // fall through
          case 117: break;
          case 37:
            { pushState(xTEMPLATE_STRING_EXPRESSION); return LCURLY;
            }
          // fall through
          case 118: break;
          case 38:
            { pushState(xSHEBANG); return SHEBANG;
            }
          // fall through
          case 119: break;
          case 39:
            { return MOD_EQ;
            }
          // fall through
          case 120: break;
          case 40:
            { return MULT_EQ;
            }
          // fall through
          case 121: break;
          case 41:
            { return PLUS_EQ;
            }
          // fall through
          case 122: break;
          case 42:
            { boolean block = checkBlock();
          if (block) {
              boolean docBlock = checkDocBlock();
              yypushback(yylength());
              zzMarkedPos += checkBlockEnd();
              return docBlock ? DOC_BLOCK_COMMENT : BLOCK_COMMENT;
          }
          else { yypushback(yylength()); pushState(xCOMMENT); }
            }
          // fall through
          case 123: break;
          case 43:
            { return MINUS_EQ;
            }
          // fall through
          case 124: break;
          case 44:
            { return ARROW;
            }
          // fall through
          case 125: break;
          case 45:
            { return CONCAT;
            }
          // fall through
          case 126: break;
          case 46:
            { return DOUBLE_DIV;
            }
          // fall through
          case 127: break;
          case 47:
            { return DIV_EQ;
            }
          // fall through
          case 128: break;
          case 48:
            { return DOUBLE_COLON;
            }
          // fall through
          case 129: break;
          case 49:
            { return LE;
            }
          // fall through
          case 130: break;
          case 50:
            { return EQ;
            }
          // fall through
          case 131: break;
          case 51:
            { return GE;
            }
          // fall through
          case 132: break;
          case 52:
            { return EXP_EQ;
            }
          // fall through
          case 133: break;
          case 53:
            { return DO;
            }
          // fall through
          case 134: break;
          case 54:
            { return IF;
            }
          // fall through
          case 135: break;
          case 55:
            { return IN;
            }
          // fall through
          case 136: break;
          case 56:
            { return OR;
            }
          // fall through
          case 137: break;
          case 57:
            { return NE;
            }
          // fall through
          case 138: break;
          case 58:
            { popState(); return SHORT_COMMENT;
            }
          // fall through
          case 139: break;
          case 59:
            { return ELLIPSIS;
            }
          // fall through
          case 140: break;
          case 60:
            { return CONCAT_EQ;
            }
          // fall through
          case 141: break;
          case 61:
            { return DOUBLE_DIV_EQ;
            }
          // fall through
          case 142: break;
          case 62:
            { return AND;
            }
          // fall through
          case 143: break;
          case 63:
            { return END;
            }
          // fall through
          case 144: break;
          case 64:
            { return FOR;
            }
          // fall through
          case 145: break;
          case 65:
            { return NIL;
            }
          // fall through
          case 146: break;
          case 66:
            { return NOT;
            }
          // fall through
          case 147: break;
          case 67:
            { popState(); return DOC_COMMENT;
            }
          // fall through
          case 148: break;
          case 68:
            { return ELSE;
            }
          // fall through
          case 149: break;
          case 69:
            { return THEN;
            }
          // fall through
          case 150: break;
          case 70:
            { return TRUE;
            }
          // fall through
          case 151: break;
          case 71:
            { return REGION;
            }
          // fall through
          case 152: break;
          case 72:
            { return ENDREGION;
            }
          // fall through
          case 153: break;
          case 73:
            { return BREAK;
            }
          // fall through
          case 154: break;
          case 74:
            { return FALSE;
            }
          // fall through
          case 155: break;
          case 75:
            { return LOCAL;
            }
          // fall through
          case 156: break;
          case 76:
            { return UNTIL;
            }
          // fall through
          case 157: break;
          case 77:
            { return WHILE;
            }
          // fall through
          case 158: break;
          case 78:
            { return ELSEIF;
            }
          // fall through
          case 159: break;
          case 79:
            { return REPEAT;
            }
          // fall through
          case 160: break;
          case 80:
            { return RETURN;
            }
          // fall through
          case 161: break;
          case 81:
            { return FUNCTION;
            }
          // fall through
          case 162: break;
          default:
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
