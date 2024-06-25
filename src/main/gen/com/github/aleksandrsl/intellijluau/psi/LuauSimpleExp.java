// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauSimpleExp extends LuauExpression {

  @Nullable
  LuauClosureExp getClosureExp();

  @Nullable
  LuauIfelseExp getIfelseExp();

  @Nullable
  LuauPrimaryExp getPrimaryExp();

  @Nullable
  LuauTableConstructor getTableConstructor();

  @Nullable
  LuauTemplateString getTemplateString();

  @Nullable
  PsiElement getNumber();

  @Nullable
  PsiElement getString();

}
