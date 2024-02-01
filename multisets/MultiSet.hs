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
add :: Eq a => MSet a -> a -> MSet a 
add (MS []) v = MS [(v, 1)] -- if v is not already present in the multiset, I add the pair
add (MS ((currVal, n):rest)) v
    | currVal == v    = MS ((currVal, n + 1):rest) -- if the current element is the one I'm looking for
    | otherwise = -- I continue iterating through the list
        let MS newRest = add (MS rest) v in
        MS ((currVal, n):newRest)


-- returns the number of occurrences of `v` in `mset`
occs :: Eq a => MSet a -> a -> Int
occs (MS mset) v = 
    case mset of
        [] -> 0 -- if mset is empty, I found 0 occurrences of v
        (currVal, currMult):rest -> if currVal == v then currMult else occs (MS rest) v -- I check if the current element is the one I'm looking for

-- returns a list containing all the elements in `mset`
elems :: MSet a -> [a] 
elems (MS mset) =
    foldl util [] mset
    where util acc (currKey, _) = currKey:acc -- I add the current element to the list

-- returns `True` iff each element of `mset1` is also an element of `mset2`, with at least the same multiplicity
subeq :: Eq a => MSet a -> MSet a -> Bool
subeq (MS []) _ = True
subeq (MS ((currKey, currMult):rest)) mset2 = if currMult > occs mset2 currKey then False else subeq (MS rest) mset2


-- additional function (not required by the exercise) added to implement the next one
-- runs the `add` function `n` times on value `v`
-- TODO: ADD TYPE SIGNATURES
addNTimes v n mset =
    if n <= 0 then mset
    else addNTimes v (n-1) (add mset v)


-- returns a MSet having all elements of `mset1` and of `mset2`, each with the sum of the corresponding multiplicities
-- to implement this, I add to `mset1` each element of `mset2`, as many times as it occurs in `mset2`

union :: Eq a => MSet a -> MSet a -> MSet a
union mset1 mset2 =
    let MS pairsList = mset2
    in
    foldl util mset1 pairsList
    where util acc (currKey, currMult) = addNTimes currKey currMult acc



-- INSTANCES -----------


-- Defining MSet to be an instance of Eq. This means implementing the equality operation on `MSet`.
-- I can only do this if the type a itself has an instance of Eq (`\=` is derived from `==`).
-- equality is defined as: two multisets are equal iff they contain the same elements with the same multiplicities
instance Eq a => Eq (MSet a) where
    (==) :: Eq a => MSet a -> MSet a -> Bool
    (==) mset1 mset2 = subeq mset1 mset2 && subeq mset2 mset1


-- Defining MSet to be an instance of Foldable.
-- implementing `foldr` is enough (minimal set of fucntion to be implemented according to the documentation of `Foldable`)
instance Foldable MSet where
    foldr fun acc (MS []) = acc
    foldr fun acc (MS ((key,_):rest)) = fun key (foldr fun acc (MS rest))



-- map ----------------

-- TODO: Explain (in a comment in the same file) why it is not possible to define an instance of Functor for MSet by providing mapMSet as the implementation of fmap.
mapMSet :: (a -> b) -> MSet a -> MSet b
mapMSet fun (MS mset) = 
    let mappedSet = map (\(x, y) -> (fun x, y)) mset
    in MS mappedSet