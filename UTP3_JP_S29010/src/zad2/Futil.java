package zad2;

import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

public class Futil {

    public static void processDir(String dirIn, String dirOut) {
        String s = "";
        try (Stream<Path> stream = Files.walk(Paths.get(dirIn))) {
            BufferedWriter pisarz = new BufferedWriter
                    (new OutputStreamWriter(new FileOutputStream(dirOut,true),"UTF-8"));
            stream.filter(Files::isRegularFile).filter(path -> path.getFileName().toString().matches(".*.txt"))
                    .forEach(path -> {
                        try {
                            BufferedReader czytnik = new BufferedReader
                                    (new InputStreamReader(new FileInputStream(path.toString()), "windows-1250"));
                            String line;
                            while ( (line = czytnik.readLine()) != null) {
                                pisarz.write(line);
                            }
                        } catch (IOException e) {
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

