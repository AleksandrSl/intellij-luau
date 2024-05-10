// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauSimpleType extends PsiElement {

  @Nullable
  LuauExpression getExpression();

  @Nullable
  LuauFunctionType getFunctionType();

  @Nullable
  LuauSingletonType getSingletonType();

  @Nullable
  LuauTableType getTableType();

  @Nullable
  LuauType getType();

  @Nullable
  LuauTypeParams getTypeParams();

}
