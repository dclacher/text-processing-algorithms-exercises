package html_to_text;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class HtmlToTextMain {

    private static final String EMAIL_ADDRESS_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(EMAIL_ADDRESS_REGEX, Pattern.CASE_INSENSITIVE);
    private static final String PHONE_NUMBER_REGEX = "\\D*([2-9]\\d{2})(\\D*)([2-9]\\d{2})(\\D*)(\\d{4})\\D*";
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
                    String content = Files.readString(path, StandardCharsets.UTF_8);
                    Matcher m = PHONE_NUMBER_PATTERN.matcher(content);
                    while (m.find()) {
                        String s = m.group(1);
                        System.out.println(s);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        List<String> emails = new ArrayList<>();
        emails.add("listwww-style@w3.org");
        emails.add("user@domain.co.br");
        emails.add("user1@domain.com");
        emails.add("user.name@domain.com");
        emails.add("user-name@domain.com");
        emails.add("user_name@domain.com");
        emails.add("user#@domain.co.br");
        emails.add("user@domaincom");
        emails.add("user#domain.com");
        emails.add("@domain.com");

        for(String email : emails){
            System.out.println(email + " is valid? " + ValidationUtils.validateEmailAddress(email));
        }

        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("2234567890");
        phoneNumbers.add("323-456-7890");
        phoneNumbers.add("423.456.7890");
        phoneNumbers.add("123 456 7890");
        phoneNumbers.add("(523) 456 7890");
        phoneNumbers.add("12345678");
        phoneNumbers.add("12-12-111");

        for(String phoneNumber : phoneNumbers){
            System.out.println(phoneNumber + " is valid? (US/CA) " + ValidationUtils.validatePhoneNumber(phoneNumber));
        }
    }
}
