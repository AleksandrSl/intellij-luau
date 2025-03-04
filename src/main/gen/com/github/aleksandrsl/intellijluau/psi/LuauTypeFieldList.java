// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauTypeFieldList extends PsiElement {

  @NotNull
  List<LuauFieldSep> getFieldSepList();

  @NotNull
  List<LuauTypeField> getTypeFieldList();

}
