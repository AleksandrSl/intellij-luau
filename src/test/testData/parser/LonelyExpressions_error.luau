local ok, width = pcall(function()
	-- Function calls a fine
	a()
	-- this is not
	TextService:GetTextBoundsAsync(params).X
end)
