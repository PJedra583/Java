package zad3;

public class Consumer extends Thread {
    public static boolean cons_end = false;
    public Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int counter = 1;
        Towar towar;
        while ((towar = buffer.konsumuj()) != null) {
            //    System.out.println("[K]: " + towar);
            counter++;
            if ((counter) % 100 == 0) {
                System.out.println("policzono wage " + (counter) + " obiektów");
            }
        }
    }
}