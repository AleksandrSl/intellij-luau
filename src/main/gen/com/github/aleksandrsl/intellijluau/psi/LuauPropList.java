// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauPropList extends PsiElement {

  @NotNull
  List<LuauFieldSep> getFieldSepList();

  @NotNull
  List<LuauTableIndexer> getTableIndexerList();

  @NotNull
  List<LuauTableProp> getTablePropList();

}
