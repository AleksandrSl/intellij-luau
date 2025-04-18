// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauTypeReference extends LuauElement {

  @Nullable
  LuauSimpleReference getSimpleReference();

  @NotNull
  LuauSimpleTypeReference getSimpleTypeReference();

  @Nullable
  LuauTypeParams getTypeParams();

}
