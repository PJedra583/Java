package zad3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    public BlockingQueue<Towar> data;
    ReentrantLock lock = new ReentrantLock(true);
    private int buff_size;
    Condition condition;

    public Buffer(int buff_size) {
        this.data = new ArrayBlockingQueue<>(buff_size);
        this.buff_size = buff_size;
        this.condition = lock.newCondition();
    }

    public void produkuj(Towar towar){
        try {
            data.put(towar);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Towar konsumuj(){
        try {
            if (data.isEmpty() && Producent.prod_end) {
                return null;
            }
            return data.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
