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

public class LuauFieldImpl extends ASTWrapperPsiElement implements LuauField {

  public LuauFieldImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitField(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LuauIndexedField getIndexedField() {
    return findChildByClass(LuauIndexedField.class);
  }

  @Override
  @Nullable
  public LuauKeyedField getKeyedField() {
    return findChildByClass(LuauKeyedField.class);
  }

  @Override
  @Nullable
  public LuauStringKeyedField getStringKeyedField() {
    return findChildByClass(LuauStringKeyedField.class);
  }

}
