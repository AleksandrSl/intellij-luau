-- This is a syntax error because unions can't be mixed with intersections
type T14 = A<| (T8 | T9) & T6>
