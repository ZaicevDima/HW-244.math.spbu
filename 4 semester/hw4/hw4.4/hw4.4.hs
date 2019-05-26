import Data.List

data Monom = Monom (Integer, Integer)

instance Show Monom where
    show(Monom(0, _)) = ""
    show(Monom(coef, 0)) = (show coef)
    show(Monom(coef, 1)) = (show coef) ++ "x"
    show(Monom(1, degree)) = "x^" ++ (show degree)
    show(Monom(coef, degree)) = (show coef) ++ "x^" ++ (show degree)

multiplyMonoms (Monom(coef1, pow1)) (Monom(coef2, pow2)) = Monom ((coef1 * coef2), (pow1 + pow2))

data Polynom = Polynom [Monom]

instance Show Polynom where
    show(Polynom[]) = "0"
    show(Polynom[x]) = (show x)
    show(Polynom(x:xs)) = (show x) ++ "+" ++ (show (Polynom xs))

multiplyPolynoms (Polynom polynom1) (Polynom polynom2) = simplify (Polynom [multiplyMonoms x y | x <- polynom1, y <- polynom2])

sumPolynoms (Polynom polynom1) (Polynom polynom2) = simplify (Polynom(polynom1 ++ polynom2))

simplify (Polynom polynom) = Polynom (simplify' polynom) where
    simplify' [] = []
    simplify' [x] = [x]
    simplify' polynom = map sumPolynoms' groupByPow where 
        sumPolynoms' ((Monom (coef, pow)):xs) = (Monom (coef + sum (map getCoef xs), pow))
        getCoef (Monom (coef, pow)) = coef
        groupByPow = groupBy (\(Monom (coef1, pow1)) -> \(Monom (coef2, pow2)) -> (pow1 == pow2)) sort
        sort = sortOn (\(Monom (coef,pow)) -> pow) polynom        

