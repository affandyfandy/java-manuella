package org.example;

import java.io.*;
import java.util.List;

public class FileUtils {
    public static void printRootPath() {
        String rootPath = System.getProperty("user.dir");
        System.out.println("Root path: " + rootPath);
    }

    public static List<String> readFile(String path) throws IOException{
        List<String> lines;
        try(BufferedReader reader = new BufferedReader(new FileReader((path)))){
            lines = reader.lines().toList();
        }
        return lines;
    }

    public static void writeFile(String path, List<String> lines) throws IOException{
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))){
            for (String line: lines){
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
