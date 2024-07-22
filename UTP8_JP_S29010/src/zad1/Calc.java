package zad1;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Calc{
    private final Map<String, Operator> operations;
    public Calc(){
        operations = new HashMap<>();
        operations.put("+", BigDecimal::add);
        operations.put("-", BigDecimal::subtract);
        operations.put("*", BigDecimal::multiply);
        operations.put("/", new Operator() {
            @Override
            public BigDecimal apply(BigDecimal num1, BigDecimal num2) {
                return num1.divide(num2, 7, 4);
            }
        });
    }
    public String doCalc(String s){
        String[] tab = s.split("\\s+");
        BigDecimal num1 = new BigDecimal(tab[0]);
        BigDecimal num2 = new BigDecimal(tab[2]);
        Operator op = operations.get(tab[1]);
        return op.apply(num1,num2) + "";
    }
    private interface Operator {
        BigDecimal apply(BigDecimal num1, BigDecimal num2);
    }
}