// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.aleksandrsl.intellijluau.psi.LuauTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.aleksandrsl.intellijluau.psi.*;

public class LuauClassicForStatementImpl extends ASTWrapperPsiElement implements LuauClassicForStatement {

  public LuauClassicForStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitClassicForStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public LuauBinding getBinding() {
    return findNotNullChildByClass(LuauBinding.class);
  }

  @Override
  @Nullable
  public LuauBlock getBlock() {
    return findChildByClass(LuauBlock.class);
  }

  @Override
  @NotNull
  public List<LuauExpression> getExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauExpression.class);
  }

}
