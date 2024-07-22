package zad2;

public class Towar {
    int id;
    double weight;
    public Towar(int id, double weight){
        this.id = id;
        this.weight = weight;
    }
    public String toString() {
        return "" + id + " " + weight;
    }
}
