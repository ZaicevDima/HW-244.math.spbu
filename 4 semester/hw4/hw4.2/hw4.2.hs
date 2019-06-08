import System.IO

main :: IO ()
main = do
    putStrLn (show (simplify (takeDerivative (X :^: 1.5))))
    putStrLn (show (simplify (takeDerivative (Negative (X :^: 2.0)))))
    putStrLn (show (simplify (takeDerivative (X :^: 1.5:*: Const 2.6))))
    putStrLn (show (simplify (takeDerivative (X :^: 1.5 :*: X :*: Const 0.0))))
    putStrLn (show (simplify (takeDerivative (X :/: (X :+: Const 1.0)))))
    putStrLn (show (simplify (takeDerivative (X :/: Const 0.0))))

data Expression = X | Const Float | Negative Expression | Expression :+: Expression | Expression :-: Expression | Expression :*: Expression | Expression :/: Expression | Expression :^: Float

instance Show Expression where
    show (X) = "x"
    show (Const const) = show const
    show (Negative operand) = "-" ++ show operand
    show (operand1 :+: operand2) = "(" ++ show operand1 ++ " + " ++ show operand2 ++ ")"
    show (operand1 :-: operand2) = "(" ++ show operand1 ++ " - " ++ show operand2 ++ ")"
    show (operand1 :/: operand2) = "(" ++ show operand1 ++ " / " ++ show operand2 ++ ")"
    show (operand1 :*: operand2) = "(" ++ show operand1 ++ " * " ++ show operand2 ++ ")"
    show (operand :^: pow) = show operand ++ "^" ++ show pow

takeDerivative :: Expression -> Expression
takeDerivative expression = case expression of
    operand :^: pow -> Const pow :*: (operand  :^: (pow - 1.0)) :*: (takeDerivative operand)
    operand1 :+: operand2 -> takeDerivative operand1 :+: takeDerivative operand2
    operand1 :-: operand2 -> takeDerivative operand1 :-: takeDerivative operand2
    operand1 :*: operand2 -> (takeDerivative operand1 :*: operand2) :+: ( operand1 :*: takeDerivative operand2) 
    operand1 :/: operand2 -> ((takeDerivative operand1 :*: operand2) :-: ( operand1 :*: takeDerivative operand2)) :/: (operand2 :^: 2.0)
    Negative operand -> Negative (takeDerivative operand)
    X -> Const 1.0
    Const const -> Const 0.0 
    
simplify :: Expression -> Expression
simplify expression = case expression of
    expression1 :+: expression2 -> simplify'(simplify expression1 :+: simplify expression2)
    expression1 :-: expression2 -> simplify'(simplify expression1 :-: simplify expression2)
    expression1 :*: expression2 -> simplify'(simplify expression1 :*: simplify expression2)
    expression1 :/: expression2 -> simplify'(simplify expression1 :/: simplify expression2)
    expression' :^: pow ->  simplify' (simplify expression' :^: pow)
    Negative expression' -> simplify' (Negative (simplify expression'))
    _ -> expression

simplify' :: Expression -> Expression
simplify' expression = case expression of
    Negative (Negative expression') -> simplify' expression'
    
    Const 0.0 :+: expression' -> simplify' expression'
    expression' :+: Const 0.0 -> simplify' expression'
    
    Const 0.0 :-: expression' -> Negative (simplify' expression')
    expression' :-: Const 0.0 -> simplify' expression'

    Const 0.0 :*: expression' -> Const 0.0
    expression' :*: Const 0.0 -> Const 0.0

    Const 1.0 :*: expression' -> expression'
    expression' :*: Const 1.0 -> simplify' expression'
    
    expression' :/: Const 0.0 -> error "Division by zero undefined"
    
    expression' :/: Const 1.0 -> simplify' expression'
    Const 0.0 :/: expression' -> Const 0.0    

    expression' :^: 0.0 -> Const 1.0
    expression' :^: 1.0 -> simplify' expression'

    Const a :+: Const b -> Const (a + b)
    Const a :-: Const b -> Const (a - b)
    Const a :*: Const b -> Const (a * b)
    Const a :/: Const b -> Const (a / b)
    Const a :^: pow -> Const (a ** pow)   
    _ -> expression
