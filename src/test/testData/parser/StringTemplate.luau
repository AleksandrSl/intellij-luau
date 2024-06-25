a = `asdasd {123}`
a = `{123}`
a = `asdasd`
a = `{b} {c}`
a = `{b} asdasd {c}`
a = `{b} {c} asdasd`
a = `{""} {c} asdasd`
a = `{"" .. '' .. "123"} {c} asdasd`
a = `{"1" .. `{"2"}`} {c} asdasd`
-- Empty interpolations are not valid
a = `{}`
