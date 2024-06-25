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

public class LuauSimpleExpImpl extends LuauExpressionImpl implements LuauSimpleExp {

  public LuauSimpleExpImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitSimpleExp(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LuauClosureExp getClosureExp() {
    return findChildByClass(LuauClosureExp.class);
  }

  @Override
  @Nullable
  public LuauIfelseExp getIfelseExp() {
    return findChildByClass(LuauIfelseExp.class);
  }

  @Override
  @Nullable
  public LuauPrimaryExp getPrimaryExp() {
    return findChildByClass(LuauPrimaryExp.class);
  }

  @Override
  @Nullable
  public LuauTableConstructor getTableConstructor() {
    return findChildByClass(LuauTableConstructor.class);
  }

  @Override
  @Nullable
  public LuauTemplateString getTemplateString() {
    return findChildByClass(LuauTemplateString.class);
  }

  @Override
  @Nullable
  public PsiElement getNumber() {
    return findChildByType(NUMBER);
  }

  @Override
  @Nullable
  public PsiElement getString() {
    return findChildByType(STRING);
  }

}
