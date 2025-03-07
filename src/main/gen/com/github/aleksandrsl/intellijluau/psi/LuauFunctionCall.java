// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauFunctionCall extends PsiElement {

  @NotNull
  List<LuauFuncArgs> getFuncArgsList();

  @NotNull
  List<LuauIndexExpr> getIndexExprList();

  @NotNull
  LuauPrefixExp getPrefixExp();

}
