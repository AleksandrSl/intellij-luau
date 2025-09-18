package com.github.aleksandrsl.intellijluau.template

import com.github.aleksandrsl.intellijluau.LuauFileType
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.ide.fileTemplates.DefaultCreateFromTemplateHandler
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement

class LuauCreateFromTemplateHandler : DefaultCreateFromTemplateHandler() {
    override fun createFromTemplate(
        project: Project,
        directory: PsiDirectory,
        fileName: String?,
        template: FileTemplate,
        templateText: String,
        props: Map<String?, Any?>
    ): PsiElement {
        if (!template.isTemplateOfType(LuauFileType)) {
            return super.createFromTemplate(project, directory, fileName, template, templateText, props)
        }
        // I want a different extension based on the settings (Legacy projects may use .lua extension).
        // Having a separate template is weird given that I may have different templates, but the content is the same. So the best way is to use the correct extension.
        // I tried cloning a template and setting the correct extension for it, but it doesn't work, since the value is coming from an internal object I don't have access to.
        // Inside the checkAppendExtension I don't have access to the Project. Let's do it manually before the actual call, then.
        val extension = if (ProjectSettingsState.getInstance(project).useLuauExtension) "luau" else "lua"
        val newFileName = if (fileName == null) fileName else {
            if (fileName.endsWith(".$extension")) fileName else "$fileName.$extension"
        }

        return super.createFromTemplate(project, directory, newFileName, template, templateText, props)
    }

    // See comment in createFromTemplate
    override fun checkAppendExtension(fileName: String?, template: FileTemplate): String? {
        if (!template.isTemplateOfType(LuauFileType)) {
            return super.checkAppendExtension(fileName, template)
        }
        return fileName
    }
}
