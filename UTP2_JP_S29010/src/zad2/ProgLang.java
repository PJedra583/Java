package zad2;

import java.io.*;
import java.util.*;
import java.util.function.*;

public class ProgLang {

    Map<String,List<String>> langsMap;
    public ProgLang(String nazwaPliku){
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(nazwaPliku)));
            String s;
            String[] wysplitowane;
            List<String> list;
            this.langsMap = new LinkedHashMap<>();
            while ((s=br.readLine()) != null) {
                wysplitowane = s.split("\\s+");
                list = new ArrayList<>();
                for (int i = 1; i < wysplitowane.length; i++) {
                    if (!list.contains(wysplitowane[i])) {
                        list.add(wysplitowane[i]);
                    }
                }
                langsMap.put(wysplitowane[0],list);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Map<String,List<String>> getLangsMap(){
            return this.langsMap;
    }
    public Map<String, List<String>> getProgsMap() {
        Map<String,List<String>> result = new LinkedHashMap<>();
        List<String> programmers = new ArrayList();
        String[] tab;
        for (Map.Entry<String, List<String>> e : this.langsMap.entrySet()) {
            for (String s:e.getValue()) {
                result.put(s,new ArrayList<>());
            }
        }

        for (Map.Entry<String,List<String>> e: this.langsMap.entrySet()) {
            for (Map.Entry<String,List<String>> e2 : result.entrySet()) {
                if (e.getValue().contains(e2.getKey())) {
                    e2.getValue().add(e.getKey());
                }
                }
        }
        return result;
    }
    public Map<String,List<String>> getLangsMapSortedByNumOfProgs() {
        Map<String, List<String>> result = new LinkedHashMap<>();
        List<Map.Entry<String,List<String>>> list = new ArrayList<>();
        for (Map.Entry<String, List<String>> e : this.langsMap.entrySet()) {
            list.add(e);
        }
        list.sort(new Comparator<Map.Entry<String, List<String>>>() {
            @Override
            public int compare(Map.Entry<String, List<String>> o1, Map.Entry<String, List<String>> o2) {
                if (o1.getValue().size() == o2.getValue().size()) {
                    return o1.getKey().compareTo(o2.getKey());
                } else return o2.getValue().size() - o1.getValue().size();
            }
        });
        for (Map.Entry<String, List<String>> e: list) {
            result.put(e.getKey(),e.getValue());
        }
        return result;
    }
    public Map<String,List<String>> getProgsMapSortedByNumOfLangs() {
        Map<String, List<String>> result = new LinkedHashMap<>();
        List<Map.Entry<String,List<String>>> list = new ArrayList<>();
        for (Map.Entry<String, List<String>> e : this.getProgsMap().entrySet()) {
            list.add(e);
        }
        list.sort(new Comparator<Map.Entry<String, List<String>>>() {
            @Override
            public int compare(Map.Entry<String, List<String>> o1, Map.Entry<String, List<String>> o2) {
                if (o1.getValue().size() == o2.getValue().size()) {
                    return o1.getKey().compareTo(o2.getKey());
                } else return o2.getValue().size() - o1.getValue().size();
            }
        });
        for (Map.Entry<String, List<String>> e: list) {
            result.put(e.getKey(),e.getValue());
        }
        return result;
    }
    public Map<String,List<String>> getProgsMapForNumOfLangsGreaterThan(int n){
        Map<String, List<String>> result = new LinkedHashMap<>();
        List<Map.Entry<String,List<String>>> list = new ArrayList<>();
        List<Map.Entry<String,List<String>>> tmp = new ArrayList<>();
        for (Map.Entry<String, List<String>> e : this.getProgsMap().entrySet()) {
            list.add(e);
            tmp.add(e);
        }
        for (Map.Entry<String,List<String>> e: tmp) {
            if (e.getValue().size() <= n) {
                list.remove(e);
            }
        }
        for (Map.Entry<String,List<String>> e: list) {
            result.put(e.getKey(),e.getValue());
        }
        return result;
    }
    public <T,R> Map<T,R> sorted(Map<T,R> map, Comparator<Map.Entry<T,R>> comparator){
        Map<T, R> result = new LinkedHashMap<>();
        List<Map.Entry<T,R>> list = new ArrayList<>();
        for (Map.Entry<T, R> e : map.entrySet()) {
            list.add(e);
        }
        list.sort(comparator);
        for (Map.Entry<T, R> e : list) {
            result.put(e.getKey(),e.getValue());
        }
        return result;
    }
    public static <T, R> Map<T, R> filtered(Map<T, R> map, Predicate<Map.Entry<T, R>> pred) {
        List<Map.Entry<T, R>> list = new ArrayList<>();
        for (Map.Entry<T, R> e : map.entrySet()) {
            list.add(e);
        }
        for (Map.Entry<T, R> e : list) {
            if (!pred.test(e)) {
                map.remove(e.getKey(), e.getValue());
            }
        }
        return map;
    }
}