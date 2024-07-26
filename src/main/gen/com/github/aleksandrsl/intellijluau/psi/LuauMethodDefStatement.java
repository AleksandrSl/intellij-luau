// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.navigation.ItemPresentation;

public interface LuauMethodDefStatement extends PsiElement {

  @Nullable
  LuauFuncBody getFuncBody();

  @NotNull
  LuauMethodName getMethodName();

  @NotNull
  ItemPresentation getPresentation();

}
