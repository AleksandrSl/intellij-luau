a = `asdasd {123}`
a = `{123}`
a = `asdasd`
a = `{b} {c}`
a = `{b} asdasd {c}`
a = `{b} {c} asdasd`
a = `{""} {c} asdasd`
a = `{"" .. '' .. "123"} {c} asdasd`
a = `{"1" .. `{"2"}`} {c} asdasd`
-- Table with a value
a = `{ {123}}`
a = `4{(function(a) end)({{890}})}`
