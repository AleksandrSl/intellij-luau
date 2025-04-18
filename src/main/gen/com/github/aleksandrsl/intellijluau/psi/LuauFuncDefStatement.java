// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.navigation.ItemPresentation;

public interface LuauFuncDefStatement extends LuauNamedElement, LuauStatement {

  @Nullable
  LuauAttributes getAttributes();

  @Nullable
  LuauFuncBody getFuncBody();

  @Nullable
  PsiElement getId();

  @NotNull ItemPresentation getPresentation();

}
