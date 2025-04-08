package com.github.aleksandrsl.intellijluau.highlight

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.LuauIcons
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
    <KEYWORD>type</KEYWORD> A = number
    
    <ATTRIBUTE>@[deprecated</ATTRIBUTE> { parameter = "test" }<ATTRIBUTE>]</ATTRIBUTE>
    local function printClicked()
        <STDLIB>print</STDLIB>("Clicked")
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
                        Size = <STDLIB>UDim2.fromOffset</STDLIB>(120, 32),
                        LayoutOrder = 2,
                        OnClick = printClicked,
                    }, {
                        -- Adding a HoverArea as a child allows the cursor to change
                        Roact.createElement(HoverArea, { Cursor = "PointingHand" }),
                    }),
                })
            end
        }
    }    
    """.trimIndent()

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey> =
        TAG_TO_DESCRIPTOR_MAP

    companion object {
        private val TAG_TO_DESCRIPTOR_MAP = mutableMapOf(
            "ATTRIBUTE" to LuauSyntaxHighlighter.ATTRIBUTE,
            "KEYWORD" to LuauSyntaxHighlighter.KEYWORD,
            "STDLIB" to LuauSyntaxHighlighter.STDLIB,
        )

        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor(
                LuauBundle.messagePointer("luau.settings.color.identifier"),
                LuauSyntaxHighlighter.IDENTIFIER
            ),
            AttributesDescriptor(
                LuauBundle.messagePointer("luau.settings.color.keyword"),
                LuauSyntaxHighlighter.KEYWORD
            ),
            AttributesDescriptor(LuauBundle.messagePointer("luau.settings.color.number"), LuauSyntaxHighlighter.NUMBER),
            AttributesDescriptor(LuauBundle.messagePointer("luau.settings.color.string"), LuauSyntaxHighlighter.STRING),
            AttributesDescriptor(
                LuauBundle.messagePointer("luau.settings.color.comment"),
                LuauSyntaxHighlighter.COMMENT
            ),
            AttributesDescriptor(
                LuauBundle.messagePointer("luau.settings.color.operator"),
                LuauSyntaxHighlighter.OPERATOR
            ),
            AttributesDescriptor(
                LuauBundle.messagePointer("luau.settings.color.bad"),
                LuauSyntaxHighlighter.BAD_CHARACTER
            ),
            AttributesDescriptor(
                LuauBundle.messagePointer("luau.settings.color.stdlib"),
                LuauSyntaxHighlighter.STDLIB
            ),
            AttributesDescriptor(
                LuauBundle.messagePointer("luau.settings.color.attribute"),
                LuauSyntaxHighlighter.ATTRIBUTE
            ),
            AttributesDescriptor(
                LuauBundle.messagePointer("luau.settings.color.types.type"),
                LuauSyntaxHighlighter.TYPE
            ),
            AttributesDescriptor(
                LuauBundle.messagePointer("luau.settings.color.types.type.parameter"),
                LuauSyntaxHighlighter.TYPE_PARAMETER
            )
        )
    }
}
