
package contentSearchersTests;

import org.example.contentSearchers.TextFileContentSearcher;
import org.example.model.ResultFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextFileContentSearcherTest {

    @Test
    public void testSearchContent() throws IOException {
        // Create a temporary text file for testing
        Path tempFile = Files.createTempFile("test", ".txt");
        File textFile = tempFile.toFile();
        textFile.deleteOnExit();

        // Write content to the text file
        String content = "This is a sample text file.\nIt contains some example content.";
        Files.write(tempFile, content.getBytes());

        String searchedContent = "sample";

        TextFileContentSearcher textFileContentSearcher = new TextFileContentSearcher();
        ResultFile resultFile = textFileContentSearcher.searchContent(textFile, searchedContent);

        Assertions.assertNotNull(resultFile);
        assertEquals(textFile.getName(), resultFile.getName());
        assertEquals(textFile.getPath(), resultFile.getPath());
    }

    @Test
    public void testSearchContent_WithNoMatch() throws IOException {
        // Create a temporary text file for testing
        Path tempFile = Files.createTempFile("test", ".txt");
        File textFile = tempFile.toFile();
        textFile.deleteOnExit();

        // Write content to the text file
        String content = "This is a sample text file.\nIt contains some example content.";
        Files.write(tempFile, content.getBytes());

        String searchedContent = "notfound";

        TextFileContentSearcher textFileContentSearcher = new TextFileContentSearcher();
        ResultFile resultFile = textFileContentSearcher.searchContent(textFile, searchedContent);

        Assertions.assertNull(resultFile);
    }

    @Test
    public void testSearchContent_WithEmptyFile() throws IOException {
        // Create a temporary empty text file for testing
        Path tempFile = Files.createTempFile("test", ".txt");
        File textFile = tempFile.toFile();
        textFile.deleteOnExit();

        String searchedContent = "sample";

        TextFileContentSearcher textFileContentSearcher = new TextFileContentSearcher();
        ResultFile resultFile = textFileContentSearcher.searchContent(textFile, searchedContent);

        Assertions.assertNull(resultFile);
    }

    @Test
    public void testSearchContent_WithInvalidFile() {
        File textFile = new File("nonexistent.txt");
        String searchedContent = "sample";

        TextFileContentSearcher textFileContentSearcher = new TextFileContentSearcher();

        // Expecting a RuntimeException to be thrown
        Assertions.assertThrows(RuntimeException.class, () ->
                textFileContentSearcher.searchContent(textFile, searchedContent));
    }
}
