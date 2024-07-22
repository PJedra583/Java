package zad2;
import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
public class Main2 {
    public static void main(String[] args) {
        List<String> classes = new ArrayList<>();
        Path path = Paths.get("C:\\Users\\Acer\\IdeaProjects\\UTP_JBDC\\out\\production\\UTP_JBDC\\Current");
        try {
            //Uzupełnienie klas
            Files.walk(path)
                    .filter(e -> e.getFileName().toString()
                            .matches(".*\\.class"))
                    .map(c -> c.getFileName().toString())
                    .map(s -> s.replaceAll("\\.class$", ""))
                    .filter(s -> !s.contains("$"))
                    .map(s -> "Current."+s )
                    .forEach(classes::add);
        } catch (IOException e) {}
        for (String s: classes){
            System.out.println("====================");
            System.out.println("KLASA: " + s);
            try {
                    Class c = Class.forName(s);
                    System.out.println("Nadklasa: " + c.getSuperclass());

                    Constructor[] konstruktory = c.getDeclaredConstructors();

                    System.out.print("Konstruktory: ");
                    for (Constructor konstruktor : konstruktory) {
                        if (konstruktor.getParameters().length > 0) {
                            System.out.print(konstruktor + " ");
                        }
                    }
                    System.out.println();
                    System.out.print("Nie-prywatne metody: ");

                for (Method m: c.getDeclaredMethods()) {
                    if (!m.toString().matches("private.*")) {
                        System.out.print(m + " ");
                    }
                }
                System.out.println();
                System.out.print("Publiczne pola: ");
                for (Field f: c.getDeclaredFields()) {
                    if (f.toString().matches("public.*")) {
                        System.out.print(f + " ");
                    }
                }
                System.out.println();
                System.out.print("Pola nadklasy: ");
                Class c2 = c.getSuperclass();
                for (Field f: c2.getDeclaredFields()) {
                        System.out.print(f + " ");
                }
                } catch (ClassNotFoundException e) {
                    System.out.println(e);
                }
            System.out.println();
            }
            Class c = Main2.class;
            Object o = null;
        for (Constructor k: c.getDeclaredConstructors()) {
            if (k.getParameters().length > 0) {
                try {
                    o = k.newInstance("Java Refleksja");
                    break;
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        Main2 m = (Main2) o;
        for (Method method: m.getClass().getDeclaredMethods()) {
            try {
                if (method.getParameters().length == 1) {
                    if (method.getParameters()[0].getType() == String.class) {
                        String test = " Example";
                        method.invoke(o, test);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public int x;
    private int y;
    private String s;
    public Main2(String s) {
        this.s = s;
    }
    public void meth(String s1){
        System.out.println(this.s + s1);
    }
    private void priv_meth(){
        System.out.println("How did u call me?");
    }
}