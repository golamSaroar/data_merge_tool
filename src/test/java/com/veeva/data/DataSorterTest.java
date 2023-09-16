package com.veeva.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DataSorterTest {
    private List<Map<String, String>> dataList;

    @BeforeEach
    void setUp() {
        dataList = new ArrayList<>();
    }

    @Test
    void testSortByIDWithEmptyList() {
        List<Map<String, String>> sortedList = DataSorter.sortByID(dataList);
        assertEquals(0, sortedList.size());
    }

    @Test
    void testSortByIDWithNullID() {
        // should work without error, null id will be at the bottom
        Map<String, String> dataMap1 = new HashMap<>();
        dataMap1.put("id", null);
        dataList.add(dataMap1);

        Map<String, String> dataMap2 = new HashMap<>();
        dataMap2.put("id", "1");
        dataList.add(dataMap2);

        List<Map<String, String>> sortedList = DataSorter.sortByID(dataList);

        assertEquals(2, sortedList.size());
        assertEquals("1", sortedList.get(0).get("id"));
        assertNull(sortedList.get(1).get("id"));
    }

    @Test
    void testSortByIDWithValidData() {
        Map<String, String> dataMap1 = new HashMap<>();
        dataMap1.put("id", "3");
        dataList.add(dataMap1);

        Map<String, String> dataMap2 = new HashMap<>();
        dataMap2.put("id", "1");
        dataList.add(dataMap2);

        Map<String, String> dataMap3 = new HashMap<>();
        dataMap3.put("id", "2");
        dataList.add(dataMap3);

        List<Map<String, String>> sortedList = DataSorter.sortByID(dataList);

        // Check if the list is sorted in ascending order of "ID" values
        assertEquals("1", sortedList.get(0).get("id"));
        assertEquals("2", sortedList.get(1).get("id"));
        assertEquals("3", sortedList.get(2).get("id"));
    }
}
