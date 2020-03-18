package html_to_text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class HtmlToTextMain {

    // TODO URL REGEX : ^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]

    private static final String EMAIL_ADDRESS_REGEX =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(EMAIL_ADDRESS_REGEX);
    private static final String PHONE_NUMBER_REGEX =
            "1?\\W*([2-9][0-8][0-9])\\W*([2-9][0-9]{2})\\W*([0-9]{4})(\\se?x?t?(\\d*))?";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    public static void main(String[] args) throws IOException {

        // QUESTION 3: HTMLtoText converter: Write a program that takes the 100 given Web pages (W3C WebPages.zip),
        // and using Jsoup, converts all files into text. The text files should be saved as individual files for use
        // in the next tasks of this assignment. Keep good OO design practice by creating a method processes one file.
        // That method will then be called 100 times.

        try (Stream<Path> stream = Files.walk(Paths.get("./static/html/"))) {
            // This stream gets each one of the HTML files for processing from (./static/html/)
            stream.filter(Files::isRegularFile).map(Path::toFile).forEach(file -> {
                // For each HTML file, get the formatted (parsed) text
                // This utils class is called 100 times, for the sake of good OO design
                String text = ConversionUtils.getPlainTextFromHtml(file);
                // Save the formatted text in a new file, in a different folder (./static/text/)
                Path path = Paths.get("./static/text/"
                        + file.getName().replaceFirst("[.][^.]+$", "")
                        + ".txt");
                try {
                    if (text != null) {
                        Files.write(path, text.getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        // QUESTION 4: Pattern finder: Using Java Regex, find phone numbers and email addresses in the 100 text files.

        try (Stream<Path> stream = Files.walk(Paths.get("./static/text/"))) {
            // This stream gets each one of the TEXT files for processing from (./static/html/)
            stream.filter(Files::isRegularFile).forEach(path -> {
                // For each TEXT file, find phone numbers and emails
                // This utils class is called 100 times, for the sake of good OO design
                try {
                    String content = Files.readString(path);
                    Matcher emailMatcher = EMAIL_ADDRESS_PATTERN.matcher(content);
                    System.out.println("E-MAILS found in file: " + path.getFileName());
                    while (emailMatcher.find()) {
                        String s = emailMatcher.group();
                        System.out.println(s);
                    }
                    Matcher phoneMatcher = PHONE_NUMBER_PATTERN.matcher(content);
                    System.out.println("PHONE NUMBERS found in file: " + path.getFileName());
                    while (phoneMatcher.find()) {
                        String s = phoneMatcher.group();
                        System.out.println(s);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
