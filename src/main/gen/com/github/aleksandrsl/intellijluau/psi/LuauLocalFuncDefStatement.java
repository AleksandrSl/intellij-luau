// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;

public interface LuauLocalFuncDefStatement extends LuauNamedElement, LuauStatement {

  @Nullable
  LuauAttributes getAttributes();

  @Nullable
  LuauFuncBody getFuncBody();

  @Nullable
  PsiElement getId();

  @NotNull ItemPresentation getPresentation();

  boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, @Nullable PsiElement lastParent, @NotNull PsiElement place);

}
