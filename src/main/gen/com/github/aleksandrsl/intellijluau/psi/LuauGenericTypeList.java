// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauGenericTypeList extends PsiElement {

  @Nullable
  LuauGenericTypeList getGenericTypeList();

  @NotNull
  List<LuauGenericTypePackParameter> getGenericTypePackParameterList();

  @Nullable
  PsiElement getId();

}
