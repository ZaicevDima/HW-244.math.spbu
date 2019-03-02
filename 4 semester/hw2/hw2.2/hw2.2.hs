module ListOfDegrees where

listOfDegrees :: Int -> [Int]
listOfDegrees n = take n (map (2^) [0..])
