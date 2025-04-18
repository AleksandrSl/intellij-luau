// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauTypeParams extends LuauElement {

  @NotNull
  List<LuauGenericTypePack> getGenericTypePackList();

  @NotNull
  List<LuauType> getTypeList();

  @NotNull
  List<LuauTypePack> getTypePackList();

  @NotNull
  List<LuauVariadicTypePack> getVariadicTypePackList();

}
