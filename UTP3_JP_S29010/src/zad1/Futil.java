package zad1;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil {

    static String result;
    public static FileVisitor<Path> wizytator = new FileVisitor<Path>() {
        @Override
        public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
            if (path.getFileName().toString().matches(".*.txt")) {
                try( BufferedWriter pisarz = new BufferedWriter
                        (new OutputStreamWriter(new FileOutputStream(result,true),"UTF-8"));){
                    BufferedReader czytnik = new BufferedReader
                            (new InputStreamReader(new FileInputStream(path.toString()),"windows-1250"));
                    String line = "";
                    while ((line = czytnik.readLine()) != null) {
                        // System.out.println(line);
                        pisarz.write(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path path, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    };
    public static void processDir(String dirIn,String dirOut) {
        Path path = Paths.get(dirIn);
        result = dirOut;
        try {
            Files.walkFileTree(path, wizytator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
