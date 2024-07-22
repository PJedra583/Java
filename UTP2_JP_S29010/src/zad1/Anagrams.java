/**
 *
 *  @author Jędra Piotr S29010
 *
 */

package zad1;


import java.io.*;
import java.util.*;

public class Anagrams {
    //!IMPORTANT zwrócić liste w postaci alfabetycznej
    List <String> wordslist;
    List <List<String>> anagramslist;
    public Anagrams(String s) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(s)));
            this.wordslist = new ArrayList<>();
            String s2 = "";
            while ((s2 = br.readLine()) != null)
            {
                for (String s3:s2.split("\\s+")) {
                    wordslist.add(s3);
                }
            }

        } catch (IOException e) {
            System.err.println("FILE NOT FOUND");
        }
    }
    public List<List<String>> getSortedByAnQty(){
        // this.anagrams porównujemy czy da się utworzyć słowa
        List<List<String>> list = new ArrayList<>();
        List<String> anagramy = new ArrayList<>();
        String word;
        char[] tab;
        boolean found = false;
        boolean unqiue = true;
        //Pętla odpowiadająca za Słowo które będzie porównywane
        for (int i = 0; i < wordslist.size(); i++) {
            word = wordslist.get(i);
            anagramy = new ArrayList<>();
            //Pętla odpowiadająca za Słowo z którym będzie porównywane
            for (int j = 0; j < wordslist.size(); j++) {
                tab = wordslist.get(j).toCharArray();
                found = true;
                //Pętla odpowiadająca za każdą litere w pierwszym słowie
                for (int k = 0; k < word.length(); k++) {
                    // Pętla odpowiadająca za każdą litere z drugiego słowa
                    for (int l = 0; l < tab.length; l++) {
                        if (tab.length == word.length()) {
                            if (word.charAt(k) == tab[l]) {
                                tab[l] = '_';
                            }
                        }
                    }
                }
                for (int m = 0; m < tab.length; m++) {
                    if (tab[m] != '_') {
                        found = false;
                    }
                }
                if (found) {
                    anagramy.add(wordslist.get(j));
                }
            }
            //Kryterium nie uwzględione na SKDP
            anagramy.sort(((o1, o2) -> o1.compareTo(o2)));
            list.add(anagramy);
        }
        return viewAnagrams(list);
    }
    public String getAnagramsFor(String word){
        List<List<String>> tmp1 = anagramslist;
        List<String> tmp = null;
        boolean contains = false;
        for (List<String> l:tmp1) {
                if (l.contains(word)) {
                    contains = true;
                     tmp = l;
                }
        }
        if (contains) {
            tmp.remove(word);
            return word + ": " + tmp;
        } else {
            if (wordslist.contains(word)){
                tmp = new ArrayList<>();
                return word + ": " + tmp;
            } else return word + ": " + tmp;
        }
    }
    public List<List<String>> viewAnagrams(List<List<String>> list1) {
        List<List<String>> uniqueLists = new ArrayList<>();
        //Wyelimnowanie nieunikalnych list
        for (List<String> l : list1) {
            boolean unique = true;
            for (List<String> uniqueList : uniqueLists) {
                if (l.get(0).equals(uniqueList.get(0))) {
                    unique = false;
                }
            }
            if (unique) {
                uniqueLists.add(l);
            }
        }
        //Segregacja list
        uniqueLists.sort(new Comparator<List<String>>() {
            @Override
            public int compare(List<String> o1, List<String> o2) {
                if (o1.size() == o2.size()) {
                    return o1.get(0).compareTo(o2.get(0));
                } else return Integer.compare(o2.size(),o1.size());
            }
        });
        this.anagramslist = uniqueLists;
        return uniqueLists;
    }
}
