// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.aleksandrsl.intellijluau.psi.LuauTypes.*;
import com.github.aleksandrsl.intellijluau.psi.LuauBlockImplMixin;
import com.github.aleksandrsl.intellijluau.psi.*;

public class LuauRootBlockImpl extends LuauBlockImplMixin implements LuauRootBlock {

  public LuauRootBlockImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitRootBlock(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<LuauAssignmentStatement> getAssignmentStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauAssignmentStatement.class);
  }

  @Override
  @NotNull
  public List<LuauClassicForStatement> getClassicForStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauClassicForStatement.class);
  }

  @Override
  @NotNull
  public List<LuauCompoundOpStatement> getCompoundOpStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauCompoundOpStatement.class);
  }

  @Override
  @NotNull
  public List<LuauDoStatement> getDoStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauDoStatement.class);
  }

  @Override
  @NotNull
  public List<LuauExpressionStatement> getExpressionStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauExpressionStatement.class);
  }

  @Override
  @NotNull
  public List<LuauForeachStatement> getForeachStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauForeachStatement.class);
  }

  @Override
  @NotNull
  public List<LuauFuncDefStatement> getFuncDefStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauFuncDefStatement.class);
  }

  @Override
  @NotNull
  public List<LuauIfStatement> getIfStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauIfStatement.class);
  }

  @Override
  @NotNull
  public List<LuauLocalDefStatement> getLocalDefStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauLocalDefStatement.class);
  }

  @Override
  @NotNull
  public List<LuauLocalFuncDefStatement> getLocalFuncDefStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauLocalFuncDefStatement.class);
  }

  @Override
  @NotNull
  public List<LuauMethodDefStatement> getMethodDefStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauMethodDefStatement.class);
  }

  @Override
  @NotNull
  public List<LuauRepeatStatement> getRepeatStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauRepeatStatement.class);
  }

  @Override
  @Nullable
  public LuauReturnStatement getReturnStatement() {
    return findChildByClass(LuauReturnStatement.class);
  }

  @Override
  @NotNull
  public List<LuauTypeDeclarationStatement> getTypeDeclarationStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauTypeDeclarationStatement.class);
  }

  @Override
  @NotNull
  public List<LuauTypeFunctionDeclarationStatement> getTypeFunctionDeclarationStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauTypeFunctionDeclarationStatement.class);
  }

  @Override
  @NotNull
  public List<LuauWhileStatement> getWhileStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauWhileStatement.class);
  }

}
