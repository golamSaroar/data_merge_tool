package com.veeva.data.reader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DataReader {
    List<Map<String, String>> readData(String filePath);
}
