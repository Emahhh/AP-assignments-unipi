module MultiSet where
-- exercise: implement a type constructor providing the functionalities of multisets (also known as bags)
-- that is, collections of elements where the order does not count, but each element can occur several times.

-- CONSTRUCTOR provided by the exercise
-- `MS` is the constructor for the type `MSet`
data MSet a = MS [(a, Int)] deriving (Show)

-- empty constructor
-- returns an empty multiset
empty :: MSet a
empty = MS []

-- returns a multiset obtained by adding the element `v` to `mset`
-- added the costraint `Eq a` to the type signature so that we can compare elements (to check if already present)
add :: Eq a => (MSet a) -> a -> (MSet a) 
add (MSet mset) v = MS [] -- TODO: complete

-- returns the number of occurrences of `v` in `mset`
occs :: Eq a => (MSet a) -> a -> Int
occs (MSet mset) v