// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauAssignmentStatement extends PsiElement {

  @Nullable
  LuauCompoundOp getCompoundOp();

  @Nullable
  LuauExpList getExpList();

  @Nullable
  LuauExpression getExpression();

  @Nullable
  LuauLocalDefStatement getLocalDefStatement();

  @Nullable
  LuauVar getVar();

  @Nullable
  LuauVarList getVarList();

}
