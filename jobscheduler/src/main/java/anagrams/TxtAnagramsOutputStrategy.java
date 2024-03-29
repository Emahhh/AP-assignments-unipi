package anagrams;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import jobsched.OutputStrategy;
import jobsched.Pair;


/**
 * Additional strategy that writes the results in a .txt file, 
 * in the format is `key : listOfWords`
 */
public class TxtAnagramsOutputStrategy implements OutputStrategy <String, String> {

    private static final String FILE_NAME = "anagrams.txt";

    /**
     * For each line, 
     * it writes the single key and the list of strings associated with each key
     */
    @Override
    public void output(Stream<Pair<String, List<String>>> myStream) {

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME))) {
        myStream.forEach(pair -> {
            try {
                writer.write(pair.getKey() + " : " + pair.getValue() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
}
