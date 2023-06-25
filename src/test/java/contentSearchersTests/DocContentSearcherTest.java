package contentSearchersTests;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.example.contentSearchers.DocContentSearcher;
import org.example.model.ResultFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocContentSearcherTest {

    @Test
    public void testSearchContent_FileWithMatchingContent() throws IOException {
        DocContentSearcher docContentSearcher = new DocContentSearcher();
        File file = createDocxFileWithEntry("sample.docx", "This is a sample text file.");
        String searchedContent = "sample";

        ResultFile resultFile = docContentSearcher.searchContent(file, searchedContent);

        Assertions.assertNotNull(resultFile);
        assertEquals(file.getName(), resultFile.getName());
        assertEquals(file.getPath(), resultFile.getPath());
    }

    @Test
    public void testSearchContent_FileWithoutMatchingContent() throws IOException {
        DocContentSearcher docContentSearcher = new DocContentSearcher();
        File file = createDocxFileWithEntry("other.docx", "This is a sample text file.");
        String searchedContent = "example";

        ResultFile resultFile = docContentSearcher.searchContent(file, searchedContent);

        Assertions.assertNull(resultFile);
    }

    @Test
    public void testSearchContent_UnsupportedFileFormat() throws IOException {
        DocContentSearcher docContentSearcher = new DocContentSearcher();
        File file = createDocxFileWithEntry("other.pdf", "This is a sample text file.");
        String searchedContent = "example";

        ResultFile resultFile = docContentSearcher.searchContent(file, searchedContent);

        Assertions.assertNull(resultFile);
    }
    private File createDocxFileWithEntry(String fileName, String content) throws IOException {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(content);

        FileOutputStream out = new FileOutputStream(fileName);
        document.write(out);
        out.close();

        return new File(fileName);
    }
}
