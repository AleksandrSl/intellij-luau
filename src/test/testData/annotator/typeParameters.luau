<info textAttributesKey="LUAU_KEYWORD">type</info> <info textAttributesKey="LUAU_TYPE">A</info><<info textAttributesKey="LUAU_TYPE_PARAMETER">B</info>> = <info textAttributesKey="LUAU_TYPE">C</info><<info textAttributesKey="LUAU_TYPE_PARAMETER">B</info><>>

function test<<info textAttributesKey="LUAU_TYPE_PARAMETER">T</info>>(a: <info textAttributesKey="LUAU_TYPE_PARAMETER">T</info>, b: <info textAttributesKey="LUAU_TYPE">number</info>): (<info textAttributesKey="LUAU_TYPE_PARAMETER">T</info>, <info textAttributesKey="LUAU_TYPE">number</info>)
    <info textAttributesKey="LUAU_KEYWORD">type</info> <info textAttributesKey="LUAU_TYPE">A</info> = <info textAttributesKey="LUAU_TYPE_PARAMETER">T</info>

    local t: <info textAttributesKey="LUAU_TYPE_PARAMETER">T</info> = nil
    local c: <info textAttributesKey="LUAU_TYPE">A</info> = a
end

local t: <info textAttributesKey="LUAU_TYPE">T</info> = nil

-- type render<Q, Container = Instance, BaseElement = Container> = typesModule.render<Q, Container, BaseElement>
<info textAttributesKey="LUAU_KEYWORD">type</info> <info textAttributesKey="LUAU_TYPE">render</info><<info textAttributesKey="LUAU_TYPE_PARAMETER">Q</info>, <info textAttributesKey="LUAU_TYPE_PARAMETER">Container</info> = <info textAttributesKey="LUAU_TYPE">Instance</info>, <info textAttributesKey="LUAU_TYPE_PARAMETER">BaseElement</info> = <info textAttributesKey="LUAU_TYPE_PARAMETER">Container</info>> = typesModule.<info textAttributesKey="LUAU_TYPE">render</info><<info textAttributesKey="LUAU_TYPE_PARAMETER">Q</info>, <info textAttributesKey="LUAU_TYPE_PARAMETER">Container</info>, <info textAttributesKey="LUAU_TYPE_PARAMETER">BaseElement</info>>
-- Should highlight type pack, and shouldn't color types with the same names as in declaration above as type parameters because they are in a different scope
<info textAttributesKey="LUAU_KEYWORD">type</info> <info textAttributesKey="LUAU_TYPE">render</info><<info textAttributesKey="LUAU_TYPE_PARAMETER">Q</info>, <info textAttributesKey="LUAU_TYPE_PARAMETER">T</info>...> = typesModule.<info textAttributesKey="LUAU_TYPE">render</info><<info textAttributesKey="LUAU_TYPE_PARAMETER">T</info>, <info textAttributesKey="LUAU_TYPE">Container</info>, <info textAttributesKey="LUAU_TYPE">BaseElement</info>>
