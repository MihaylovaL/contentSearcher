package contentSearchersTests;

import org.example.contentSearchers.FileSearcher;
import org.example.model.ResultFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class FileSearcherTest {
    private FileSearcher fileSearcher;

    @BeforeEach
    public void setUp() {
        fileSearcher = new FileSearcher();
    }

    @Test
    public void testSearchFiles() {
        // Specify the directory and content to search for
        File directory = new File("path/to/directory");
        String searchedContent = "example";

        // Perform the search
        List<ResultFile> resultFiles = fileSearcher.searchFiles(directory, searchedContent);

        // Assert the expected results
        Assertions.assertNotNull(resultFiles);
        Assertions.assertTrue(resultFiles.isEmpty());

        // You can perform further assertions on the resultFiles list if needed
        for (ResultFile resultFile : resultFiles) {
            // Perform assertions on individual ResultFile objects
            Assertions.assertTrue(resultFile.getName().contains(searchedContent));
        }
    }
    @Test
    public void testSearchFilesWithNonexistentDirectory() {
        // Specify a nonexistent directory and content to search for
        File directory = new File("nonexistent/directory");
        String searchedContent = "example";

        // Perform the search
        List<ResultFile> resultFiles = fileSearcher.searchFiles(directory, searchedContent);

        // Assert that no files are found
        Assertions.assertNotNull(resultFiles);
        Assertions.assertTrue(resultFiles.isEmpty());
    }


    @Test
    public void testSearchFilesWithEmptyContent() {
        // Specify the directory and empty content to search for
        File directory = new File("path/to/directory");
        String searchedContent = "";

        // Perform the search
        List<ResultFile> resultFiles = fileSearcher.searchFiles(directory, searchedContent);

        // Assert that no files are found
        Assertions.assertNotNull(resultFiles);
        Assertions.assertTrue(resultFiles.isEmpty());
    }

    @Test
    public void testSearchFilesWithUnsupportedFile() {
        // Specify a file with an unsupported extension and content to search for
        File file = new File("path/to/file.unsupported");
        String searchedContent = "example";

        // Perform the search
        List<ResultFile> resultFiles = fileSearcher.searchFiles(file, searchedContent);

        // Assert that no files are found
        Assertions.assertNotNull(resultFiles);
        Assertions.assertTrue(resultFiles.isEmpty());
    }
}
