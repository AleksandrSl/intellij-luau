-- There is no syntax error in luau for this, but it gives type error. The main thing is that variadic type pack should be the final type, and nothing can go after.
type T = A<(string, ...string, ...number)>
