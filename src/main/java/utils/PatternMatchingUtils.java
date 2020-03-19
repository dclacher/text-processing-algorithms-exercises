package utils;

import pattern_matching.PatternMatchingEnum;
import textprocessing.BoyerMoore;
import textprocessing.BruteForceMatch;
import textprocessing.KMP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternMatchingUtils {

    /**
     * Method to find all offsets of patterns in a text using either Brute Force, Boyer-Moore or the KMP algorithm
     * @param patterns the patterns to be found
     * @param text the text to be searched
     * @return a map with a list of offsets for each one of the patterns
     */
    public static Map<String, List<Integer>> getPatternsOffsetsInText(String[] patterns, String text, String type) {
        Map<String, List<Integer>> offsets = new HashMap<>();
        for (String pattern : patterns) {
            String tempText = text;
            int tempLength = tempText.length();
            int textLength = text.length();
            List<Integer> patternOffsets = new ArrayList<>();
            KMP kmp = new KMP(pattern);
            BoyerMoore boyerMoore = new BoyerMoore(pattern);
            int i = 0;
            while (i < textLength) {
                int offset = getOffsetForType(pattern, tempText, type, kmp, boyerMoore);
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

    private static int getOffsetForType(String pattern, String tempText, String type, KMP kmp, BoyerMoore boyerMoore) {
        if (type.equalsIgnoreCase(PatternMatchingEnum.BRUTE_FORCE.value)) {
            return BruteForceMatch.search1(pattern, tempText);
        } else if (type.equalsIgnoreCase(PatternMatchingEnum.BOYER_MOORE.value)) {
            return boyerMoore.search(tempText);
        } else {
            return kmp.search(tempText);
        }
    }
}
