# Anagrams

Some quick notes:
- To run with maven: `mvn exec:java`
- The `jobsched.Jobscheduler.collect()` method has been implemented to **not count duplicates** in the list of each `Pair`. Hence, the same word will not be counted more than once. 
- I implemented **3** different **output strategies**:
  - one that prints the key and its **number** of words in a file named `count_anagrams.txt`
  - one that prints the key and its **number** of words in a csv file named `count_anagrams.csv`
  - one that prints the key and its **anagrams list**  in a file named `anagrams.txt`
- A demo of all these 3 strategies is implemented in the class `AnagramsMain.java`, which will therefore ask for the path of the directory 3 times.