-- Once defaults started you can only have defaults.
type A<B, C=U, D...=...E> = U
type A<C=U, D...=...E> = U
type A<C=U, C1=U, D...=...E, D1...=...E1> = U
type A<B, D..., D1...=...E1> = U
type A<D..., T...=...E2> = U
type A<D..., D1..., D2...=...E2> = U
type A<D...=...E, D1...=...E1> = U
