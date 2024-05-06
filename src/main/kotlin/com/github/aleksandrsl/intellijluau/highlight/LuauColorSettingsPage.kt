package com.github.aleksandrsl.intellijluau.highlight

import com.github.aleksandrsl.intellijluau.LuauIcons
import com.github.aleksandrsl.intellijluau.psi.LuauFile
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class LuauColorSettingsPage : ColorSettingsPage {
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS

    override fun getColorDescriptors(): Array<ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String = "Luau"


    override fun getIcon(): Icon = LuauIcons.FILE

    override fun getHighlighter(): SyntaxHighlighter = LuauSyntaxHighlighter()

    override fun getDemoText(): String = """
    local function printClicked()
        print("Clicked")
    end
    
    local ButtonStory = {
        controls = {
            isDisabled = false,
        },
        stories = {
            -- The default Studio button
            Round = function(props)
                return ContextServices.provide({ theme }, {
                    Child = Roact.createElement(Button, {
                        Style = "Round",
                        Text = "Cancel",
                        StyleModifier = if props.controls.isDisabled then StyleModifier.Disabled else nil,
                        Size = UDim2.fromOffset(120, 32),
                        LayoutOrder = 2,
                        OnClick = printClicked,
                    }, {
                        -- Adding a HoverArea as a child allows the cursor to change
                        Roact.createElement(HoverArea, { Cursor = "PointingHand" }),
                    }),
                })
            end
        },
        a: 123
    }    
    """.trimIndent()

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? = null

    companion object {
        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor("Identifier", LuauSyntaxHighlighter.IDENTIFIER),
            AttributesDescriptor("Keyword", LuauSyntaxHighlighter.KEYWORD),
            AttributesDescriptor("Number", LuauSyntaxHighlighter.NUMBER),
            AttributesDescriptor("String", LuauSyntaxHighlighter.STRING),
            AttributesDescriptor("Comment", LuauSyntaxHighlighter.COMMENT),
            AttributesDescriptor("Operator", LuauSyntaxHighlighter.OPERATOR),
            AttributesDescriptor("Bad value", LuauSyntaxHighlighter.BAD_CHARACTER)
        )
    }
}
