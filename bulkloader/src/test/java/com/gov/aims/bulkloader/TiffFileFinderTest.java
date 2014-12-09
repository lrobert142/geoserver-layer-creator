/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
package com.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gov.aims.utilities.FileFinder;

public class TiffFileFinderTest {
	static FileFinder ff;
	static Logger logger;
	static File dir;
	static FileWriter writer;

	@BeforeClass
	public static void setUp() {
		logger = Logger.getLogger(TiffFileFinderTest.class);
		BasicConfigurator.configure();
		ff = new FileFinder();

		dir = new File("TestResources");
		dir.mkdir();

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
	public void findAllFilesInDirTest() {
		assertTrue(ff.findAll(dir.getAbsolutePath()).size() == 5);
	}

	@Test
	public void findByExtensionSingleStrTest() {
		assertTrue(ff.findAllBySingleExtension(dir.getAbsolutePath(),
				".tif").size() == 5);
	}
	

	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}

}