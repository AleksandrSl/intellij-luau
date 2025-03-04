@deprecated
local function f(t)
end

@[deprecated {reason = "my reason"}]
local function f(t)
end

@[deprecated {use = "bar()"}]
local function f(t)
end

@[deprecated {reason = "my reason", use = "bar()"}]
local function f(t)
end
