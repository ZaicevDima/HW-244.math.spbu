module SumDigits where

sumDigits :: Int -> Int
sumDigits number = sumDigits' (abs number) 0 where
    sumDigits' 0 sum = sum
    sumDigits' number sum = sumDigits' (div number 10) (sum + mod number 10) 
