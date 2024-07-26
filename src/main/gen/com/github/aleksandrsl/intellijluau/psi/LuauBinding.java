// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.navigation.ItemPresentation;

public interface LuauBinding extends LuauNamedElement {

  @Nullable
  LuauType getType();

  @NotNull
  PsiElement getId();

  @NotNull
  ItemPresentation getPresentation();

}
