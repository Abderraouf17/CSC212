import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Project {

    public static void main(String[] args) {
        try {
            // stop path
            String stopWordsFilePath = "C:\\Users\\abd40\\OneDrive\\Desktop\\CSC212\\CSC212\\Project\\src\\stop.txt";
            // cvs path
            String csvFilePath = "C:\\Users\\abd40\\OneDrive\\Desktop\\CSC212\\CSC212\\Project\\src\\dataset.csv";
            DocumentProcessor processor = new DocumentProcessor(stopWordsFilePath);

            Map<Integer, List<String>> documentIndex = processor.processDocuments(csvFilePath);
            for (Map.Entry<Integer, List<String>> entry : documentIndex.entrySet()) {
                System.out.println("ID: " + entry.getKey() + " >>> " + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }
}
