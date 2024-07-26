// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauBoundTypeList extends PsiElement {

  @Nullable
  LuauBoundTypeList getBoundTypeList();

  @Nullable
  LuauGenericTypePack getGenericTypePack();

  @Nullable
  LuauType getType();

  @Nullable
  LuauVariadicTypePack getVariadicTypePack();

  @Nullable
  PsiElement getId();

}
