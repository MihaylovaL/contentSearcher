package unitlsTest;

import org.example.unitls.ExtensionChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExtensionCheckerTest {
    @Test
    public void testIsDocxFile() {
        // Test with a .docx file
        File docxFile = new File("document.docx");
        Assertions.assertTrue(ExtensionChecker.isDocxFile(docxFile));

        // Test with a file that doesn't have a .docx extension
        File txtFile = new File("text.txt");
        Assertions.assertFalse(ExtensionChecker.isDocxFile(txtFile));
    }

    @Test
    public void testIsZipFile() {
        // Test with a .zip file
        File zipFile = new File("archive.zip");
        Assertions.assertTrue(ExtensionChecker.isZipFile(zipFile));

        // Test with a file that doesn't have a .zip extension
        File txtFile = new File("text.txt");
        Assertions.assertFalse(ExtensionChecker.isZipFile(txtFile));
    }

    @Test
    public void testIsJarFile() {
        // Test with a .jar file
        File jarFile = new File("library.jar");
        Assertions.assertTrue(ExtensionChecker.isJarFile(jarFile));

        // Test with a file that doesn't have a .jar extension
        File txtFile = new File("text.txt");
        Assertions.assertFalse(ExtensionChecker.isJarFile(txtFile));
    }
    @Test
    public void testIsMDFile() {
        // Test with a .jar file
        File mdFile = new File("library.md");
        Assertions.assertTrue(ExtensionChecker.isMDFile(mdFile));

        // Test with a file that doesn't have a .md extension
        File txtFile = new File("text.txt");
        Assertions.assertFalse(ExtensionChecker.isMDFile(txtFile));
    }
}
