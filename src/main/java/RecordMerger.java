import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecordMerger {

	private static final Logger logger = LogManager.getLogger();

	// FILENAME_COMBINED is read from a property file, which can be configured at Runtime
	// public static final String FILENAME_COMBINED = "combined.csv";

	/**
	 * Entry point of this test.
	 *
	 * @param args command line arguments: first.html and second.csv.
	 * @throws Exception bad things had happened.
	 */
	public static void main(final String[] args) throws Exception {

		if (args.length == 0) {
			System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
			System.exit(1);
		}

		// your code starts here.
		logger.info("Application started with arguments.");
	}
}
