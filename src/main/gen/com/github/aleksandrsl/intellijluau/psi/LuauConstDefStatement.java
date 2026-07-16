// This is a generated file. Not intended for manual editing.
package com.github.aleksandrsl.intellijluau.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LuauConstDefStatement extends LuauStatement {

  @NotNull
  LuauBindingList getBindingList();

  @NotNull
  LuauConstSoftKeyword getConstSoftKeyword();

  @Nullable
  LuauExpList getExpList();

}
