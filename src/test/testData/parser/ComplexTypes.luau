export type Constructor<T...> = (T...) -> Types.Table

type ValueFactory<T, U, Args2..., Args...> = (Args...) -> T

-- Empty generic list is fine, because the T above is a type pack
local defaultConstructor: Constructor<> = function()
	return {}
end

function test2(a: number, ...: boolean) end
