export type Constructor<T...> = (T...) -> Types.Table

type ValueFactory<T, U, Args2..., Args...> = (Args...) -> T

-- Empty generic list is fine, because the T above is a type pack
local defaultConstructor: Constructor<> = function()
	return {}
end

function test2(a: number, ...: boolean) end

-- Function as a parameter in generic
local a = React.createContext(function() end :: (string) -> string) :: React.Context<(string) -> string>

-- Valid according to the grammar but I'm yet to find a real case where it's not a type error
type T7 = A<string..., (...() -> (string, string) | nil)>
type T8 = A<string..., ...number, (...() -> (string, string) | nil)>
