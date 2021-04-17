package com.lukk.utilities;

import java.util.*;
import java.util.stream.Collectors;

public class MapUtils {

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {

        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<>();

        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static List<Map.Entry<String, Double>> limitMapAndReturnList(Map<String, Double> map, int limit) {
        List<Map.Entry<String, Double>> list = map.entrySet().stream().limit(limit).collect(Collectors.toList());
        return list;
    }
}