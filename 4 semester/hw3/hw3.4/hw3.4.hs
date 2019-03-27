module CorrectBracketSequence where

checkCBS string = checkCBS' string "" where
    checkCBS' "" "" = True
    checkCBS' (x:xs) "" 
        | isOpenBracket x = checkCBS' xs ([x])
        | isCloseBracket x = False
        | otherwise = checkCBS' xs ("")

    checkCBS' "" (y:yx)  
        | isOpenBracket y = False
        | otherwise = checkCBS' "" yx

    checkCBS' (x:xs) (y:ys) 
        | isOpenBracket x = checkCBS' xs (x : (y:ys))
        | isCloseBracket x = if (isSameType y x) then checkCBS' xs ys else False
        | otherwise = checkCBS' xs (y:ys)

    isOpenBracket x = if (x == '(' || x == '{' || x == '[') then True else False
    isCloseBracket x = if (x == ')' || x == '}' || x == ']') then True else False
    isBracket x = isOpenBracket x || isCloseBracket x
    isSameType x y = elem(x, y) [('(', ')'), ('{', '}'), ('[', ']')]

