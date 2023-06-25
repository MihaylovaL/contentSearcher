package org.example.contentSearchers;

import org.example.model.ResultFile;
import org.example.unitls.ExtensionChecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The FileSearcher class provides functionality to search for content within files and directories.
 * It supports various file types including ZIP, JAR, text files, and DOCX files.
 */
public class FileSearcher {
    private ZipContentSearcher zipContentSearcher;
    private JarContentSearcher jarContentSearcher;
    private TextFileContentSearcher textFileContentSearcher;
    private DocContentSearcher docContentSearcher;
    private List<ResultFile> resultFiles;

    /**
     * Constructs a new FileSearcher instance.
     * Initializes the necessary ContentSearcher implementations and the list of ResultFile objects.
     */
    public FileSearcher() {
        zipContentSearcher = new ZipContentSearcher();
        jarContentSearcher = new JarContentSearcher();
        textFileContentSearcher = new TextFileContentSearcher();
        docContentSearcher = new DocContentSearcher();
        resultFiles = new ArrayList<>();
    }

   /* public ZipContentSearcher getZipContentSearcher() {
        return zipContentSearcher;
    }

    public void setZipContentSearcher(ZipContentSearcher zipContentSearcher) {
        this.zipContentSearcher = zipContentSearcher;
    }

    public JarContentSearcher getJarContentSearcher() {
        return jarContentSearcher;
    }

    public void setJarContentSearcher(JarContentSearcher jarContentSearcher) {
        this.jarContentSearcher = jarContentSearcher;
    }

    public TextFileContentSearcher getTextFileContentSearcher() {
        return textFileContentSearcher;
    }

    public void setTextFileContentSearcher(TextFileContentSearcher textFileContentSearcher) {
        this.textFileContentSearcher = textFileContentSearcher;
    }

    public DocContentSearcher getDocContentSearcher() {
        return docContentSearcher;
    }

    public void setDocContentSearcher(DocContentSearcher docContentSearcher) {
        this.docContentSearcher = docContentSearcher;
    }

    public List<ResultFile> getResultFiles() {
        return resultFiles;
    }

    public void setResultFiles(List<ResultFile> resultFiles) {
        this.resultFiles = resultFiles;
    }
*/
    /**
     * Searches for files and directories within the specified directory, and its subdirectories if any,
     * that contain the specified content.
     *
     * @param directory       the directory to search within
     * @param searchedContent the content to search for
     * @return a list of ResultFile objects representing the files and directories that match the search criteria
     */
    public List<ResultFile> searchFiles(File directory, String searchedContent) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (ExtensionChecker.isZipFile(file)) {
                        var resulFile = zipContentSearcher.searchContent(file, searchedContent);
                        if(resultFiles != null) {
                            resultFiles.add(resulFile);
                        }
                    } else if (ExtensionChecker.isJarFile(file)) {
                        var resultFile = jarContentSearcher.searchContent(file, searchedContent);
                        if(resultFile != null) {
                            resultFiles.add(resultFile);
                        }
                    } else if (file.isFile()) {
                        if (ExtensionChecker.isDocxFile(file)) {
                            var resultFile = docContentSearcher.searchContent(file, searchedContent);
                            if (resultFile != null) {
                                resultFiles.add(docContentSearcher.searchContent(file, searchedContent));
                            }
                        } else {
                            var resultFile = textFileContentSearcher.searchContent(file, searchedContent);
                            if(resultFile != null) {
                                resultFiles.add(resultFile);
                            }
                        }
                    } else if (file.isDirectory()) {
                        searchFiles(file, searchedContent); // Recursively explore subdirectories
                    }
                }
            }
        } else if (directory.isFile() && ExtensionChecker.isZipFile(directory) && ExtensionChecker.isJarFile(directory)) {
            zipContentSearcher.searchContent(directory, searchedContent);
        }
        return resultFiles;
    }
}