
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class DocumentProcessor {
    private Set<String> stopWords;

    // Constructor
    public DocumentProcessor(String stopWordsFilePath) throws IOException {
        this.stopWords = loadStopWords(stopWordsFilePath);
    }

    private Set<String> loadStopWords(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .map(String::trim) // trim whitespace from each line
                .map(String::toLowerCase) // convert to lowercase
                .collect(Collectors.toSet());
    }

    // Process document
    public List<String> processDocument(String documentText) {

        // Remove leading digit (document ID)
        documentText = documentText.replaceFirst("^\\d+[,\\s]*", "");
        documentText = documentText.replaceAll("\\d", " "); // Replace digits with spaces
        documentText = documentText.replaceAll("[^a-zA-Z\\s]", ""); // non-alphanumeric characters

        // Split the text into words, convert to lowercase, and filter stop words
        return Arrays.stream(documentText.toLowerCase() // Convert to lowercase
                .split("\\s+")) // Split by whitespace
                .map(word -> word.replaceFirst("^\\d+", "")) // Remove leading digits from words
                .filter(word -> !stopWords.contains(word)) // Remove stop words
                .filter(word -> !word.isEmpty()) // Remove empty strings
                .collect(Collectors.toList()); // Collect into a List
    }

    public Map<Integer, List<String>> processDocuments(String csvFilePath) throws IOException {
        Map<Integer, List<String>> documentIndex = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            int docID = 1; // ID starts at 1

            while ((line = br.readLine()) != null) {
                // Split the line by a comma, first part is ID
                String[] parts = line.split(",", 2); // Split into Document ID and Content
                if (parts.length > 1) {
                    String documentText = parts[1]; // to getcontent form the second part
                    List<String> processedWords = processDocument(documentText);
                    documentIndex.put(docID++, processedWords); // Map docID to the processed words
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("CSV file not found: " + e.getMessage());
        }

        return documentIndex;
    }
}