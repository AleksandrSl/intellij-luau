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

public class LuauTypeReferenceImpl extends ASTWrapperPsiElement implements LuauTypeReference {

  public LuauTypeReferenceImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitTypeReference(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LuauSimpleReference getSimpleReference() {
    return findChildByClass(LuauSimpleReference.class);
  }

  @Override
  @NotNull
  public LuauSimpleTypeReference getSimpleTypeReference() {
    return findNotNullChildByClass(LuauSimpleTypeReference.class);
  }

  @Override
  @Nullable
  public LuauTypeParams getTypeParams() {
    return findChildByClass(LuauTypeParams.class);
  }

}
