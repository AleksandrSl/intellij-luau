// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauSimpleType extends PsiElement {

  @Nullable
  LuauExpression getExpression();

  @Nullable
  LuauSingletonType getSingletonType();

  @Nullable
  LuauType getType();

  @Nullable
  LuauTypeFunction getTypeFunction();

  @Nullable
  LuauTypeParams getTypeParams();

  @Nullable
  LuauTypeTable getTypeTable();

  @Nullable
  LuauTypeofSoftKeyword getTypeofSoftKeyword();

}
