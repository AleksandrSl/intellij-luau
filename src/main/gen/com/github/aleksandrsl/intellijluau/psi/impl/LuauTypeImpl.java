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

public class LuauTypeImpl extends ASTWrapperPsiElement implements LuauType {

  public LuauTypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitType(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LuauIntersectionType getIntersectionType() {
    return findChildByClass(LuauIntersectionType.class);
  }

  @Override
  @Nullable
  public LuauIntersectionTypePart getIntersectionTypePart() {
    return findChildByClass(LuauIntersectionTypePart.class);
  }

  @Override
  @Nullable
  public LuauSimpleType getSimpleType() {
    return findChildByClass(LuauSimpleType.class);
  }

  @Override
  @Nullable
  public LuauUnionType getUnionType() {
    return findChildByClass(LuauUnionType.class);
  }

  @Override
  @Nullable
  public LuauUnionTypePart getUnionTypePart() {
    return findChildByClass(LuauUnionTypePart.class);
  }

}
