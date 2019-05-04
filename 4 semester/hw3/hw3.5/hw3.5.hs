module Fibonacci where

fibonacciIterative :: Integer -> Integer
fibonacciIterative n = search 1 1 n where
    search previous current n 
        | (n == 0) || (n == 1) = current
        | (n > 0) = search current (current + previous) (n - 1)
        | otherwise = search (current - previous) previous (n + 1)
