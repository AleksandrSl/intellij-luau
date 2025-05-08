print(`Some example escaping the braces \{like so}`)
--> Some example escaping the braces {like so}

print(`Backslash \ that escapes the space is not a part of the string...`)
--> Backslash  that escapes the space is not a part of the string...

print(`Backslash \\ will escape the second backslash...`)
--> Backslash \ will escape the second backslash...

print(`Some text that also includes \`...`)
--> Some text that also includes `...

local name = "Luau"

print(`Welcome to {
    name
}!`)
--> Welcome to Luau!

print(`Test\
\
`)
