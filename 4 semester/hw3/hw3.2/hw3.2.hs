module KnottingMethod where

numbers :: [Integer]
numbers = 1:7:9:(concat(map (\x -> [x * 10 + 1, x * 10 + 7, x * 10 + 9]) numbers)) 
