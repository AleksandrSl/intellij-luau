// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauGenericTypeListWithDefaults extends PsiElement {

  @Nullable
  LuauGenericTypeList getGenericTypeList();

  @NotNull
  List<LuauGenericTypePackParameterWithDefault> getGenericTypePackParameterWithDefaultList();

  @Nullable
  LuauType getType();

}