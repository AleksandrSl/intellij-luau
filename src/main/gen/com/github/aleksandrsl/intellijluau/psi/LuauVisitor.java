// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;

public class LuauVisitor extends PsiElementVisitor {

  public void visitAndExpr(@NotNull LuauAndExpr o) {
    visitExpression(o);
  }

  public void visitAsExpr(@NotNull LuauAsExpr o) {
    visitExpression(o);
  }

  public void visitAssignmentStatement(@NotNull LuauAssignmentStatement o) {
    visitStatement(o);
  }

  public void visitAttribute(@NotNull LuauAttribute o) {
    visitElement(o);
  }

  public void visitAttributes(@NotNull LuauAttributes o) {
    visitElement(o);
  }

  public void visitBaseGenericTypeDeclaration(@NotNull LuauBaseGenericTypeDeclaration o) {
    visitNamedElement(o);
  }

  public void visitBinding(@NotNull LuauBinding o) {
    visitNamedElement(o);
  }

  public void visitBindingList(@NotNull LuauBindingList o) {
    visitElement(o);
  }

  public void visitBlock(@NotNull LuauBlock o) {
    visitElement(o);
  }

  public void visitBoundTypeList(@NotNull LuauBoundTypeList o) {
    visitElement(o);
  }

  public void visitClassicForStatement(@NotNull LuauClassicForStatement o) {
    visitStatement(o);
  }

  public void visitClosureExpr(@NotNull LuauClosureExpr o) {
    visitExpression(o);
  }

  public void visitComparisonExpr(@NotNull LuauComparisonExpr o) {
    visitExpression(o);
  }

  public void visitCompoundOpStatement(@NotNull LuauCompoundOpStatement o) {
    visitStatement(o);
  }

  public void visitComputedKey(@NotNull LuauComputedKey o) {
    visitElement(o);
  }

  public void visitConcatExpr(@NotNull LuauConcatExpr o) {
    visitExpression(o);
  }

  public void visitContinueSoftKeyword(@NotNull LuauContinueSoftKeyword o) {
    visitSoftKeyword(o);
  }

  public void visitDivExpr(@NotNull LuauDivExpr o) {
    visitExpression(o);
  }

  public void visitDoStatement(@NotNull LuauDoStatement o) {
    visitStatement(o);
  }

  public void visitExpExpr(@NotNull LuauExpExpr o) {
    visitExpression(o);
  }

  public void visitExpList(@NotNull LuauExpList o) {
    visitElement(o);
  }

  public void visitExportSoftKeyword(@NotNull LuauExportSoftKeyword o) {
    visitSoftKeyword(o);
  }

  public void visitExpression(@NotNull LuauExpression o) {
    visitElement(o);
  }

  public void visitExpressionStatement(@NotNull LuauExpressionStatement o) {
    visitStatement(o);
  }

  public void visitField(@NotNull LuauField o) {
    visitElement(o);
  }

  public void visitFloorDivExpr(@NotNull LuauFloorDivExpr o) {
    visitExpression(o);
  }

  public void visitForeachStatement(@NotNull LuauForeachStatement o) {
    visitStatement(o);
  }

  public void visitFuncArgs(@NotNull LuauFuncArgs o) {
    visitElement(o);
  }

  public void visitFuncBody(@NotNull LuauFuncBody o) {
    visitGenericDeclaration(o);
  }

  public void visitFuncCall(@NotNull LuauFuncCall o) {
    visitElement(o);
  }

  public void visitFuncDefStatement(@NotNull LuauFuncDefStatement o) {
    visitNamedElement(o);
    // visitStatement(o);
  }

  public void visitFuncTypeParams(@NotNull LuauFuncTypeParams o) {
    visitElement(o);
  }

  public void visitFunctionType(@NotNull LuauFunctionType o) {
    visitGenericDeclaration(o);
  }

  public void visitGenericTypeDeclaration(@NotNull LuauGenericTypeDeclaration o) {
    visitBaseGenericTypeDeclaration(o);
  }

  public void visitGenericTypeList(@NotNull LuauGenericTypeList o) {
    visitElement(o);
  }

  public void visitGenericTypeListWithDefaults(@NotNull LuauGenericTypeListWithDefaults o) {
    visitElement(o);
  }

  public void visitGenericTypePack(@NotNull LuauGenericTypePack o) {
    visitElement(o);
  }

  public void visitGenericTypePackParameter(@NotNull LuauGenericTypePackParameter o) {
    visitBaseGenericTypeDeclaration(o);
  }

  public void visitGenericTypePackParameterWithDefault(@NotNull LuauGenericTypePackParameterWithDefault o) {
    visitBaseGenericTypeDeclaration(o);
  }

  public void visitGenericTypeWithDefaultDeclaration(@NotNull LuauGenericTypeWithDefaultDeclaration o) {
    visitBaseGenericTypeDeclaration(o);
  }

  public void visitIfStatement(@NotNull LuauIfStatement o) {
    visitStatement(o);
  }

  public void visitIfelseExpr(@NotNull LuauIfelseExpr o) {
    visitExpression(o);
  }

  public void visitIndexAccess(@NotNull LuauIndexAccess o) {
    visitElement(o);
  }

  public void visitIndexedField(@NotNull LuauIndexedField o) {
    visitElement(o);
  }

  public void visitIntersectionType(@NotNull LuauIntersectionType o) {
    visitElement(o);
  }

  public void visitKeyedField(@NotNull LuauKeyedField o) {
    visitElement(o);
  }

  public void visitLastStatement(@NotNull LuauLastStatement o) {
    visitStatement(o);
  }

  public void visitLengthExpr(@NotNull LuauLengthExpr o) {
    visitExpression(o);
  }

  public void visitListArgs(@NotNull LuauListArgs o) {
    visitFuncArgs(o);
  }

  public void visitLiteralExpr(@NotNull LuauLiteralExpr o) {
    visitExpression(o);
  }

  public void visitLiteralField(@NotNull LuauLiteralField o) {
    visitElement(o);
  }

  public void visitLiteralTable(@NotNull LuauLiteralTable o) {
    visitElement(o);
  }

  public void visitLocalDefStatement(@NotNull LuauLocalDefStatement o) {
    visitStatement(o);
  }

  public void visitLocalFuncDefStatement(@NotNull LuauLocalFuncDefStatement o) {
    visitNamedElement(o);
    // visitStatement(o);
  }

  public void visitLvalue(@NotNull LuauLvalue o) {
    visitElement(o);
  }

  public void visitMethodDefStatement(@NotNull LuauMethodDefStatement o) {
    visitNamedElement(o);
    // visitStatement(o);
  }

  public void visitMethodName(@NotNull LuauMethodName o) {
    visitElement(o);
  }

  public void visitMinusExpr(@NotNull LuauMinusExpr o) {
    visitExpression(o);
  }

  public void visitModExpr(@NotNull LuauModExpr o) {
    visitExpression(o);
  }

  public void visitMulExpr(@NotNull LuauMulExpr o) {
    visitExpression(o);
  }

  public void visitNotExpr(@NotNull LuauNotExpr o) {
    visitExpression(o);
  }

  public void visitOperator(@NotNull LuauOperator o) {
    visitElement(o);
  }

  public void visitOrExpr(@NotNull LuauOrExpr o) {
    visitExpression(o);
  }

  public void visitParList(@NotNull LuauParList o) {
    visitElement(o);
  }

  public void visitParameters(@NotNull LuauParameters o) {
    visitElement(o);
  }

  public void visitParametrizedAttribute(@NotNull LuauParametrizedAttribute o) {
    visitElement(o);
  }

  public void visitParenExpr(@NotNull LuauParenExpr o) {
    visitExpression(o);
  }

  public void visitParenthesisedType(@NotNull LuauParenthesisedType o) {
    visitElement(o);
  }

  public void visitPlusExpr(@NotNull LuauPlusExpr o) {
    visitExpression(o);
  }

  public void visitPrimaryGroupExpr(@NotNull LuauPrimaryGroupExpr o) {
    visitExpression(o);
  }

  public void visitReadSoftKeyword(@NotNull LuauReadSoftKeyword o) {
    visitSoftKeyword(o);
  }

  public void visitRepeatStatement(@NotNull LuauRepeatStatement o) {
    visitStatement(o);
  }

  public void visitReturnStatement(@NotNull LuauReturnStatement o) {
    visitStatement(o);
  }

  public void visitReturnType(@NotNull LuauReturnType o) {
    visitElement(o);
  }

  public void visitRootBlock(@NotNull LuauRootBlock o) {
    visitElement(o);
  }

  public void visitShebangLine(@NotNull LuauShebangLine o) {
    visitElement(o);
  }

  public void visitSimpleReference(@NotNull LuauSimpleReference o) {
    visitElement(o);
  }

  public void visitSimpleTypeReference(@NotNull LuauSimpleTypeReference o) {
    visitElement(o);
  }

  public void visitSingleArg(@NotNull LuauSingleArg o) {
    visitFuncArgs(o);
  }

  public void visitSingletonType(@NotNull LuauSingletonType o) {
    visitElement(o);
  }

  public void visitStringKeyedField(@NotNull LuauStringKeyedField o) {
    visitElement(o);
  }

  public void visitTableConstructor(@NotNull LuauTableConstructor o) {
    visitElement(o);
  }

  public void visitTableConstructorExpr(@NotNull LuauTableConstructorExpr o) {
    visitExpression(o);
  }

  public void visitTableType(@NotNull LuauTableType o) {
    visitElement(o);
  }

  public void visitTemplateStringExpr(@NotNull LuauTemplateStringExpr o) {
    visitExpression(o);
  }

  public void visitType(@NotNull LuauType o) {
    visitTypeElement(o);
  }

  public void visitTypeComputedKey(@NotNull LuauTypeComputedKey o) {
    visitElement(o);
  }

  public void visitTypeDeclarationStatement(@NotNull LuauTypeDeclarationStatement o) {
    visitNamedElement(o);
    // visitGenericDeclaration(o);
    // visitStatement(o);
  }

  public void visitTypeField(@NotNull LuauTypeField o) {
    visitElement(o);
  }

  public void visitTypeFunctionDeclarationStatement(@NotNull LuauTypeFunctionDeclarationStatement o) {
    visitNamedElement(o);
    // visitStatement(o);
  }

  public void visitTypeKeyedField(@NotNull LuauTypeKeyedField o) {
    visitElement(o);
  }

  public void visitTypeList(@NotNull LuauTypeList o) {
    visitElement(o);
  }

  public void visitTypePack(@NotNull LuauTypePack o) {
    visitElement(o);
  }

  public void visitTypeParams(@NotNull LuauTypeParams o) {
    visitElement(o);
  }

  public void visitTypeReference(@NotNull LuauTypeReference o) {
    visitElement(o);
  }

  public void visitTypeSoftKeyword(@NotNull LuauTypeSoftKeyword o) {
    visitSoftKeyword(o);
  }

  public void visitTypeStringKeyedField(@NotNull LuauTypeStringKeyedField o) {
    visitElement(o);
  }

  public void visitTypeofSoftKeyword(@NotNull LuauTypeofSoftKeyword o) {
    visitSoftKeyword(o);
  }

  public void visitTypeofType(@NotNull LuauTypeofType o) {
    visitElement(o);
  }

  public void visitUnaryMinExpr(@NotNull LuauUnaryMinExpr o) {
    visitExpression(o);
  }

  public void visitUnionType(@NotNull LuauUnionType o) {
    visitElement(o);
  }

  public void visitVarList(@NotNull LuauVarList o) {
    visitElement(o);
  }

  public void visitVariadicTypePack(@NotNull LuauVariadicTypePack o) {
    visitElement(o);
  }

  public void visitWhileStatement(@NotNull LuauWhileStatement o) {
    visitStatement(o);
  }

  public void visitWriteSoftKeyword(@NotNull LuauWriteSoftKeyword o) {
    visitSoftKeyword(o);
  }

  public void visitTypeElement(@NotNull LuauTypeElement o) {
    visitElement(o);
  }

  public void visitGenericDeclaration(@NotNull LuauGenericDeclaration o) {
    visitElement(o);
  }

  public void visitNamedElement(@NotNull LuauNamedElement o) {
    visitElement(o);
  }

  public void visitSoftKeyword(@NotNull LuauSoftKeyword o) {
    visitElement(o);
  }

  public void visitStatement(@NotNull LuauStatement o) {
    visitElement(o);
  }

  public void visitElement(@NotNull LuauElement o) {
    super.visitElement(o);
  }

}
