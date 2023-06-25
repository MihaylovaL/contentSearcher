package contentSearchersTests;

import org.example.contentSearchers.JarContentSearcher;
import org.example.model.ResultFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JarContentSearcherTest {

    @Test
    public void testSearchContent() throws IOException {
        File jarFile = createJarFileWithEntry("sample.jar", "This is a sample text file.");

        String searchedContent = "sample";

        JarContentSearcher jarContentSearcher = new JarContentSearcher();
        ResultFile resultFile = jarContentSearcher.searchContent(jarFile, searchedContent);

        assertNotNull(resultFile);
        assertEquals(jarFile.getName(), resultFile.getName());
        assertEquals(jarFile.getPath(), resultFile.getPath());
    }

    @Test
    public void testSearchContent_FileWithoutMatchingContent() throws IOException {
        File jarFile = createJarFileWithEntry("sample.txt", "This is a sample text file.");

        String searchedContent = "notfound";

        JarContentSearcher jarContentSearcher = new JarContentSearcher();
        ResultFile resultFile = jarContentSearcher.searchContent(jarFile, searchedContent);

        Assertions.assertNull(resultFile);
    }

    @Test
    public void testSearchContent_WithEmptyJarFile() throws IOException {
        File jarFile = createEmptyJarFile();

        String searchedContent = "sample";

        JarContentSearcher jarContentSearcher = new JarContentSearcher();
        ResultFile resultFile = jarContentSearcher.searchContent(jarFile, searchedContent);

        Assertions.assertNull(resultFile);
    }

    @Test
    public void testSearchContent_WithInvalidFile() {
        File jarFile = new File("nonexistent.jar");
        String searchedContent = "sample";

        JarContentSearcher jarContentSearcher = new JarContentSearcher();

        // Expecting a RuntimeException to be thrown
        Assertions.assertThrows(RuntimeException.class, () ->
                jarContentSearcher.searchContent(jarFile, searchedContent));
    }

    private File createJarFileWithEntry(String entryName, String entryContent) throws IOException {
        File jarFile = File.createTempFile("test", ".jar");
        jarFile.deleteOnExit();

        try (JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(jarFile))) {
            JarEntry jarEntry = new JarEntry(entryName);
            jarOutputStream.putNextEntry(jarEntry);
            jarOutputStream.write(entryContent.getBytes());
            jarOutputStream.closeEntry();
        }

        return jarFile;
    }

    private File createEmptyJarFile() throws IOException {
        File jarFile = File.createTempFile("test", ".jar");
        jarFile.deleteOnExit();

        try (JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(jarFile))) {
            // Add an empty entry to the JAR file
            JarEntry emptyEntry = new JarEntry("empty.txt");
            jarOutputStream.putNextEntry(emptyEntry);
            jarOutputStream.closeEntry();
        }

        return jarFile;
    }
}