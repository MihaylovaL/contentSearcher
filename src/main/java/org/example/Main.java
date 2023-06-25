package org.example;

import org.example.contentSearchers.FileSearcher;
import org.example.model.ResultFile;
import org.example.printer.ResultPrinter;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Read the directory path and content from console input
        System.out.print("Enter the directory path and content, separated with quotes: ");
        Scanner scan = new Scanner(System.in);

        String input = scan.nextLine();

        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }

        int indexOfFirstQuote = input.indexOf('"');

        String directoryPath = input.substring(0, indexOfFirstQuote - 1);
        String searchedString = input.substring(indexOfFirstQuote + 1, input.length() - 1);

        File file = new File(directoryPath);
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("The given path is not an existing directory");
        }

        // Search for the string in the files within the directory
        FileSearcher fileSearcher = new FileSearcher();
        List<ResultFile> resultFiles = fileSearcher.searchFiles(file, searchedString);

        ResultPrinter.printingResult(resultFiles);
    }
}