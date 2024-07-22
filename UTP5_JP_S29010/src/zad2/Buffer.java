package zad2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    ReentrantLock lock = new ReentrantLock(true);
    private List<Towar> data;
    private int buff_size;
    Condition condition;

    public Buffer(int buff_size) {
        this.data = new ArrayList<>(buff_size);
        this.buff_size = buff_size;
        this.condition = lock.newCondition();
    }

    public void produkuj(Towar towar){
        try{
            lock.lock();
            while (data.size() == buff_size) {
                condition.await();
            }
            data.add(towar);
            condition.signal();
        }
        catch (InterruptedException e) {

        }finally {
            lock.unlock();
        }
    }

    public Towar konsumuj(){
        try {
            lock.lock();
            if (Producent.prod_end && data.isEmpty()) {
                return null;
            }
            while (data.isEmpty()) {
                condition.await();
            }
            condition.signal();
            Towar result = data.get(0);
            data.remove(0);
            return result;
        } catch (InterruptedException e) {}
        finally {
            lock.unlock();
        }
        return null;
    }
}
