// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauAsExpr extends LuauExpression {

  @Nullable
  LuauExpression getExpression();

  @Nullable
  LuauFuncCall getFuncCall();

  @Nullable
  LuauIndexAccess getIndexAccess();

  @Nullable
  LuauSimpleReference getSimpleReference();

  @Nullable
  LuauType getType();

}
