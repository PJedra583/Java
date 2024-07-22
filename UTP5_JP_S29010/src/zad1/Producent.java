package zad1;
import java.util.LinkedList;

public class Producent extends Thread {
    private Buffer buffer;

    public LinkedList<Towar> data;
    public static boolean prod_end = false;

    public Producent(Buffer buffer,LinkedList<Towar> data) {
        this.buffer = buffer;
        this.data = data;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < data.size(); i++) {
              buffer.produkuj(data.get(i));
             //   System.out.println("[P]: " + data.get(i));
                // i-1 ponieważ zaczynamy od 0 i przy i = 199 mamy już 200
                if ((i+1) % 200 == 0) {
                    System.out.println("utworzono " + (i+1) +" obiektów");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Producent Kończy prace
        prod_end = true;
    }
}