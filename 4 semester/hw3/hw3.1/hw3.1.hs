module PointFree where

{--
original function:
func x l = map (\y -> y*x) l

reducion l:
func x = map (\y -> y*x)   

reduction lambda
func x = map (*x)   

reduction x:
func  = map . (*) 
--}
func :: Integer -> [Integer] -> [Integer]
func = map . (*)  
