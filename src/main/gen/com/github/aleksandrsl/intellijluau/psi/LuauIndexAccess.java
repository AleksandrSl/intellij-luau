// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauIndexAccess extends PsiElement {

  @NotNull
  List<LuauExpression> getExpressionList();

  @Nullable
  LuauFuncCall getFuncCall();

  @Nullable
  LuauIndexAccess getIndexAccess();

  @NotNull
  List<LuauSimpleReference> getSimpleReferenceList();

}
