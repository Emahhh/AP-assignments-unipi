/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package anagrams;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import jobsched.AJob;
import jobsched.Pair;  

/**
 *
 * @author ema
 */
public class AnagramsJob extends AJob<String, String> {

    private final String filename;
    
    public AnagramsJob(String filename){
        this.filename = filename;
    }
    
    /**
     * Reads the file specified in the constructor and returns a stream of pairs
     *  a stream containing all pairs of the form `(ciao(w), w)` where w is a word of the file.
     * @return a stream of pairs
     */
    @Override
    public Stream<Pair<String, String>> execute() {
        // reads the file filename
        try (FileInputStream fis = new FileInputStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                return br
                    .lines()
                    .flatMap(line -> Stream.of(line.toLowerCase().split("\\s+"))) // to get lowercase words
                    .filter(AnagramsJob::filterPredicate) // to filter out words of less than four characters
                    .map(w -> new Pair<String, String>(ciao(w), w));

                    
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    /**
     * Returns False for words of less than four characters, and those containing non-alphabetic characters.
     * So that they can be filtered out.
     * @param str
     * @return
     */
    public static Boolean filterPredicate(String str){
        return str.length() >= 4 && !str.chars().anyMatch((c) -> !Character.isAlphabetic(c));
    }

    /**
     * ciao (characters in alphabetical order)
     * @param str
     * @return the string having the same length of str and containing all the characters of str in lower case and alphabetical order.
     */
    public static String ciao(String str){
        return str
            .toLowerCase()
            .chars()
            .sorted()
            .toString();
    }
    
}
