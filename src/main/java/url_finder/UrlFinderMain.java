package url_finder;

import utils.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class UrlFinderMain {

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
    public static void main(String[] args) throws IOException {
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
}
