-- Taken directly from luau parsing tests
type A = {read x: number}
type B = {write x: number}
type C = {read x: number, write x: number}
type D = {read: () -> string}
type E = {write: (string) -> ()}
type F = {read read: () -> string}
type G = {read write: (string) -> ()}

type H = {read ["A"]: number}
type I = {write ["A"]: string}

type J = {read [number]: number}
type K = {write [number]: string}
