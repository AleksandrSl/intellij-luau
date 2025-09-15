function bar(outerArg1, outerArg2)
end

local a = {}

function a:method(methodArg1, methodArg2)
    local function baz(localFuncArg1, localFuncArg2)
        function foo(funcArg1, funcArg2)
            local closure = function(closureArg1, closureArg2)
                <caret>
            end
        end
    end
end

