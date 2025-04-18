// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import java.util.Collection;

public interface LuauFuncBody extends LuauGenericDeclaration {

  @Nullable
  LuauBlock getBlock();

  @Nullable
  LuauFuncTypeParams getFuncTypeParams();

  @NotNull
  LuauParList getParList();

  @Nullable
  LuauReturnType getReturnType();

  @NotNull Collection<@NotNull LuauNamedElement> getDeclaredGenerics();

}
