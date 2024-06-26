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

public class LuauStatementImpl extends ASTWrapperPsiElement implements LuauStatement {

  public LuauStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LuauAssignmentStatement getAssignmentStatement() {
    return findChildByClass(LuauAssignmentStatement.class);
  }

  @Override
  @Nullable
  public LuauClassicForStatement getClassicForStatement() {
    return findChildByClass(LuauClassicForStatement.class);
  }

  @Override
  @Nullable
  public LuauCompoundOpStatement getCompoundOpStatement() {
    return findChildByClass(LuauCompoundOpStatement.class);
  }

  @Override
  @Nullable
  public LuauDoStatement getDoStatement() {
    return findChildByClass(LuauDoStatement.class);
  }

  @Override
  @Nullable
  public LuauExpressionStatement getExpressionStatement() {
    return findChildByClass(LuauExpressionStatement.class);
  }

  @Override
  @Nullable
  public LuauForeachStatement getForeachStatement() {
    return findChildByClass(LuauForeachStatement.class);
  }

  @Override
  @Nullable
  public LuauFuncDefStatement getFuncDefStatement() {
    return findChildByClass(LuauFuncDefStatement.class);
  }

  @Override
  @Nullable
  public LuauIfStatement getIfStatement() {
    return findChildByClass(LuauIfStatement.class);
  }

  @Override
  @Nullable
  public LuauLocalDefStatement getLocalDefStatement() {
    return findChildByClass(LuauLocalDefStatement.class);
  }

  @Override
  @Nullable
  public LuauLocalFuncDefStatement getLocalFuncDefStatement() {
    return findChildByClass(LuauLocalFuncDefStatement.class);
  }

  @Override
  @Nullable
  public LuauMethodDefStatement getMethodDefStatement() {
    return findChildByClass(LuauMethodDefStatement.class);
  }

  @Override
  @Nullable
  public LuauRepeatStatement getRepeatStatement() {
    return findChildByClass(LuauRepeatStatement.class);
  }

  @Override
  @Nullable
  public LuauTypeDeclarationStatement getTypeDeclarationStatement() {
    return findChildByClass(LuauTypeDeclarationStatement.class);
  }

  @Override
  @Nullable
  public LuauWhileStatement getWhileStatement() {
    return findChildByClass(LuauWhileStatement.class);
  }

}
