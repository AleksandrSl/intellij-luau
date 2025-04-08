// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauClassicForStatement extends LuauStatement {

  @NotNull
  LuauBinding getBinding();

  @Nullable
  LuauBlock getBlock();

  @NotNull
  List<LuauExpression> getExpressionList();

}
