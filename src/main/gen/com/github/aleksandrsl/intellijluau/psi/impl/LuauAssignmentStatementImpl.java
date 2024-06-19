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

public class LuauAssignmentStatementImpl extends ASTWrapperPsiElement implements LuauAssignmentStatement {

  public LuauAssignmentStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitAssignmentStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LuauCompoundOp getCompoundOp() {
    return findChildByClass(LuauCompoundOp.class);
  }

  @Override
  @Nullable
  public LuauExpList getExpList() {
    return findChildByClass(LuauExpList.class);
  }

  @Override
  @Nullable
  public LuauExpression getExpression() {
    return findChildByClass(LuauExpression.class);
  }

  @Override
  @Nullable
  public LuauLocalDefStatement getLocalDefStatement() {
    return findChildByClass(LuauLocalDefStatement.class);
  }

  @Override
  @Nullable
  public LuauVar getVar() {
    return findChildByClass(LuauVar.class);
  }

  @Override
  @Nullable
  public LuauVarList getVarList() {
    return findChildByClass(LuauVarList.class);
  }

}
