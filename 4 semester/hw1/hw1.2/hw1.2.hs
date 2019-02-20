module Fibonacci where

fibonacciSearch :: Integer -> Integer -> Integer -> Integer
fibonacciSearch previous current 0 = current
fibonacciSearch previous current 1 = current
fibonacciSearch previous current n = fibonacciSearch current (current + previous) (n - 1)

fibonacciIterative :: Integer -> Integer
fibonacciIterative n = if n >= 0 then fibonacciSearch 1 1 n else error "argument must be >= 0"

fibonacciRecursive :: Integer -> Integer
fibonacciRecursive 0 = 1
fibonacciRecursive 1 = 1
fibonacciRecursive n = if n >= 0 then fibonacciRecursive (n - 1) + fibonacciRecursive (n - 2) else error "argument must >= 0"	

