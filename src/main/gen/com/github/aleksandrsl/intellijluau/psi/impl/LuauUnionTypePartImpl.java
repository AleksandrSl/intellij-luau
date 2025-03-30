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

public class LuauUnionTypePartImpl extends ASTWrapperPsiElement implements LuauUnionTypePart {

  public LuauUnionTypePartImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitUnionTypePart(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LuauFunctionType getFunctionType() {
    return findChildByClass(LuauFunctionType.class);
  }

  @Override
  @Nullable
  public LuauParenthesisedType getParenthesisedType() {
    return findChildByClass(LuauParenthesisedType.class);
  }

  @Override
  @Nullable
  public LuauSingletonType getSingletonType() {
    return findChildByClass(LuauSingletonType.class);
  }

  @Override
  @Nullable
  public LuauTableType getTableType() {
    return findChildByClass(LuauTableType.class);
  }

  @Override
  @Nullable
  public LuauTypeReference getTypeReference() {
    return findChildByClass(LuauTypeReference.class);
  }

  @Override
  @Nullable
  public LuauTypeofType getTypeofType() {
    return findChildByClass(LuauTypeofType.class);
  }

}
