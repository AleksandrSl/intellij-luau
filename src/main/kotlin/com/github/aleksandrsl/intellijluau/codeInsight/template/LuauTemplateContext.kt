package com.github.aleksandrsl.intellijluau.codeInsight.template

import com.github.aleksandrsl.intellijluau.LuauFileType
import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.openapi.fileTypes.FileTypeRegistry

class LuauTemplateContext : TemplateContextType("Luau") {
    // TODO (AleksandrSl 23/06/2025): Maybe it's worth to check for language injections as well, e.g. in markdown.
    override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
        return FileTypeRegistry.getInstance()
            .getFileTypeByFileName(templateActionContext.file.name) == LuauFileType
    }
}
