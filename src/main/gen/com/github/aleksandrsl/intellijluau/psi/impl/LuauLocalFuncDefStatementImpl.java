// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.aleksandrsl.intellijluau.psi.LuauTypes.*;
import com.github.aleksandrsl.intellijluau.psi.LuauNamedElementImpl;
import com.github.aleksandrsl.intellijluau.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;

public class LuauLocalFuncDefStatementImpl extends LuauNamedElementImpl implements LuauLocalFuncDefStatement {

  public LuauLocalFuncDefStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitLocalFuncDefStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LuauAttributes getAttributes() {
    return findChildByClass(LuauAttributes.class);
  }

  @Override
  @Nullable
  public LuauFuncBody getFuncBody() {
    return findChildByClass(LuauFuncBody.class);
  }

  @Override
  @Nullable
  public PsiElement getId() {
    return findChildByType(ID);
  }

  @Override
  public @NotNull ItemPresentation getPresentation() {
    return LuauPsiImplUtilKt.getPresentation(this);
  }

  @Override
  public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, @Nullable PsiElement lastParent, @NotNull PsiElement place) {
    return LuauPsiImplUtilKt.processDeclarations(this, processor, state, lastParent, place);
  }

}
