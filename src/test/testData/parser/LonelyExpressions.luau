local ok, width = pcall(function()
	-- Function calls are fine as statements
	a()
end)

a()
a:test():test(1)
a:test()[1]()
