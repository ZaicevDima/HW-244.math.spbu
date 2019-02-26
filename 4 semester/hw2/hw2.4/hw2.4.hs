module SearchFirstEntry where

searchFistEntry :: [Int] -> Int -> Int
searchFistEntry list value = searchFistEntry' list value 0 where
  searchFistEntry' (x:xs) value counter 
    | x == value = counter
    | length (x:xs) == 1 = error "not found"
    | otherwise =  searchFistEntry' xs value (counter + 1)
