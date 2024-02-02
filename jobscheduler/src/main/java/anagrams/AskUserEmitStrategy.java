package anagrams;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jobsched.AJob;
import jobsched.EmitStrategy;

public class AskUserEmitStrategy implements EmitStrategy {

    /**
     * Asks the user for a path of a directory containing documents.
     * For each file in that directory, it creates a job.
     * @return a stream of jobs
     */
    @Override
    public Stream<AnagramsJob> emit() {
        System.out.println("Insert the absolute path of the directory containing the documents: ");
        String path;
        
        try {
            path = System.console().readLine();
        } catch (Exception e){
            System.err.println("Error while reading the path from the user: " + e.getMessage());
            return Stream.empty();
        }
        
        Path dirPath = Paths.get(path);

        try {
            return Files.list(dirPath)
                    .filter(Files::isRegularFile) // Filter out directories and special files
                    .map(p -> new AnagramsJob(p.toString())) // passes the Path as a string
                    .collect(Collectors.toList())
                    .stream() // Convert the list to a stream
                    ;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            System.out.println("Successfully emitted all jobs for the directory");
        }

    }
    
}
