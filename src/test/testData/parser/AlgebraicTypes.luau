type A = number | string

type B = number & string

type C = & (number | string)

type D = | (number & string)

type E = number? | string

type T10 = A<(T8 | T9) & T6>
type T11 = A<T8 | (T9 & T6)>
type T12 = A<(T8 & T9) & T6>
type T13 = A<(T8 & T9) | T6>

type T14 = A<& (T8 | T9) & T6>
type T15 = A<| T8 | (T9 & T6)>
type T16 = A<(T8 & T9) & T6>
type T17 = A<| (& T8 & T9) | T6>

type T18 = A<
 & (T8 | T9)
 & T6
>
