package html_to_text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class HtmlToTextMain {

    public static void main(String[] args) throws IOException {

        // Execute question 3
        htmlToTextConverter();

        // Execute question 4
        patternFinder();

        // Execute question 5
        urlFinder();
    }

    /**
     * 5. URL finder: Using Java Regex, write a program that finds Web links (URLs) in a Web file.
     * Test your program with the 100 HTML files to find the following:
     * a. Links with domain w3.org
     * b. Links that contain folders: e.g., www.w3.org/TR/owl-features/
     * c. Links that contain references in a Web page and may contain folders; for example:
     * www.w3.org/TR/owl-features/#DefiningSimpleClasses
     * d. Links with domain .net, .com, .org
     *
     * @throws IOException
     */
    private static void urlFinder() throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get("./static/html/"))) {
            // This stream gets each one of the HTML files for processing from (./static/html/)
            stream.filter(Files::isRegularFile).forEach(path -> {
                // For each HTML file, find URLs
                // The utils class is called 100 times, for the sake of good OO design
                try {
                    String content = Files.readString(path);
                    System.out.println("URLs found in file: " + path.getFileName());
                    List<String> urls = ValidationUtils.findAllUrlsInText(content);
                    urls.forEach(System.out::println);
                } catch (IOException e) {
                    System.out.println("Something bad has happened: " + e.getClass().getName() + ", " + e.getMessage());
                }
            });
        }
    }

    /**
     * 4. Pattern finder: Using Java Regex, find phone numbers and email addresses in the 100 text files.
     *
     * @throws IOException
     */
    private static void patternFinder() throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get("./static/text/"))) {
            // This stream gets each one of the TEXT files for processing from (./static/text/)
            stream.filter(Files::isRegularFile).forEach(path -> {
                // For each TEXT file, find phone numbers and emails
                // This utils class is called 100 times, for the sake of good OO design
                try {
                    String content = Files.readString(path);

                    // EMAILS:
                    System.out.println("E-MAILS found in file: " + path.getFileName());
                    List<String> emails = ValidationUtils.findAllEmailAddressesInText(content);
                    emails.forEach(System.out::println);

                    // PHONE NUMBERS:
                    System.out.println("PHONE NUMBERS found in file: " + path.getFileName());
                    List<String> phoneNumbers = ValidationUtils.findAllPhoneNumbersInText(content);
                    phoneNumbers.forEach(System.out::println);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * QUESTION 3: HTMLtoText converter: Write a program that takes the 100 given Web pages (W3C WebPages.zip),
     * and using Jsoup, converts all files into text. The text files should be saved as individual files for use
     * in the next tasks of this assignment. Keep good OO design practice by creating a method processes one file.
     * That method will then be called 100 times.
     *
     * @throws IOException
     */
    private static void htmlToTextConverter() throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get("./static/html/"))) {
            // This stream gets each one of the HTML files for processing from (./static/html/)
            stream.filter(Files::isRegularFile).map(Path::toFile).forEach(file -> {
                // For each HTML file, get the formatted (parsed) text
                // This utils class is called 100 times, for the sake of good OO design
                String text = ConversionUtils.getPlainTextFromHtmlFile(file);
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
    }
}
