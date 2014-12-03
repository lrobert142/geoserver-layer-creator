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

import com.gov.aims.utilities.ShapeFileCsvParser;
import com.gov.aims.utilities.ShapeFileSorter;
import com.gov.aims.utilities.ShapeFileZipper;
import com.gov.aims.utilities.ShapeFileFinder;

public class ShapeFileCSVParserTest {
	static String writeFile;
	static List<File> fileList;
	static ShapeFileCsvParser parser;
	static ShapeFileFinder sff;
	static ShapeFileZipper sfz;
	static ShapeFileSorter sfs;
	static File dir;
	static FileWriter writer;

	@BeforeClass
	public static void setUp() throws IOException {
		sff = new ShapeFileFinder();
		sfz = new ShapeFileZipper();
		sfs = new ShapeFileSorter();
		parser = new ShapeFileCsvParser();
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
		fileList = sff.findAllBySingleExtension(
				dir.getAbsolutePath(), ".zip");

		parser.writeShapeFilesToCSVFromList(fileList, writeFile);
	}

	
	@Test
	public void parseCSVToBeanTest() throws IOException {
		fileList = sff.findAllBySingleExtension(
				dir.getAbsolutePath(), ".zip");

		parser.writeShapeFilesToCSVFromList(fileList, writeFile);
		assertTrue(parser.parseShapeFileCSVToBeanList(writeFile).size() ==  1);
		
	}
	
	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}
	
}
