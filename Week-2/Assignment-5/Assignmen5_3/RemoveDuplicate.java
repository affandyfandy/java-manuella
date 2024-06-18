import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RemoveDuplicate {

    public static void removeDuplicates(String inputFile, String outputFile, String keyFieldName) {
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader("res/" + inputFile));
            writer = new PrintWriter(new FileWriter("res/" + outputFile));

            String line;
            Map<String, String> seenKeys = new HashMap<>();

            String header = reader.readLine();
            writer.println(header);

            int keyIndex = -1;
            String[] headers = header.split(",");

            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equals(keyFieldName)) {
                    keyIndex = i;
                    break;
                }
            }

            if (keyIndex == -1) {
                throw new IllegalArgumentException("Key field name not found in the header.");
            }

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length > keyIndex) {
                    String keyFieldValue = fields[keyIndex].trim();

                    if (!seenKeys.containsKey(keyFieldValue)) {
                        seenKeys.put(keyFieldValue, line);
                        writer.println(line);
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("Error: Input file '" + inputFile + "' not found.");
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input file name: ");
        String inputFile = scanner.nextLine();

        System.out.print("Output file name: ");
        String outputFile = scanner.nextLine();

        System.out.print("Key field: ");
        String keyFieldName = scanner.nextLine();

        scanner.close();

        removeDuplicates(inputFile, outputFile, keyFieldName);

        System.out.println("Duplicates removed successfully. Output written to " + outputFile);
    }
}
