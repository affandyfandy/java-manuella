package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RemoveDuplicate {
    public static void removeDuplicates(String inputFile, String outputFile, String keyFieldName) {
        try (
                CSVReader reader = new CSVReader(new FileReader("res/" + inputFile));
                CSVWriter writer = new CSVWriter(new FileWriter("res/" + outputFile))
        ) {
            String[] header = reader.readNext();
            if (header == null) {
                throw new IllegalArgumentException("Input file is empty.");
            }

            writer.writeNext(header);

            int keyIndex = -1;
            for (int i = 0; i < header.length; i++) {
                if (header[i].equals(keyFieldName)) {
                    keyIndex = i;
                    break;
                }
            }

            if (keyIndex == -1) {
                throw new IllegalArgumentException("Key field name not found in the header.");
            }

            Set<String> seenKeys = new HashSet<>();
            String[] line;

            while ((line = reader.readNext()) != null) {
                if (keyIndex < line.length) {
                    String keyFieldValue = line[keyIndex].trim();
                    if (!seenKeys.contains(keyFieldValue)) {
                        seenKeys.add(keyFieldValue);
                        writer.writeNext(line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file '" + inputFile + "' not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
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