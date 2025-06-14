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

public class LuauTypeFieldImpl extends ASTWrapperPsiElement implements LuauTypeField {

  public LuauTypeFieldImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitTypeField(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LuauReadSoftKeyword getReadSoftKeyword() {
    return findChildByClass(LuauReadSoftKeyword.class);
  }

  @Override
  @Nullable
  public LuauTypeKeyedField getTypeKeyedField() {
    return findChildByClass(LuauTypeKeyedField.class);
  }

  @Override
  @Nullable
  public LuauTypeStringKeyedField getTypeStringKeyedField() {
    return findChildByClass(LuauTypeStringKeyedField.class);
  }

  @Override
  @Nullable
  public LuauWriteSoftKeyword getWriteSoftKeyword() {
    return findChildByClass(LuauWriteSoftKeyword.class);
  }

}
