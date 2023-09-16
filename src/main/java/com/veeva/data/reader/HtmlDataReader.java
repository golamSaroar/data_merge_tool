package com.veeva.data.reader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlDataReader implements DataReader {
    /**
     * Reads data from a HTML file and returns it as a list of maps.
     *
     * @param filePath The path to the HTML file.
     * @return A list of maps representing the HTML table data.
     */
    @Override
    public List<Map<String, String>> readData(String filePath) {
        // store all rows (using map for column name: value) of a table in a list
        List<Map<String, String>> dataList = new ArrayList<>();

        try {
            // parse the html file using jsoup
            Document document = Jsoup.parse(new File(filePath), "UTF-8");

            // find the table with the id "directory"
            Element directoryTable = document.select("table#directory").first();

            if (directoryTable != null) {
                // get the table headers (these will be used as column names)
                Elements headers = directoryTable.select("th");
                List<String> columnNames = new ArrayList<>();
                for (Element header : headers) {
                    columnNames.add(header.text());
                }

                // iterate through table rows (skipping the header row)
                Elements rows = directoryTable.select("tr:gt(0)");
                for (Element row : rows) {
                    Elements columns = row.select("td");

                    // create a data record (Map) for each row
                    Map<String, String> dataRecord = new HashMap<>();

                    for (int i = 0; i < columns.size(); i++) {
                        String columnName = columnNames.get(i).toLowerCase();
                        String columnValue = columns.get(i).text().replaceAll("\u00a0", "");
                        dataRecord.put(columnName, columnValue);
                    }

                    // add row to the list
                    dataList.add(dataRecord);
                }
            } else {
                throw new IllegalStateException("Table with id 'directory' not found in the HTML file");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Error reading HTML file", e);
        }

        return dataList;
    }
}
