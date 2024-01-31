module TestMSet where
import MultiSet

esempioSet :: MSet Int
esempioSet = MS [(1, 3), (2, 2), (3, 1)]

main :: IO ()
main = do
    putStrLn "Hello, TestMSet!"
    print esempioSet
