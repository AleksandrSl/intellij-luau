// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauFunctionType extends PsiElement {

  @Nullable
  LuauBoundTypeList getBoundTypeList();

  @Nullable
  LuauGenericTypeList getGenericTypeList();

  @Nullable
  LuauReturnType getReturnType();

}
