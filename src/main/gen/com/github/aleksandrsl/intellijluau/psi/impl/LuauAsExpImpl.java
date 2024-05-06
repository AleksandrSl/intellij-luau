// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.aleksandrsl.intellijluau.psi.LuauTypes.*;
import com.github.aleksandrsl.intellijluau.psi.*;

public class LuauAsExpImpl extends LuauExpressionImpl implements LuauAsExp {

  public LuauAsExpImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitAsExp(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public LuauType getType() {
    return findNotNullChildByClass(LuauType.class);
  }

  @Override
  @NotNull
  public LuauSimpleExp getSimpleExp() {
    return findNotNullChildByClass(LuauSimpleExp.class);
  }

}