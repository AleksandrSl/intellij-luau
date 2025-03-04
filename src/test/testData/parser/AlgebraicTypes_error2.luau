-- This is a syntax error because unions can't be mixed with intersections, and type? is a union of type | nil
type C = number? & string
