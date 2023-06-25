package contentSearchersTests;

import org.example.contentSearchers.JarContentSearcher;
import org.example.contentSearchers.ZipContentSearcher;
import org.example.model.ResultFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ZipContentSearcherTest {

    @Test
    public void testSearchContent() throws IOException {
        // Create a temporary ZIP file for testing
        Path tempFile = Files.createTempFile("test", ".zip");
        File zipFile = tempFile.toFile();
        zipFile.deleteOnExit();

        // Create a sample text file within the ZIP file
        File textFile = createTextFileInZip(zipFile, "example.txt", "This is an example text.");

        String searchedContent = "example";

        ZipContentSearcher zipContentSearcher = new ZipContentSearcher();
        ResultFile resultFile = zipContentSearcher.searchContent(zipFile, searchedContent);

        assertNotNull(resultFile);
        assertEquals(zipFile.getName(), resultFile.getName());
        assertEquals(zipFile.getPath(), resultFile.getPath());
    }

    @Test
    public void testSearchContent_FileWithoutMatchingContent() throws IOException {
        File zipFile = createJarFileWithEntry("sample.txt", "This is a sample text file.");

        String searchedContent = "notfound";

        JarContentSearcher jarContentSearcher = new JarContentSearcher();
        ResultFile resultFile = jarContentSearcher.searchContent(zipFile, searchedContent);

        Assertions.assertNull(resultFile);
    }
    @Test
    public void testSearchContent_WithEmptyZipFile() throws IOException {
        File zipFile = createEmptyZipFile();

        String searchedContent = "sample";

        ZipContentSearcher zipContentSearcher = new ZipContentSearcher();
        ResultFile resultFile = zipContentSearcher.searchContent(zipFile, searchedContent);

        Assertions.assertNull(resultFile);
    }
    @Test
    public void testSearchContent_WithInvalidFile() {
        File zipFile = new File("nonexistent.zip");
        String searchedContent = "sample";

        JarContentSearcher jarContentSearcher = new JarContentSearcher();

        // Expecting a RuntimeException to be thrown
        Assertions.assertThrows(RuntimeException.class, () ->
                jarContentSearcher.searchContent(zipFile, searchedContent));
    }

    private File createTextFileInZip(File zipFile, String entryName, String content) throws IOException {
        java.util.zip.ZipOutputStream zipOutputStream = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(zipFile));
        zipOutputStream.putNextEntry(new java.util.zip.ZipEntry(entryName));

        byte[] buffer = new byte[1024];
        int length;
        java.io.ByteArrayInputStream inputStream = new java.io.ByteArrayInputStream(content.getBytes());
        while ((length = inputStream.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, length);
        }
        zipOutputStream.closeEntry();
        inputStream.close();
        zipOutputStream.close();

        return zipFile;
    }

    private File createEmptyZipFile() throws IOException {
        File zipFile = File.createTempFile("test", ".zip");
        zipFile.deleteOnExit();

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile))) {
            // Add an empty entry to the ZIP file
            ZipEntry emptyEntry = new ZipEntry("empty.txt");
            zipOutputStream.putNextEntry(emptyEntry);
            zipOutputStream.closeEntry();
        }
        return zipFile;
    }
    private File createJarFileWithEntry(String entryName, String entryContent) throws IOException {
        File zipFile = File.createTempFile("test", ".zip");
        zipFile.deleteOnExit();

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile))) {
            ZipEntry zipEntry = new ZipEntry(entryName);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(entryContent.getBytes());
            zipOutputStream.closeEntry();
        }
        return zipFile;
    }
}
