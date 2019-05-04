module SearchMaxIndex where

searchMaxI :: [Int] -> Int
searchMaxI l = searchMaxI' (sumLists l) (-1) (-1) 1 where
    sumLists (x:xs) = zipWith (+) (xs ++ [0]) (0:x:xs)
    searchMaxI' [] _ maxI _ = maxI 
    searchMaxI' (x:xs) _ _ 1 = searchMaxI' xs x 0 2
    searchMaxI' (x:xs) max maxI i = if (x > max) then searchMaxI' xs x i (i + 1) else searchMaxI' xs max maxI (i + 1) 

