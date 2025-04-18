type function foo()
    return types.number
end

export type function bar()
    return types.string
end

local v1 = 1
type function foo()
    local v2 = 2
    local function bar()
        v2 += 1
        type function inner() end
        v2 += 2
    end
    local function bar2()
        v2 += 3
    end
end
local function bar() v1 += 1 end
