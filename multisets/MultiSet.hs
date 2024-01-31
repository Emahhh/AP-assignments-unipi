module MultiSet where

data MSet a = MS [(a, Int)] deriving (Show)

main = do
    putStrLn "Hello, World!"