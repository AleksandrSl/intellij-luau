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
import java.util.Collection;

public class LuauFuncBodyImpl extends ASTWrapperPsiElement implements LuauFuncBody {

  public LuauFuncBodyImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitFuncBody(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LuauBlock getBlock() {
    return findChildByClass(LuauBlock.class);
  }

  @Override
  @Nullable
  public LuauFuncTypeParams getFuncTypeParams() {
    return findChildByClass(LuauFuncTypeParams.class);
  }

  @Override
  @NotNull
  public LuauParList getParList() {
    return findNotNullChildByClass(LuauParList.class);
  }

  @Override
  @Nullable
  public LuauReturnType getReturnType() {
    return findChildByClass(LuauReturnType.class);
  }

  @Override
  @NotNull
  public Collection<LuauNamedElement> getDeclaredGenerics() {
    return LuauPsiImplUtilKt.getDeclaredGenerics(this);
  }

}
