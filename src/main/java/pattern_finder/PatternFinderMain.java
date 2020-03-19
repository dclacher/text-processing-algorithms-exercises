package pattern_finder;

import utils.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class PatternFinderMain {

    /**
     * 4. Pattern finder: Using Java Regex, find phone numbers and email addresses in the 100 text files.
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
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
}
