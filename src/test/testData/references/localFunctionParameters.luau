local a = 10
-- Yes, you can shadow a function with a parameter
local function a(a: number, b: number)
    return <caret>a
end
