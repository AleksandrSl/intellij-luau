// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauStatement extends PsiElement {

  @Nullable
  LuauAssignmentStatement getAssignmentStatement();

  @Nullable
  LuauClassicForStatement getClassicForStatement();

  @Nullable
  LuauCompoundOpStatement getCompoundOpStatement();

  @Nullable
  LuauDoStatement getDoStatement();

  @Nullable
  LuauExpressionStatement getExpressionStatement();

  @Nullable
  LuauForeachStatement getForeachStatement();

  @Nullable
  LuauFuncDefStatement getFuncDefStatement();

  @Nullable
  LuauIfStatement getIfStatement();

  @Nullable
  LuauLocalDefStatement getLocalDefStatement();

  @Nullable
  LuauLocalFuncDefStatement getLocalFuncDefStatement();

  @Nullable
  LuauRepeatStatement getRepeatStatement();

  @Nullable
  LuauTypeDeclarationStatement getTypeDeclarationStatement();

  @Nullable
  LuauWhileStatement getWhileStatement();

}
