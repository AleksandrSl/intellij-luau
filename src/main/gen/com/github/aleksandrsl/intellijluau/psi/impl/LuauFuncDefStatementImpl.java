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
import com.intellij.navigation.ItemPresentation;

public class LuauFuncDefStatementImpl extends ASTWrapperPsiElement implements LuauFuncDefStatement {

  public LuauFuncDefStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitFuncDefStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LuauFuncBody getFuncBody() {
    return findChildByClass(LuauFuncBody.class);
  }

  @Override
  @Nullable
  public LuauFuncName getFuncName() {
    return findChildByClass(LuauFuncName.class);
  }

  @Override
  @NotNull
  public ItemPresentation getPresentation() {
    return LuauPsiImplUtilKt.getPresentation(this);
  }

}
