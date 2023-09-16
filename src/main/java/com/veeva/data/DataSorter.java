package com.veeva.data;

import java.util.List;
import java.util.Map;

public class DataSorter {
    public static List<Map<String, String>> sortByID(List<Map<String, String>> dataList) {
        dataList.sort((map1, map2) -> {
            String id1 = map1.get("id");
            String id2 = map2.get("id");

            // handle null or empty values by moving them to the end
            if (id1 == null || id1.isEmpty()) {
                return 1;
            }
            if (id2 == null || id2.isEmpty()) {
                return -1;
            }

            // compare values based on whether they are integers or not
            if (isInteger(id1) && isInteger(id2)) {
                // both values are integers, compare them as integers
                return Integer.compare(Integer.parseInt(id1), Integer.parseInt(id2));
            } else {
                // one or both values are not integers, compare them as strings
                return id1.compareTo(id2);
            }
        });

        return dataList;
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
