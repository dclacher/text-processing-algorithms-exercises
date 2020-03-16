package tries;

import textprocessing.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class TriesMain {

    public static void main(String[] args) throws IOException {
        // a. Write a program that reads file “Protein.txt” and creates a trie using TST. Use
        // StringTokenizer, Jsoup or a similar API to extract the words from the file.

        // Read the file
        String text = Files.readString(Paths.get("./Protein.txt"));

        // Use StringTokenizer to extract the content of the text into a list
        List<String> tokens = Collections.list(new StringTokenizer(text, ",.-_() \t\n\r\f")).stream()
                .map(token -> (String) token)
                .collect(Collectors.toList());

        // Create the TST from the list generated above
        TST<Integer> ternarySearchTree = new TST<>();
        for (String token : tokens) {
            ternarySearchTree.put(token, tokens.indexOf(token));
            System.out.println("Key: " + token + ", Index: " + tokens.indexOf(token));
        }

        // b. Do several searches of keys “protein”, “complex”, “PPI”, “prediction”, and others, and
        // show the occurrences of these words in file Protein.txt

        Iterable<String> iterable = ternarySearchTree.keys();
        System.out.println("METHOD KEYS():");
        for (String s : iterable) {
            //if (s.toLowerCase().contains("protein")) {
                System.out.println(s);
            //}
        }

        Iterable<String> iterable2 = ternarySearchTree.prefixMatch("protein");
        System.out.println("METHOD PREFIXMATCH(...):");
        for (String t : iterable2) {
            System.out.println(t);
        }

        Iterable<String> iterable3 = ternarySearchTree.wildcardMatch("protei.........");
        System.out.println("METHOD WILDCARDMATCH(...):");
        for (String u : iterable3) {
            System.out.println(u);
        }
    }
}
