// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauCompoundOpStatement extends PsiElement {

  @NotNull
  LuauCompoundOp getCompoundOp();

  @Nullable
  LuauExpression getExpression();

  @NotNull
  LuauVar getVar();

}
