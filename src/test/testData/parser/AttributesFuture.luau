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

@[deprecated {"my reason", use = "bar()", 8, {}}]
local function f(t)
end

@[deprecated 'test']
local function f(t)
end

@[deprecated ('test', { 9, 10 })]
local function f(t)
end

@[deprecated {use = "bar()"}, native]
local function f(t)
end
