import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class XList<T> extends ArrayList<T> {
    // Konstruktor XList przyjmujący argumenty podane przez przecinki
    public XList(T... elements) {
        super(Arrays.asList(elements));
    }

    // Metoda fabryczna of dla tworzenia XList z innej kolekcji
    public static <T> XList of(Collection<T> collection) {
        return new XList<Object>(collection.toArray());
    }

    // Metoda fabryczna of dla tworzenia XList z tablicy
    public static <T> XList<T> of(T[] array) {
        return new XList<>(array);
    }

    // Metoda ofChars - zwraca XListę znaków napisu
    public static XList<Character> ofChars(String str) {
        char[] charArray = str.toCharArray();
        Character[] charObjects = new Character[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            charObjects[i] = charArray[i];
        }
        return new XList<>(charObjects);
    }

    // Metoda ofTokens - zwraca XListę symboli napisu, rozdzielonych separatorem sep
    public static XList<String> ofTokens(String str, String sep) {
        String[] tokens = str.split(sep);
        return new XList<>(tokens);
    }

    // Metoda union - suma elementów
    public XList<T> union(Collection<T> other) {
        XList<T> result = new XList<>(this);
        result.addAll(other);
        return result;
    }

    // Metoda diff - różnica elementów
    public XList<T> diff(Collection<T> other) {
        XList<T> result = new XList<>(this);
        result.removeAll(other);
        return result;
    }

    // Metoda unique - bez duplikatów
    public XList<T> unique() {
        return new XList<>(new HashSet<>(this));
    }

    // Metoda combine - kombinacje elementów z różnych kolekcji
    public static <T> XList<XList<T>> combine(XList<T>... lists) {
        XList<XList<T>> result = new XList<>();
        int[] indexes = new int[lists.length];
        while (true) {
            XList<T> combination = new XList<>();
            for (int i = 0; i < lists.length; i++) {
                combination.add(lists[i].get(indexes[i]));
            }
            result.add(combination);

            int k = lists.length - 1;
            while (k >= 0 && (indexes[k] + 1 >= lists[k].size())) {
                k--;
            }
            if (k < 0) {
                break;
            }
            indexes[k]++;
            for (int j = k + 1; j < lists.length; j++) {
                indexes[j] = 0;
            }
        }
        return result;
    }

    // Metoda collect - wyniki funkcji stosowanej do elementów
    public <R> XList<R> collect(Function<T, R> mapper) {
        XList<R> result = new XList<>();
        for (T element : this) {
            result.add(mapper.apply(element));
        }
        return result;
    }

    // Metoda join - łączenie elementów z separatorem
    public String join(String separator) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            if (i > 0) {
                result.append(separator);
            }
            result.append(get(i).toString());
        }
        return result.toString();
    }

    // Metoda forEachWithIndex - iterowanie po liście z dostępem do elementów i ich indeksów
    public void forEachWithIndex(BiConsumer<T, Integer> consumer) {
        for (int i = 0; i < size(); i++) {
            consumer.accept(get(i), i);
        }
    }
}
