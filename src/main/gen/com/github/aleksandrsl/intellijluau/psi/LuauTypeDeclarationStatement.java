// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import java.util.Collection;

public interface LuauTypeDeclarationStatement extends LuauNamedElement, LuauGenericDeclaration, LuauStatement {

  @Nullable
  LuauExportSoftKeyword getExportSoftKeyword();

  @Nullable
  LuauGenericTypeListWithDefaults getGenericTypeListWithDefaults();

  @Nullable
  LuauType getType();

  @NotNull
  LuauTypeSoftKeyword getTypeSoftKeyword();

  @Nullable
  PsiElement getId();

  @NotNull Collection<@NotNull LuauNamedElement> getDeclaredGenerics();

}
