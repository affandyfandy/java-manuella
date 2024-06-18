import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Lab1 {
    public static void readFileAndWriteToFile(String inputFileName, String outputFileName) throws FileNotFoundException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("res/" + inputFileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter("res/" + outputFileName))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public static void main(String[] args) {
        try {
            String inputFileName = "test1.txt";
            String outputFileName = "test2.txt";
            
            readFileAndWriteToFile(inputFileName, outputFileName);
            
            System.out.println("Content from " + inputFileName + " successfully written to " + outputFileName);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
