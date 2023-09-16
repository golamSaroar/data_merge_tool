package com.veeva.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProcessorTest {

    @Test
    public void testConsolidateData() {
        List<Map<String, String>> data = getDummyData();

        Map<String, Map<String, String>> consolidatedData = DataProcessor.consolidateData(data);

        assertNotNull(consolidatedData);
        assertEquals(3, consolidatedData.size());

        for (Map.Entry<String, Map<String, String>> entry : consolidatedData.entrySet()) {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());

            if (entry.getKey().equals("1")) {
                assertEquals("Saroar", entry.getValue().get("name"));
                assertEquals("28", entry.getValue().get("age"));
            }
        }
    }

    private static List<Map<String, String>> getDummyData() {
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", "1");
        map1.put("name", "Saroar");
        data.add(map1);

        Map<String, String> map2 = new HashMap<>();
        map2.put("id", "1");
        map2.put("age", "28"); // same id as map1, should be consolidated
        data.add(map2);

        Map<String, String> map3 = new HashMap<>();
        map3.put("name", "John"); // this map has no "id"
        data.add(map3);

        Map<String, String> map4 = new HashMap<>();
        map3.put("id", "2");
        map3.put("name", "Jane");
        data.add(map4);
        return data;
    }
}

