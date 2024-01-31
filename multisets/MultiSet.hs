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
-- added the costraint `Eq a` to the type signature so that we are sure we can compare elements (to check if already present)
add :: Eq a => (MSet a) -> a -> (MSet a) 
add (MSet mset) v =
    -- check if element already present
    if elem v (map fst mset) then
        MSet (map incrementEntry v mset) -- if element already present, increment its multiplicity
    else
        MSet (mset ++ [(v, 1)]) -- if element not present, I concatenate a new entry

    where incrementEntry v (currKey, currVal) =
        if currKey == v then (currKey, currVal + 1) else (currKey, currVal)



-- returns the number of occurrences of `v` in `mset`
occs :: Eq a => (MSet a) -> a -> Int
occs (MSet mset) v = 
    case mset of
        [] -> 0 -- if mset is empty, I found 0 occurrences of v
        (currVal, currMult):rest -> if currVal == v then currMult else occs (MSet rest) v -- I check if the current element is the one I'm looking for

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


instance Foldable (MSet a)


-- Explain (in a comment in the same file) why it is not possible to define an instance of Functor for MSet by providing mapMSet as the implementation of fmap.
mapMSet :: (a -> b) -> (MSet a) -> (MSet b)
mapMSet fun mset 