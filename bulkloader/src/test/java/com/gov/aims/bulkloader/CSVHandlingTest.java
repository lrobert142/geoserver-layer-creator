/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
 */
package com.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gov.aims.model.CsvHandler;
import com.gov.aims.model.ShapeFileHandler;
import com.gov.aims.utilities.FileFinder;
import com.gov.aims.utilities.FileZipper;

public class CSVHandlingTest {
	static String writeFile;
	static List<File> fileList;
	static CsvHandler ch;
	static FileFinder ff;
	static FileZipper fz;
	static ShapeFileHandler sfh;
	static File dir;
	static FileWriter writer;

	@BeforeClass
	public static void setUp() throws IOException {
		ff = new FileFinder();
		fz = new FileZipper();
		sfh = new ShapeFileHandler();
		ch = new CsvHandler();
		dir = new File("TestResources");
		dir.mkdir();
		writeFile = dir.getAbsolutePath() +  "\\testWrite.csv";

		try {
			writer = new FileWriter(dir.getAbsolutePath() + "\\Test.gs.shp.zip");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			writer.flush();
			writer.close();
		}

	}

	@Test
	public void writeToCSVTest() throws IOException {
		fileList = ff.findFilesByExtension(
				dir.getAbsolutePath(), ".zip");

		ch.writeShapeFilesToCSVFromList(fileList, writeFile);
	}

	
	@Test
	public void parseCSVToBeanTest() throws IOException {
		fileList = ff.findFilesByExtension(
				dir.getAbsolutePath(), ".zip");

		ch.writeShapeFilesToCSVFromList(fileList, writeFile);
		assertTrue(ch.parseShapeFileCSVToBeanList(writeFile).size() ==  1);
		
	}
	
	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}
	
}
