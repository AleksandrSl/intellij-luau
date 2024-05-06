// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauPrefixExp extends PsiElement {

  @Nullable
  LuauExpression getExpression();

  @NotNull
  List<LuauPostfixExp> getPostfixExpList();

  @Nullable
  LuauVar getVar();

}