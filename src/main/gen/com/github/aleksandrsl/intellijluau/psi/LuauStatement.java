// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauStatement extends PsiElement {

  @Nullable
  LuauGenericTypeListWithDefaults getGenericTypeListWithDefaults();

  @Nullable
  LuauType getType();

  @Nullable
  LuauAssignmentStatement getAssignmentStatement();

  @Nullable
  LuauClassicForStatement getClassicForStatement();

  @Nullable
  LuauDoStatement getDoStatement();

  @Nullable
  LuauExpressionStatement getExpressionStatement();

  @Nullable
  LuauForeachStatement getForeachStatement();

  @Nullable
  LuauFuncDefStat getFuncDefStat();

  @Nullable
  LuauFunctioncall getFunctioncall();

  @Nullable
  LuauIfStatement getIfStatement();

  @Nullable
  LuauLocalFuncDefStat getLocalFuncDefStat();

  @Nullable
  LuauRepeatStatement getRepeatStatement();

  @Nullable
  LuauWhileStatement getWhileStatement();

  @Nullable
  PsiElement getId();

}
