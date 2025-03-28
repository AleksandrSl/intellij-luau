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

public class LuauTypeParamsImpl extends ASTWrapperPsiElement implements LuauTypeParams {

  public LuauTypeParamsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitTypeParams(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<LuauGenericTypePack> getGenericTypePackList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauGenericTypePack.class);
  }

  @Override
  @NotNull
  public List<LuauType> getTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauType.class);
  }

  @Override
  @NotNull
  public List<LuauTypePack> getTypePackList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauTypePack.class);
  }

  @Override
  @NotNull
  public List<LuauVariadicTypePack> getVariadicTypePackList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LuauVariadicTypePack.class);
  }

}
