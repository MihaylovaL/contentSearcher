package org.example.contentSearchers;

import org.example.model.ResultFile;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * The ZipContentSearcher class is responsible for searching the content within a ZIP file.
 * It implements the ContentSearcher interface.
 */
public class ZipContentSearcher implements ContentSearcher {

    /**
     * Searches for the specified content within the given ZIP file.
     *
     * @param zipFile         The ZIP file to search within.
     * @param searchedContent The content to search for.
     * @return A ResultFile object representing the files containing the searched content.
     * @throws RuntimeException if an error occurs while searching the ZIP file.
     */
    @Override
    public ResultFile searchContent(File zipFile, String searchedContent) {

        try (ZipFile zf = new ZipFile(zipFile)) {
            Enumeration<? extends ZipEntry> entries = zf.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                if (!entry.isDirectory()) {
                    try (InputStream inputStream = zf.getInputStream(entry);
                         InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                         BufferedReader reader = new BufferedReader(inputStreamReader)) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            if (line.contains(searchedContent)) {
                                return new ResultFile(zipFile.getName(), zipFile.getPath(), zipFile.length());
                            }
                        }
                    }
                } else {
                    // Handle subdirectories within the ZIP file
                    TextFileContentSearcher contentSearcherInFile = new TextFileContentSearcher();
                    File file = new File(zipFile.getAbsolutePath());
                    contentSearcherInFile.searchContent(file, searchedContent);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
