package zad1;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
    private List<Towar> data;
    private int buff_size;

    public Buffer(int buff_size) {
        this.data = new ArrayList<>(buff_size);
        this.buff_size = buff_size;
    }

    public synchronized void produkuj(Towar towar) throws InterruptedException {
        while (data.size() == buff_size) {
            wait();
        }
        data.add(towar);
        notifyAll();
    }

    public synchronized Towar konsumuj() throws InterruptedException {
        if (Producent.prod_end && data.isEmpty()) {
            return null;
        }
        while (data.isEmpty()) {
            wait();
        }
        notifyAll();
        Towar result = data.get(0);
        data.remove(0);
        return result;
    }
}
