function foo()<fold text='...'>
    local x = 1

    if x == 10 then<fold text='...'>
        local a = 123
        print(123)
        return</fold>
    else<fold text='...'>
        return</fold>
    end

    repeat<fold text='...'>
        local a = 123</fold>
    until x == 1</fold>
end
