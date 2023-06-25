package org.example.printer;

import org.example.model.ResultFile;

import java.util.List;

public class ResultPrinter {
    public static void printingResult(List<ResultFile> files) {
        if(files.isEmpty()){
            System.out.println("No files with matching content were found.");
            return;
        }
        for (ResultFile file : files) {
            System.out.println("File name: " + file.getName());
            System.out.println("File path: " + file.getPath());
            System.out.println("File size: " + file.getSize() + " bytes");
        }
    }
}
