module SumOfLists where

sumLists [] [] [] = []
sumLists list1 list2 list3 = ((head' list1) + (head' list2) + (head' list3) : sumLists (tail' list1) (tail' list2) (tail' list3)) where
    head' [] = 0
    head' list = head list
    tail' [] = []
    tail' list = tail list
