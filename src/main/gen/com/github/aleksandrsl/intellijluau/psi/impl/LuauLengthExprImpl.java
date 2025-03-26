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

public class LuauLengthExprImpl extends LuauExpressionImpl implements LuauLengthExpr {

  public LuauLengthExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitLengthExpr(this);
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
  @NotNull
  public LuauOperator getOperator() {
    return findNotNullChildByClass(LuauOperator.class);
  }

}
