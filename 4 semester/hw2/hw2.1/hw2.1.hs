module Reverse where

reverse :: [Int] -> [Int]
reverse list = reverse' [] list where
    reverse' list [] = list
    reverse' list (x : xs) = reverse' (x : list) xs
