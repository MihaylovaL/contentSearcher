package org.example.contentSearchers;

import org.example.model.ResultFile;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * The JarContentSearcher class is responsible for searching the content within a JAR file.
 * It implements the ContentSearcher interface.
 */
public class JarContentSearcher implements ContentSearcher {

    /**
     * Searches for the specified content within the given JAR file.
     *
     * @param jarFile         The JAR file to search within.
     * @param searchedContent The content to search for.
     * @return A ResultFile object representing the files containing the searched content.
     * @throws RuntimeException if an error occurs while searching the JAR file.
     */
    @Override
    public ResultFile searchContent(File jarFile, String searchedContent) {

        try (JarFile jF = new JarFile(jarFile)) {
            Enumeration<? extends JarEntry> entries = jF.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                if (!entry.isDirectory()) {
                    try (InputStream inputStream = jF.getInputStream(entry);
                         InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                         BufferedReader reader = new BufferedReader(inputStreamReader)) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            if (line.contains(searchedContent)) {
                                return new ResultFile(jarFile.getName(), jarFile.getPath(), jarFile.length());
                            }
                        }
                    }
                } else {
                    // Handle subdirectories within the JAR file
                    TextFileContentSearcher contentSearcherInFile = new TextFileContentSearcher();
                    File file = new File(jarFile.getAbsolutePath());
                    contentSearcherInFile.searchContent(file, searchedContent);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
