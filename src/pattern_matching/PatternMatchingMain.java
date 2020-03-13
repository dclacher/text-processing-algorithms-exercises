package pattern_matching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import textprocessing.*;

public class PatternMatchingMain {

    public static void main(String[] args) throws IOException {

        String pat = "hard";
        String txt =  Files.readString(Paths.get("./03 Hard disk drive.txt"));
        char[] pattern = pat.toCharArray();
        char[] text    = txt.toCharArray();

        int offset1a = BruteForceMatch.search1(pat, txt);
        int offset2a = BruteForceMatch.search2(pat, txt);
        int offset1b = BruteForceMatch.search1(pattern, text);
        int offset2b = BruteForceMatch.search2(pattern, text);

        System.out.println(offset1a);
        System.out.println(offset1b);
        System.out.println(offset2a);
        System.out.println(offset2b);

        // b. Find all occurrences of patterns "hard", "disk", "hard disk", "hard drive", "hard dist" and "xltpru", and show the offsets.
        String[] patterns = {"hard", "disk", "hard disk", "hard drive", "hard dist", "xltpru"};
        Map<String, List<Integer>> bruteForceResult = bruteForceMatching(patterns, txt);
        Iterator<Map.Entry<String, List<Integer>>> iterator = bruteForceResult.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, List<Integer>> entry = iterator.next();
            List<Integer> value = entry.getValue();
            System.out.println("Key = " + entry.getKey());
            value.forEach(System.out::println);
        }

        // c. Repeat "b" 100 times and record the average CPU time for each case.
    }

    private static Map<String, List<Integer>> bruteForceMatching(String[] patterns, String text) {
        Map<String, List<Integer>> offsets = new HashMap<>();
        for (String pattern : patterns) {
            String tempText = text;
            int textLength = tempText.length();
            List<Integer> patternOffsets = new ArrayList<>();
            int i = 0;
            while (i < textLength) {
                int offset = BruteForceMatch.search1(pattern, tempText);
                patternOffsets.add(offset + i);
                if (offset < textLength) {
                    tempText = tempText.substring(offset + 1);
                    textLength = tempText.length();
                }
                i = i + offset + 1;
            }
            offsets.put(pattern, patternOffsets);
        }
        return offsets;
    }

    private static void boyerMooreMatching() {
    }

    private static void kmpMatching() {
    }
}
