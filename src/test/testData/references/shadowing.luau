-- b is shadowed by a function declaration and should resolve to it and not to the variable
local b = 10

local function b() end

local function a()
	<caret>b()
end
