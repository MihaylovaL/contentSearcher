/**
 * The ContentSearcher interface provides logic for classes that implement content searching functionality.
 * It defines a method for searching content within a file and returning a ResultFile object.
 */
package org.example.contentSearchers;

import org.example.model.ResultFile;

import java.io.File;

public interface ContentSearcher {
    /**
     * Searches for the specified content within the given file and returns a ResultFile object
     * that match the search criteria.
     *
     * @param file            the file to search within
     * @param searchedContent the content to search for
     * @return a ResultFile object that match the search criteria
     */
    ResultFile searchContent(File file, String searchedContent);
}
