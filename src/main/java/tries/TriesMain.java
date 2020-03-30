package tries;

import pattern_matching.PatternMatchingEnum;
import utils.PatternMatchingUtils;
import textprocessing.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TriesMain {

    public static void main(String[] args) throws IOException {
        // a. Write a program that reads file “Protein.txt” and creates a trie using TST. Use
        // StringTokenizer, Jsoup or a similar API to extract the words from the file.

        // Read the file
        String text = Files.readString(Paths.get("./static/Protein.txt"));

        // Use StringTokenizer to extract the content of the text into a list
        List<String> tokens = Collections.list(new StringTokenizer(text, ",._() \t\n\r\f")).stream()
                .map(token -> token.toString().toLowerCase())
                .collect(Collectors.toList());

        // Create the TST from the list generated above
        TST<Integer> ternarySearchTree = new TST<>();
        for (String token : tokens) {
            ternarySearchTree.put(token, tokens.indexOf(token));
            // System.out.println("Key: " + token + ", Index: " + tokens.indexOf(token));
        }

        // Getting the keys from the TST
        List<String> result = StreamSupport.stream(ternarySearchTree.keys().spliterator(), false)
                .collect(Collectors.toList());
        for (String s : result) {
            System.out.println(result.indexOf(s) + "\t\t" + s);
        }
        System.out.println(ternarySearchTree.get("on")); // index in list is 116, but in TST it's 147

        // b. Do several searches of keys “protein”, “complex”, “PPI”, “prediction”, and others, and
        // show the occurrences of these words in file Protein.txt

        String[] patterns = {"protein", "complex", "PPI", "prediction", "interaction", "analysis"};
        searchPatternsAndDisplayOccurrencesInText(patterns, text, ternarySearchTree);
    }

    private static void searchPatternsAndDisplayOccurrencesInText(String[] patterns,
                                                                  String text,
                                                                  TST<Integer> ternarySearchTree) {
        for (String pattern : patterns) {
            System.out.println("Value for key '" + pattern + "' in the TST: " + ternarySearchTree.get(pattern));
            System.out.println("Occurrences (offsets) of key '" + pattern + "' in the text file:");
            String[] singlePattern = {pattern};
            Map<String, List<Integer>> occurrences =
                    PatternMatchingUtils.getPatternsOffsetsInText(singlePattern, text, PatternMatchingEnum.BOYER_MOORE.value);
            Iterator<Map.Entry<String, List<Integer>>> iterator = occurrences.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<Integer>> entry = iterator.next();
                List<Integer> value = entry.getValue();
                value.forEach(System.out::println);
            }
        }
    }
}
