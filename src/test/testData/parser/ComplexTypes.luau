export type Constructor<T...> = (T...) -> Types.Table

-- Empty generic list is fine, because the T above is a type pack
local defaultConstructor: Constructor<> = function()
	return {}
end


function test2(a: number, ...: boolean) end
-- It's not ok in types, you should do ...boolean
type Test2 = (a: number, ...: boolean) -> never
