package com.veeva.core;

import com.veeva.config.ConfigurationManager;
import com.veeva.data.DataProcessor;
import com.veeva.data.DataWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RecordMerger {

	private static final ConfigurationManager configManager = ConfigurationManager.getInstance();
	private static final Logger logger = LogManager.getLogger();

	 public static final String FILENAME_COMBINED = "combined.csv";

	/**
	 * Entry point of this test.
	 *
	 * @param args command line arguments: first.html and second.csv.
	 * @throws Exception bad things had happened.
	 */
	public static void main(final String[] args) throws Exception {

		if (args.length == 0) {
			System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
			logger.error("One or more filenames were expected as arguments. None given.");
			System.exit(1);
		}

		// your code starts here.
		List<String> fileNames = Arrays.asList(args);
		logger.info("Input from user: {}", String.join(", ", fileNames));

		String dataDir = configManager.getProperty("app.data.directory", "data");
		List<Map<String, String>> consolidatedData = DataProcessor.processDataFromFiles(fileNames, dataDir);

		String outputFilePath = Paths.get(dataDir, FILENAME_COMBINED).toString();
		DataWriter.writeToOutputFile(consolidatedData, outputFilePath);
	}
}
