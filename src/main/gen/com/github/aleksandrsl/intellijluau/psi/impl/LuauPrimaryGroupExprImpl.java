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

public class LuauPrimaryGroupExprImpl extends LuauExpressionImpl implements LuauPrimaryGroupExpr {

  public LuauPrimaryGroupExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitPrimaryGroupExpr(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LuauExpression getExpression() {
    return findChildByClass(LuauExpression.class);
  }

  @Override
  @Nullable
  public LuauFuncCall getFuncCall() {
    return findChildByClass(LuauFuncCall.class);
  }

  @Override
  @Nullable
  public LuauIndexAccess getIndexAccess() {
    return findChildByClass(LuauIndexAccess.class);
  }

  @Override
  @Nullable
  public LuauSimpleReference getSimpleReference() {
    return findChildByClass(LuauSimpleReference.class);
  }

}
