package zad1;

import zad1.Buffer;
import zad1.Consumer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(500);

        //Odczyt z pliku
        LinkedList<Towar> list = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("../Towary.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(new Towar(Integer.parseInt(line.split("\\s+")[0]),
                        Double.parseDouble(line.split("\\s+")[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Błąd przy odczycie z pliku");
        }
        Producent producer = new Producent(buffer,list);
        Consumer consumer = new Consumer(buffer);
        producer.start();
        consumer.start();
    }
}