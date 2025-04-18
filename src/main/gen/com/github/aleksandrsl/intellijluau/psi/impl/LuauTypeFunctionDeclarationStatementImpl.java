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

public class LuauTypeFunctionDeclarationStatementImpl extends LuauNamedElementImpl implements LuauTypeFunctionDeclarationStatement {

  public LuauTypeFunctionDeclarationStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuauVisitor visitor) {
    visitor.visitTypeFunctionDeclarationStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuauVisitor) accept((LuauVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LuauExportSoftKeyword getExportSoftKeyword() {
    return findChildByClass(LuauExportSoftKeyword.class);
  }

  @Override
  @Nullable
  public LuauFuncBody getFuncBody() {
    return findChildByClass(LuauFuncBody.class);
  }

  @Override
  @NotNull
  public LuauTypeSoftKeyword getTypeSoftKeyword() {
    return findNotNullChildByClass(LuauTypeSoftKeyword.class);
  }

  @Override
  @Nullable
  public PsiElement getId() {
    return findChildByType(ID);
  }

}
