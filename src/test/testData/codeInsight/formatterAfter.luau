local inlineEmmy = { name = "Emmy", age = 7, longLongProperty = 123 }
local emmy = {
	name = "Emmy",
	age = 7,
	longLongProperty = 123
}
local a = 1
local b, c = 2, "3"
local var = 1 -- a short comment
--- doc comment
--- @param par1 Par1Type @some strings
function var:testFun(par1, par2, par3, par4, par5)
	print('hello')
	return self.len + 2
end

var:testFun('par1Value', 'par2Value', 'par3Value', 'par4Value', 'par5Value')

function globalFun()
	return "string" .. var
end

type test = {
	a: number
}

local test = if a > 5 then 10 else 20

local padding = if cursorDetails.Padding
	then React.createElement("UIPadding", {
		PaddingTop = UDim.new(0, cursorDetails.Padding),
		PaddingBottom = UDim.new(0, cursorDetails.Padding),
		PaddingLeft = UDim.new(0, cursorDetails.Padding),
		PaddingRight = UDim.new(0, cursorDetails.Padding),
	})
	else nil

local function f(
	a: number,
	b: string,
	c: string,
	d: string,
	e: string
)
	return a
end

if true then
	return 123
end

if true then
	print("123") return 123
end
