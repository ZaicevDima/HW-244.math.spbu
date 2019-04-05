--1
numbers :: [Integer]
numbers = zipWith (*) listOfSigns [1,2..] where
    listOfSigns = 1:(-1):listOfSigns

--2

matrix :: Int -> [[Int]]
matrix n = (map (\x -> map' x) [1..n]) where
    map' x = map (\y -> (if y > x then y else x)) [1..n]

--3
drawRhombus :: Int -> IO ()
drawRhombus n = putStrLn (rhombus n) where
    rhombus n = concatMap (\x -> drawLevel x n) [0..2 * (n - 1)] where
        drawLevel i n 
            | i < n = replicate (n - i - 1) ' ' ++ replicate (2 * i + 1) 'x' ++ ['\n'] 
            | otherwise = replicate (i - (n - 1)) ' ' ++ replicate (2 * (2 * (n - 1) - i) + 1) 'x' ++ ['\n']

--4
supermap :: [a] -> (a ->[b]) -> [b]
supermap [] function = []
supermap (x:xs) function = function x ++ supermap xs function

--5
checkList :: (a -> Bool) -> [a] -> Bool
checkList function [] = True
checkList function (x:xs)
    | function x = checkList function xs 
    | otherwise = False
