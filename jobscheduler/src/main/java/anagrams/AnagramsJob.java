/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package anagrams;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;
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
    public Stream<Pair<String,String>> execute() {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(this.filename));
        } catch (FileNotFoundException e) {
            System.out.println("Error, file not found: " + this.filename);
            return Stream.empty();
        }
        return br
            .lines()
            .flatMap(s -> Arrays.stream(s.split(" ")))
            .filter(AnagramsJob::filterPredicate)
            .map(String::toLowerCase)
            .map(s -> new Pair<String, String>(ciao(s), s));
    }

    /**
     * Returns False for words of less than four characters, and those containing non-alphabetic characters.
     * So that they can be filtered out.
     * @param str
     * @return
     */
    private static Boolean filterPredicate(String str){
        return str.length() >= 4 && !str.chars().anyMatch((c) -> !Character.isAlphabetic(c));
    }

    /**
     * ciao (characters in alphabetical order)
     * @param str
     * @return the string having the same length of str and containing all the characters of str in lower case and alphabetical order.
     */
    private static String ciao(String str){
        return Stream
            .of(str.split(""))
            .sorted()
            .collect(Collectors.joining()); // joins the stream of characters into a single string
    }
    
    public String toString(){
        return "This is an `AnagramsJob` for the file " + filename + ".\n";
    }
}
