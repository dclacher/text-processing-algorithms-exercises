package pattern_matching;

public enum PatternMatchingEnum {
    BRUTE_FORCE("Brute Force"),
    BOYER_MOORE("Boyer Moore"),
    KMP("KMP");

    public final String value;

    private PatternMatchingEnum(String value) {
        this.value = value;
    }
}
