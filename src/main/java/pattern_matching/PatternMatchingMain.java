package pattern_matching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import textprocessing.*;

public class PatternMatchingMain {

    public static void main(String[] args) throws IOException {

        // 1.a. Retrieve the text file
        String text = Files.readString(Paths.get("./static/Hard disk.txt"));

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
            Map<String, List<Integer>> bruteForceResult =
                    PatternMatchingUtils.getPatternsOffsetsInText(patterns, text, PatternMatchingEnum.BRUTE_FORCE.value);
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

        // BOYER-MOORE
        // Loop to repeat the process 100 times (1.c)
        long totalTimeBM = 0L;
        for (int i = 0; i < 100; i++) {
            long startTimeBM = System.nanoTime();
            // Call the method to retrieve all the offsets for all patterns and save them in a Map object
            Map<String, List<Integer>> boyerMooreResult =
                    PatternMatchingUtils.getPatternsOffsetsInText(patterns, text, PatternMatchingEnum.BOYER_MOORE.value);
            // Traverse the Map to show the results
            Iterator<Map.Entry<String, List<Integer>>> iterator = boyerMooreResult.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<Integer>> entry = iterator.next();
                List<Integer> value = entry.getValue();
                System.out.println("Key = " + entry.getKey());
                value.forEach(System.out::println);
            }
            long elapsedTimeBM = System.nanoTime() - startTimeBM;
            totalTimeBM += elapsedTimeBM;
        }

        // KMP
        // Loop to repeat the process 100 times (1.c)
        long totalTimeKMP = 0L;
        for (int i = 0; i < 100; i++) {
            long startTimeKMP = System.nanoTime();
            // Call the method to retrieve all the offsets for all patterns and save them in a Map object
            Map<String, List<Integer>> kmpResult =
                    PatternMatchingUtils.getPatternsOffsetsInText(patterns, text, PatternMatchingEnum.KMP.value);
            // Traverse the Map to show the results
            Iterator<Map.Entry<String, List<Integer>>> iterator = kmpResult.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<Integer>> entry = iterator.next();
                List<Integer> value = entry.getValue();
                System.out.println("Key = " + entry.getKey());
                value.forEach(System.out::println);
            }
            long elapsedTimeKMP = System.nanoTime() - startTimeKMP;
            totalTimeKMP += elapsedTimeKMP;
        }

        // Printing the average times for each algorithm
        System.out.println("Average time for Brute Force in nanoseconds: " + totalTimeBF / 100);
        System.out.println("Average time for Boyer-Moore in nanoseconds: " + totalTimeBM / 100);
        System.out.println("Average time for KMP in nanoseconds: " + totalTimeKMP / 100);
    }
}
