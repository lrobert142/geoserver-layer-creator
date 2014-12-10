/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
 */
package au.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import au.gov.aims.utilities.FileFinder;
import au.gov.aims.utilities.ShapeFileCsvParser;
import au.gov.aims.utilities.ShapeFileSorter;
import au.gov.aims.utilities.ShapeFileZipper;

public class ShapeFileCSVParserTest {
	static String writeFile;
	static List<File> fileList;
	static ShapeFileCsvParser parser;
	static FileFinder sff;
	static ShapeFileZipper sfz;
	static ShapeFileSorter sfs;
	static File dir;
	static FileWriter writer;

	@BeforeClass
	public static void setUp() throws IOException {
		sff = new FileFinder();
		sfz = new ShapeFileZipper();
		sfs = new ShapeFileSorter();
		parser = new ShapeFileCsvParser();
		dir = new File("TestResources");
		dir.mkdir();
		writeFile = dir.getAbsolutePath() +  "\\testWrite.csv";

		try {
			writer = new FileWriter(dir.getAbsolutePath() + "\\Test.gs.shp.zip");
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			writer.flush();
			writer.close();
		}
	}

	@Test
	public void writeToCSVTest() throws IOException {
		fileList = sff.findAllBySingleExtension(dir.getAbsolutePath(), ".zip");
		parser.writeFilesToCsv(fileList, writeFile);
	}
	
	@Test
	public void parseCSVToBeanTest() throws IOException {
		fileList = sff.findAllBySingleExtension(dir.getAbsolutePath(), ".zip");

		parser.writeFilesToCsv(fileList, writeFile);
		assertTrue(parser.parseShapeFileToJavaBean(writeFile).size() ==  1);
	}
	
	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}
}