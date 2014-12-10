/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
package au.gov.aims.bulkloader;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import au.gov.aims.utilities.FileFinder;
import au.gov.aims.utilities.TiffFileCsvParser;

public class TiffFileCsvParserTest {
	static String writeFile;
	static List<File> fileList;
	static TiffFileCsvParser parser;
	static FileFinder ff;
	static File dir;
	static FileWriter writer;

	@BeforeClass
	public static void setUp() throws IOException {
		fileList = new ArrayList<File>();
		ff = new FileFinder();
		parser = new TiffFileCsvParser();
		dir = new File("TestResources");
		dir.mkdir();
		writeFile = dir.getAbsolutePath() +  "\\testWrite.csv";
		
		try {
			for (int i = 0; i < 5; i++) {
				try{
				writer = new FileWriter(dir.getAbsolutePath() + "\\Test"
						+ i + ".tif");
				} finally {
					writer.flush();
					writer.close();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void writeToCSVTest() throws IOException {
		fileList = ff.findAllBySingleExtension(dir.getAbsolutePath(), ".tif");
		parser.writeFilesToCsv(fileList, writeFile);
	}
	
	@Test
	public void parseCSVToBeanTest() throws IOException {
		fileList = ff.findAllBySingleExtension(dir.getAbsolutePath(), ".tif");

		parser.writeFilesToCsv(fileList, writeFile);
		assertTrue(parser.parseTiffFileToJavaBean(writeFile).size() ==  5);
	}
	
	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}
}
