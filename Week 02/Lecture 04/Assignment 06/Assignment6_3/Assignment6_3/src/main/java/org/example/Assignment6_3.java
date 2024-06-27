package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Assignment6_3{
    public void removeDuplicates(String inputFile, String outputFile, String keyFieldName){
        try (
                CSVReader reader = new CSVReader(new FileReader("res/" + inputFile));
                CSVWriter writer = new CSVWriter(new FileWriter("res/" + outputFile))
        ) {
            String[] header = reader.readNext();
            if (header == null) {
                throw new IllegalArgumentException("Input file is empty.");
            }

            writer.writeNext(header);

            int keyIndex = IntStream.range(0, header.length).filter(i -> header[i].equals(keyFieldName)).findFirst().orElse(-1);

            if (keyIndex == -1) {
                throw new IllegalArgumentException("Key field name not found in the header.");
            }

            Set<String> uniqueKeys = new HashSet<>();
            Stream<String[]> lines = reader.readAll().stream();
            lines.filter(line -> uniqueKeys.add(line[keyIndex]))
                    .forEach(writer::writeNext);

        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file '" + inputFile + "' not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
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
