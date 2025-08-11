// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauRootBlock extends LuauElement {

  @NotNull
  List<LuauAssignmentStatement> getAssignmentStatementList();

  @NotNull
  List<LuauClassicForStatement> getClassicForStatementList();

  @NotNull
  List<LuauCompoundOpStatement> getCompoundOpStatementList();

  @NotNull
  List<LuauDoStatement> getDoStatementList();

  @NotNull
  List<LuauExpressionStatement> getExpressionStatementList();

  @NotNull
  List<LuauForeachStatement> getForeachStatementList();

  @NotNull
  List<LuauFuncDefStatement> getFuncDefStatementList();

  @NotNull
  List<LuauIfStatement> getIfStatementList();

  @NotNull
  List<LuauLocalDefStatement> getLocalDefStatementList();

  @NotNull
  List<LuauLocalFuncDefStatement> getLocalFuncDefStatementList();

  @NotNull
  List<LuauMethodDefStatement> getMethodDefStatementList();

  @NotNull
  List<LuauRepeatStatement> getRepeatStatementList();

  @Nullable
  LuauReturnStatement getReturnStatement();

  @NotNull
  List<LuauTypeDeclarationStatement> getTypeDeclarationStatementList();

  @NotNull
  List<LuauTypeFunctionDeclarationStatement> getTypeFunctionDeclarationStatementList();

  @NotNull
  List<LuauWhileStatement> getWhileStatementList();

}
