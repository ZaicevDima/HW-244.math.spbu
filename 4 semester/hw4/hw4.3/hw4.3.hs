import System.IO
import Data.Char
import Data.List.Split

main = do
    hSetBuffering stdin LineBuffering
    doRequest[]

doRequest book = do
    textMenu
    
    command <- getLine
    case (words command) of
        "0":_ -> return ()
        "1":_ -> do
            putStrLn "Enter name:"
            name <- getLine
            putStrLn "Enter phone number:"
            number <- getLine
            doRequest ((name, number):book)     
        "2":_ -> do
            putStrLn "Enter name to search for phone number"
            name <- getLine
            print (filter(\(name', number') -> name' == name) book)
            doRequest book
        "3":_ -> do
            putStrLn "Enter phone number to search for name"
            number <- getLine
            print (filter(\(name', number') -> number' == number) book)
            doRequest book
        "4":_ -> do
            putStrLn "Enter file name"
            filename <- getLine
            writeFile filename (save book)
            doRequest book
        "5":_ -> do
            putStrLn "Enter file name"
            filename <- getLine
            string <- readFile filename
            doRequest (load string)
        _ -> do
            putStrLn "Incorrect command"
            doRequest book

textMenu = do
    putStrLn "Menu:"
    putStrLn "0 - exit"
    putStrLn "1 - add record (name and phone)"
    putStrLn "2 - find the phone by name"
    putStrLn "3 - find the name by phone"
    putStrLn "4 - save the current data to a file"
    putStrLn "5 - read data from file"


save book = concatMap (\(name, number) -> name ++ ", " ++ number ++ "\n") book
load string = map (splitToPair) (lines string)
splitToPair string = (head split, head (tail split)) where split = (splitOn "," string)

