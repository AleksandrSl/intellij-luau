@native
local function f(t)
end

@native
@checked
local function f(t)
end

@native@checked
local function f(t)
end

@native
function f(t)
end

@native
function test:f(t)
end

local a = f(@native function() end)
