// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class LuauVisitor extends PsiElementVisitor {

  public void visitAsExp(@NotNull LuauAsExp o) {
    visitExpression(o);
  }

  public void visitAssignmentStatement(@NotNull LuauAssignmentStatement o) {
    visitPsiElement(o);
  }

  public void visitBinOp(@NotNull LuauBinOp o) {
    visitPsiElement(o);
  }

  public void visitBinaryExp(@NotNull LuauBinaryExp o) {
    visitExpression(o);
  }

  public void visitBinding(@NotNull LuauBinding o) {
    visitNamedElement(o);
  }

  public void visitBindingList(@NotNull LuauBindingList o) {
    visitPsiElement(o);
  }

  public void visitBlock(@NotNull LuauBlock o) {
    visitPsiElement(o);
  }

  public void visitBoundTypeList(@NotNull LuauBoundTypeList o) {
    visitPsiElement(o);
  }

  public void visitClassicForStatement(@NotNull LuauClassicForStatement o) {
    visitPsiElement(o);
  }

  public void visitClosureExp(@NotNull LuauClosureExp o) {
    visitPsiElement(o);
  }

  public void visitCompoundOp(@NotNull LuauCompoundOp o) {
    visitPsiElement(o);
  }

  public void visitCompoundOpStatement(@NotNull LuauCompoundOpStatement o) {
    visitPsiElement(o);
  }

  public void visitComputedKey(@NotNull LuauComputedKey o) {
    visitPsiElement(o);
  }

  public void visitContinueSoftKeyword(@NotNull LuauContinueSoftKeyword o) {
    visitSoftKeyword(o);
  }

  public void visitDoStatement(@NotNull LuauDoStatement o) {
    visitPsiElement(o);
  }

  public void visitExpList(@NotNull LuauExpList o) {
    visitPsiElement(o);
  }

  public void visitExportSoftKeyword(@NotNull LuauExportSoftKeyword o) {
    visitSoftKeyword(o);
  }

  public void visitExpression(@NotNull LuauExpression o) {
    visitPsiElement(o);
  }

  public void visitExpressionStatement(@NotNull LuauExpressionStatement o) {
    visitPsiElement(o);
  }

  public void visitField(@NotNull LuauField o) {
    visitPsiElement(o);
  }

  public void visitFieldList(@NotNull LuauFieldList o) {
    visitPsiElement(o);
  }

  public void visitFieldSep(@NotNull LuauFieldSep o) {
    visitPsiElement(o);
  }

  public void visitForeachStatement(@NotNull LuauForeachStatement o) {
    visitPsiElement(o);
  }

  public void visitFuncArgs(@NotNull LuauFuncArgs o) {
    visitPsiElement(o);
  }

  public void visitFuncBody(@NotNull LuauFuncBody o) {
    visitPsiElement(o);
  }

  public void visitFuncDefStatement(@NotNull LuauFuncDefStatement o) {
    visitNamedElement(o);
  }

  public void visitGenericTypeList(@NotNull LuauGenericTypeList o) {
    visitPsiElement(o);
  }

  public void visitGenericTypeListWithDefaults(@NotNull LuauGenericTypeListWithDefaults o) {
    visitPsiElement(o);
  }

  public void visitGenericTypePack(@NotNull LuauGenericTypePack o) {
    visitPsiElement(o);
  }

  public void visitGenericTypePackParameter(@NotNull LuauGenericTypePackParameter o) {
    visitPsiElement(o);
  }

  public void visitGenericTypePackParameterWithDefault(@NotNull LuauGenericTypePackParameterWithDefault o) {
    visitPsiElement(o);
  }

  public void visitIfStatement(@NotNull LuauIfStatement o) {
    visitPsiElement(o);
  }

  public void visitIfelseExp(@NotNull LuauIfelseExp o) {
    visitPsiElement(o);
  }

  public void visitIndexExpr(@NotNull LuauIndexExpr o) {
    visitPsiElement(o);
  }

  public void visitIndexedField(@NotNull LuauIndexedField o) {
    visitPsiElement(o);
  }

  public void visitKeyedField(@NotNull LuauKeyedField o) {
    visitPsiElement(o);
  }

  public void visitLastStatement(@NotNull LuauLastStatement o) {
    visitPsiElement(o);
  }

  public void visitListArgs(@NotNull LuauListArgs o) {
    visitFuncArgs(o);
  }

  public void visitLocalDefStatement(@NotNull LuauLocalDefStatement o) {
    visitPsiElement(o);
  }

  public void visitLocalFuncDefStatement(@NotNull LuauLocalFuncDefStatement o) {
    visitPsiElement(o);
  }

  public void visitMethodDefStatement(@NotNull LuauMethodDefStatement o) {
    visitPsiElement(o);
  }

  public void visitMethodName(@NotNull LuauMethodName o) {
    visitPsiElement(o);
  }

  public void visitParList(@NotNull LuauParList o) {
    visitPsiElement(o);
  }

  public void visitPostfixExp(@NotNull LuauPostfixExp o) {
    visitPsiElement(o);
  }

  public void visitPrefixExp(@NotNull LuauPrefixExp o) {
    visitPsiElement(o);
  }

  public void visitPrimaryExp(@NotNull LuauPrimaryExp o) {
    visitPsiElement(o);
  }

  public void visitRepeatStatement(@NotNull LuauRepeatStatement o) {
    visitPsiElement(o);
  }

  public void visitReturnType(@NotNull LuauReturnType o) {
    visitPsiElement(o);
  }

  public void visitShebangLine(@NotNull LuauShebangLine o) {
    visitPsiElement(o);
  }

  public void visitSimpleExp(@NotNull LuauSimpleExp o) {
    visitExpression(o);
  }

  public void visitSimpleType(@NotNull LuauSimpleType o) {
    visitPsiElement(o);
  }

  public void visitSingleArg(@NotNull LuauSingleArg o) {
    visitFuncArgs(o);
  }

  public void visitSingletonType(@NotNull LuauSingletonType o) {
    visitPsiElement(o);
  }

  public void visitStatement(@NotNull LuauStatement o) {
    visitPsiElement(o);
  }

  public void visitStringKeyedField(@NotNull LuauStringKeyedField o) {
    visitPsiElement(o);
  }

  public void visitTableConstructor(@NotNull LuauTableConstructor o) {
    visitPsiElement(o);
  }

  public void visitTemplateString(@NotNull LuauTemplateString o) {
    visitPsiElement(o);
  }

  public void visitType(@NotNull LuauType o) {
    visitPsiElement(o);
  }

  public void visitTypeComputedKey(@NotNull LuauTypeComputedKey o) {
    visitPsiElement(o);
  }

  public void visitTypeDeclarationStatement(@NotNull LuauTypeDeclarationStatement o) {
    visitPsiElement(o);
  }

  public void visitTypeField(@NotNull LuauTypeField o) {
    visitPsiElement(o);
  }

  public void visitTypeFieldList(@NotNull LuauTypeFieldList o) {
    visitPsiElement(o);
  }

  public void visitTypeFunction(@NotNull LuauTypeFunction o) {
    visitPsiElement(o);
  }

  public void visitTypeIntersection(@NotNull LuauTypeIntersection o) {
    visitPsiElement(o);
  }

  public void visitTypeKeyedField(@NotNull LuauTypeKeyedField o) {
    visitPsiElement(o);
  }

  public void visitTypeList(@NotNull LuauTypeList o) {
    visitPsiElement(o);
  }

  public void visitTypePack(@NotNull LuauTypePack o) {
    visitPsiElement(o);
  }

  public void visitTypeParametersList(@NotNull LuauTypeParametersList o) {
    visitPsiElement(o);
  }

  public void visitTypeParams(@NotNull LuauTypeParams o) {
    visitPsiElement(o);
  }

  public void visitTypeSoftKeyword(@NotNull LuauTypeSoftKeyword o) {
    visitSoftKeyword(o);
  }

  public void visitTypeStringKeyedField(@NotNull LuauTypeStringKeyedField o) {
    visitPsiElement(o);
  }

  public void visitTypeTable(@NotNull LuauTypeTable o) {
    visitPsiElement(o);
  }

  public void visitTypeUnion(@NotNull LuauTypeUnion o) {
    visitPsiElement(o);
  }

  public void visitTypeofSoftKeyword(@NotNull LuauTypeofSoftKeyword o) {
    visitSoftKeyword(o);
  }

  public void visitUnOp(@NotNull LuauUnOp o) {
    visitPsiElement(o);
  }

  public void visitUnaryExp(@NotNull LuauUnaryExp o) {
    visitExpression(o);
  }

  public void visitVar(@NotNull LuauVar o) {
    visitPsiElement(o);
  }

  public void visitVarList(@NotNull LuauVarList o) {
    visitPsiElement(o);
  }

  public void visitVarReference(@NotNull LuauVarReference o) {
    visitPsiElement(o);
  }

  public void visitVariadicTypePack(@NotNull LuauVariadicTypePack o) {
    visitPsiElement(o);
  }

  public void visitWhileStatement(@NotNull LuauWhileStatement o) {
    visitPsiElement(o);
  }

  public void visitNamedElement(@NotNull LuauNamedElement o) {
    visitPsiElement(o);
  }

  public void visitSoftKeyword(@NotNull LuauSoftKeyword o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
