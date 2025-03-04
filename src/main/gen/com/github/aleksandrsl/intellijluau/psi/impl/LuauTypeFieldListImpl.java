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

public class LuauTypeFieldListImpl extends ASTWrapperPsiElement implements LuauTypeFieldList {

  public LuauTypeFieldListImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitTypeFieldList(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<LuauFieldSep> getFieldSepList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauFieldSep.class);
  }

  @Override
  @NotNull
  public List<LuauTypeField> getTypeFieldList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauTypeField.class);
  }

}
