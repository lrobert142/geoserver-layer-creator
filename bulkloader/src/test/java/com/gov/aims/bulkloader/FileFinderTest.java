/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
 */

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

public class FileFinderTest {

	static FileFinder ff;
	static Logger logger;
	static File dir;
	static FileWriter writer;

	@BeforeClass
	public static void setUp() {
		logger = Logger.getLogger(FileFinderTest.class);
		BasicConfigurator.configure();
		ff = new FileFinder();

		dir = new File("TestResources");
		dir.mkdir();

		try {
			for (int i = 0; i < ff.SHAPEFILE_EXTENSIONS.size(); i++) {
				try{
				writer = new FileWriter(dir.getAbsolutePath() + "\\Test"
						+ ff.SHAPEFILE_EXTENSIONS.get(i));
				}finally{
					writer.flush();
					writer.close();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void findAllFilesInDirTest() {
		assertTrue(ff.findAllFilesInDir(dir.getAbsolutePath()).size() == ff.SHAPEFILE_EXTENSIONS.size());
	}

	@Test
	public void findFilesByExtensionListTest() {
		assertTrue(ff.findFilesByExtension(dir.getAbsolutePath(),
				ff.SHAPEFILE_EXTENSIONS).size() == ff.SHAPEFILE_EXTENSIONS.size());
	}

	@Test
	public void findByExtensionSingleStrTest() {
		assertTrue(ff.findFilesByExtension(dir.getAbsolutePath(),
				ff.SHAPEFILE_EXTENSIONS.get(0)).size() == 1);
	}
	

	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}

}
