package org.example.contentSearchers;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.example.model.ResultFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The DocContentSearcher class implements the ContentSearcher interface that specializes in
 * searching for content within .docx files.
 */
public class DocContentSearcher implements ContentSearcher {

    /**
     * Searches for the specified content within the given .docx file and returns a ResultFile object
     * that match the search criteria.
     *
     * @param file            the .docx file to search within
     * @param searchedContent the content to search for
     * @return a ResultFile object that match the search criteria
     */
    @Override
    public ResultFile searchContent(File file, String searchedContent) {
        try (FileInputStream fis = new FileInputStream(file)) {
            if (file.getName().endsWith(".docx")) {
                // Read .docx file
                XWPFDocument document = new XWPFDocument(fis);
                XWPFWordExtractor extractor = new XWPFWordExtractor(document);
                String text = extractor.getText();
                if (text.contains(searchedContent)) {
                   return new ResultFile(file.getName(), file.getPath(), file.length());
                }
            } else {
                System.out.println("Unsupported file format.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
