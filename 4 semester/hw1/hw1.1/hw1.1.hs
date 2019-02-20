module Factorial where

factorial :: Integer -> Integer
factorial 0 = 1
factorial n = if n >= 0 then n * factorial (n - 1) else error "argument must be >= 0"
