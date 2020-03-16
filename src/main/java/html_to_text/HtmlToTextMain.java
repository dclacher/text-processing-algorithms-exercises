package html_to_text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class HtmlToTextMain {

    public static void main(String[] args) throws IOException {

        // HTMLtoText converter: Write a program that takes the 100 given Web pages (W3C WebPages.zip), and using Jsoup,
        // converts all files into text. The text files should be saved as individual files for use in the next tasks of
        // this assignment. Keep good OO design practice by creating a method processes one file.
        // That method will then be called 100 times.

        try (Stream<Path> stream = Files.walk(Paths.get("./static/html/"))) {
            // This stream gets each one of the HTML files for processing from (./static/html/)
            stream.filter(Files::isRegularFile).map(Path::toFile).forEach(file -> {
                // For each HTML file, get the formatted (parsed) text
                // This utils class is called 100 times, for the sake of good OO design
                String text = ConversionUtils.getPlainTextFromHtml(file);
                // Save the formatted text in a new file, in a different folder (./static/text/)
                Path path = Paths.get("./static/text/"+ file.getName().replaceFirst("[.][^.]+$", "") + ".txt");
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
