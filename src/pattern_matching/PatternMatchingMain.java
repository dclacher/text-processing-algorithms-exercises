package pattern_matching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import textprocessing.*;

public class PatternMatchingMain {

    public static void main(String[] args) throws IOException {

        // 1.a. Retrieve the text file
        String text = Files.readString(Paths.get("./03 Hard disk drive.txt"));

        // 1.b. Find all occurrences of patterns "hard", "disk", "hard disk", "hard drive", "hard dist" and "xltpru",
        // and show the offsets.
        // 1.c. Repeat "b" 100 times and record the average CPU time for each case.

        // Create array of patterns
        String[] patterns = {"hard", "disk", "hard disk", "hard drive", "hard dist", "xltpru"};

        // BRUTE FORCE
        // Loop to repeat the process 100 times (1.c)
        long totalTimeBF = 0L;
        for (int i = 0; i < 100; i++) {
            long startTimeBF = System.nanoTime();
            // Call the method to retrieve all the offsets for all patterns and save them in a Map object
            Map<String, List<Integer>> bruteForceResult = bruteForceMatching(patterns, text);
            // Traverse the Map to show the results
            Iterator<Map.Entry<String, List<Integer>>> iterator = bruteForceResult.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<Integer>> entry = iterator.next();
                List<Integer> value = entry.getValue();
                System.out.println("Key = " + entry.getKey());
                value.forEach(System.out::println);
            }
            long elapsedTimeBF = System.nanoTime() - startTimeBF;
            totalTimeBF += elapsedTimeBF;
        }
        System.out.println("Average time for Brute Force in nanoseconds: " + totalTimeBF / 100);
    }

    private static void boyerMooreMatching(String[] patterns, String text) {
        BoyerMoore boyerMoore = new BoyerMoore(patterns[0]);
        int offset1 = boyerMoore.search(text);
    }

    /**
     * Method to find all occurrences of patterns in a text using the Brute Force algorithm
     * @param patterns the patterns to be found
     * @param text the text to be searched
     * @return a map with a list of offsets (occurrences) for each one of the patterns
     */
    private static Map<String, List<Integer>> bruteForceMatching(String[] patterns, String text) {
        Map<String, List<Integer>> offsets = new HashMap<>();
        for (String pattern : patterns) {
            String tempText = text;
            int tempLength = tempText.length();
            int textLength = text.length();
            List<Integer> patternOffsets = new ArrayList<>();
            int i = 0;
            while (i < textLength) {
                int offset = BruteForceMatch.search1(pattern, tempText);
                if (offset + i < textLength) {
                    patternOffsets.add(offset + i);
                }
                if (offset < tempLength) {
                    tempText = tempText.substring(offset + 1);
                    tempLength = tempText.length();
                }
                i = i + offset + 1;
            }
            offsets.put(pattern, patternOffsets);
        }
        return offsets;
    }

    private static void kmpMatching() {
    }
}
