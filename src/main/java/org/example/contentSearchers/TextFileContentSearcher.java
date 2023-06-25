package org.example.contentSearchers;

import org.example.model.ResultFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The TextFileContentSearcher class is responsible for searching the content within a text file.
 * It implements the ContentSearcher interface.
 */

public class TextFileContentSearcher implements ContentSearcher {

    /**
     * Searches for the specified content within the given text file.
     *
     * @param file            The text file to search within.
     * @param searchedContent The content to search for.
     * @return A ResultFile object representing the files containing the searched content.
     * @throws RuntimeException if an error occurs while searching the text file.
     */
    @Override
    public ResultFile searchContent(File file, String searchedContent) {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.contains(searchedContent)) {
                    return new ResultFile(file.getName(), file.getPath(), file.length());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
