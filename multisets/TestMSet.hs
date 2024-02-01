import MultiSet
import Data.List (sort)
import Data.Char (toLower)

-- ciao (characters in alphabetical order)
-- returns a string of same elngth, with each character in alphabetical order and lowercase
ciao :: [Char] -> [Char]
ciao str = sort (map toLower str)


-- reads the content of `filename`
-- returns a MSet containt the ciao of all words of the file
readMSet :: FilePath -> IO (MSet [Char])
readMSet filename = do
  fileContent <- readFile filename
  let wordsList = words fileContent -- divide the content into words
  return (foldr util empty wordsList) -- fold on the wlist in order to create an mset
  where
    util word acc = add acc (ciao word)

--  given a multiset and a file name, writes in the file, one per line, each element of the multiset with its multiplicity
-- in the format `<elem> - <multiplicity>\n`
writeMSet :: FilePath -> MSet [Char] -> IO ()
writeMSet filename (MS mset) =
  writeFile filename (foldr appendElement "" mset)
  where
      appendElement (elem, mult) acc =
        let formattedElem = elem ++ " - " ++ show mult ++ "\n" -- builds a line for the current element
        in formattedElem ++ acc -- retutning a string of all the lines so far

main :: IO()
main = do
  -- read files to msets
  m1 <- readMSet "./aux_files/anagram.txt"
  m2 <- readMSet "./aux_files/anagram-s1.txt"
  m3 <- readMSet "./aux_files/anagram-s2.txt"
  m4 <- readMSet "./aux_files/margana2.txt"

  -- checking fact i
  if elems m1 == elems m4 && m1 /= m4 then putStrLn "fact i TRUE (Multisets m1 and m4 are not equal, but they have the same elements)"
  else putStrLn "fact i FALSE (Multisets m1 and m4 are not equal, but they have the same elements)"

  -- checking fact ii
  if m1 == (union m2 m3) then putStrLn "fact ii TRUE (Multiset m1 is equal to the union of multisets m2 and m3)"
  else putStrLn "fact ii FALSE (Multiset m1 is equal to the union of multisets m2 and m3)"

  -- write m1 and m4 to file
  writeMSet "./anag-out.txt" m1
  writeMSet "./gana-out.txt" m4