module Fibonacci where

fibonacciIterative :: Integer -> Integer
fibonacciIterative n 
	| n < 0 = error "argument must be >= 0"
	| otherwise = search 1 1 n 
  	where
  		search previous current n 
			| (n == 0) || (n == 1) = current 
			| otherwise = search current (current + previous) (n - 1)

fibonacciRecursive :: Integer -> Integer
fibonacciRecursive n 
	| n < 0 = error "argument must be >= 0"
	| (n == 0) || (n == 1) = 1 
	| otherwise = fibonacciRecursive (n - 1) + fibonacciRecursive (n - 2) 

