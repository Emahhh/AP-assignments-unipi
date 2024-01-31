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


-- OPERATIONS --------------------------------

-- returns a multiset obtained by adding the element `v` to `mset`
-- added the costraint `Eq a` to the type signature so that we can compare elements (to check if already present)
add :: Eq a => (MSet a) -> a -> (MSet a) 
add (MSet mset) v = MS [] -- TODO: complete

-- returns the number of occurrences of `v` in `mset`
occs :: Eq a => (MSet a) -> a -> Int
occs (MSet mset) v

-- returns a list containing all the elements in `mset`
elems :: Mset a -> [a] 
elems (MSet mset)

-- returns `True` iff each element of `mset1` is also an element of `mset2`, with at least the same multiplicity
subeq :: Eq a => (Mset a) -> (Mset a) -> Bool
subeq mset1 mset2

-- returns a MSet having all elements of `mset1` and of `mset2`, each with the sum of the corresponding multiplicities
union :: Eq a => (Mset a) -> (Mset a) -> (Mset a)
union mset1 mset2




-- CLASS CONSTRUCTOR INSTANCES -----------
-- TODO: controllare documentazione per instance


-- Defining MSet to be an instance of Eq. This means implementing the equality operation on `MSet`.
-- I can only do this if the type a itself has an instance of Eq.
-- equality is defined as: two multisets are equal iff they contain the same elements with the same multiplicities
instance Eq a => Eq (MSet a) where
    (==) :: Eq a => MSet a -> MSet a -> Bool
    (==) mset1 mset2 = subeq mset1 mset2 && subeq mset2 mset1