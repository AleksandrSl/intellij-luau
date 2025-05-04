// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauType extends LuauTypeElement {

  @Nullable
  LuauFunctionType getFunctionType();

  @Nullable
  LuauParenthesisedType getParenthesisedType();

  @Nullable
  LuauSingletonType getSingletonType();

  @Nullable
  LuauTableType getTableType();

  @Nullable
  LuauTypeReference getTypeReference();

  @Nullable
  LuauTypeofType getTypeofType();

  @NotNull String getTextPresentation();

}
