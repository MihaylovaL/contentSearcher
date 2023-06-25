package printerTest;

import org.example.model.ResultFile;
import org.example.printer.ResultPrinter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultPrinterTest {

    @Test
    public void testPrintingResult() {
        // Create a list of ResultFile objects
        List<ResultFile> files = new ArrayList<>();
        files.add(new ResultFile("file1.txt", "/path/to/file1.txt", 1024));
        files.add(new ResultFile("file2.txt", "/path/to/file2.txt", 2048));

        // Redirect System.out to capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalPrintStream = System.out;
        System.setOut(new PrintStream(outputStream));

        // Call the printingResult method
        ResultPrinter.printingResult(files);

        // Restore the original System.out
        System.setOut(originalPrintStream);

        // Normalize line separators in actual output and format it
        String actualOutput = outputStream.toString()
                .replaceAll("\\r\\n", "\n")  // Normalize line separators
                .trim()  // Remove leading and trailing whitespace
                .replaceAll("\n", System.lineSeparator());  // Format line separators

        // Define the expected output
        String expectedOutput = "File name: file1.txt" + System.lineSeparator() +
                "File path: /path/to/file1.txt" + System.lineSeparator() +
                "File size: 1024 bytes" + System.lineSeparator() +
                "File name: file2.txt" + System.lineSeparator() +
                "File path: /path/to/file2.txt" + System.lineSeparator() +
                "File size: 2048 bytes";

        // Verify the printed output
        assertEquals(expectedOutput, actualOutput);
    }
    @Test
    public void testPrintingResultWithNoContent(){
        List<ResultFile> files = new ArrayList<>();
        ResultPrinter.printingResult(files);
    }
}






