// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauSimpleExp extends LuauExpression {

  @Nullable
  LuauClosureExpr getClosureExpr();

  @Nullable
  LuauIfelseexp getIfelseexp();

  @Nullable
  LuauPrefixExp getPrefixExp();

  @Nullable
  LuauTableconstructor getTableconstructor();

  @Nullable
  LuauTemplateString getTemplateString();

  @Nullable
  PsiElement getNumber();

  @Nullable
  PsiElement getString();

}
