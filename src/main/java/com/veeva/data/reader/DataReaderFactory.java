package com.veeva.data.reader;

import com.veeva.exceptions.UnsupportedDataFormatException;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The DataReaderFactory class is responsible for creating DataReader instances
 * based on the file format specified by the file path.
 */
public class DataReaderFactory {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Creates a DataReader instance based on the provided file extension.
     *
     * @param filePath The path to the data file, from which the file extension will be extracted.
     * @return A DataReader instance suitable for reading the specified file format.
     * @throws UnsupportedDataFormatException If the file format is not supported.
     */
    public static DataReader createDataReader(String filePath) throws UnsupportedDataFormatException {
        String fileExtension = FilenameUtils.getExtension(filePath).toLowerCase();

        switch (fileExtension) {
            case "html":
                return new HtmlDataReader();
            case "csv":
                return new CsvDataReader();
            default:
                // TODO: add support for more formats, i.e. XML, TXT
                logger.info("User attempted to use an unsupported file format: {}", fileExtension);
                throw new UnsupportedDataFormatException(
                        "Unsupported file format: " + fileExtension);
        }
    }
}
