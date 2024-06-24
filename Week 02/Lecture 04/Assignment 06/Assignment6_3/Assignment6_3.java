import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.*;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class Assignment6_3{
    public void removeDuplicates(String inputFile, String outputFile, String keyFieldName){
        BufferedReader reader = null;
        PrintWriter writer = null;

        try{
            reader = new BufferedReader(new FileReader("res/" + inputFile));
            writer = new PrintWriter(new FileWriter("res/" + outputFile));

            String line;
            Map<String, String> seenKeys = new HashMap<>();

            String header = reader.readLine();
            writer.println(header);

            String[] headers = header.split(",");
            final int keyIndex = getKeyIndex(headers, keyFieldName);

            if (keyIndex == -1) {
                throw new IllegalArgumentException("Key field name not found in the header.");
            }
            
            List<String> lines = reader.lines().toList();

            Set<String> uniqueKeys = new HashSet<>();
            List<String> noDuplicate = lines.stream()
                                            .filter(l -> {
                                                String[] fields = l.split(",");
                                                return uniqueKeys.add(fields[keyIndex]);
                                            })
                                            .collect(Collectors.toList());

            for (String uniqueLine : noDuplicate) {
                writer.println(uniqueLine);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file '" + inputFile + "' not found.");
            e.printStackTrace();
        } catch (IOException e) {
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

    private int getKeyIndex(String[] headers, String keyFieldName) {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equals(keyFieldName)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Assignment6_3 processing = new Assignment6_3();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input file name: ");
        String inputFile = scanner.nextLine();

        System.out.print("Output file name: ");
        String outputFile = scanner.nextLine();

        System.out.print("Key field: ");
        String keyFieldName = scanner.nextLine();

        scanner.close();

        processing.removeDuplicates(inputFile, outputFile, keyFieldName);

        System.out.println("Duplicates removed successfully. Output written to " + outputFile);
    }
}
