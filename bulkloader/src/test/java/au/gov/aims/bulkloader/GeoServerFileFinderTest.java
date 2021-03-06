/**
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
 */

package au.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import au.gov.aims.utilities.FileFinder;

public class GeoServerFileFinderTest {
	static FileFinder sff;
	static Logger logger;
	static File dir;
	static FileWriter writer;

	@BeforeClass
	public static void setUp() {
		logger = Logger.getLogger(GeoServerFileFinderTest.class);
		BasicConfigurator.configure();
		sff = new FileFinder();

		dir = new File("TestResources");
		dir.mkdir();

		try {
			for (int i = 0; i < sff.SHAPEFILE_EXTENSIONS.size(); i++) {
				try {
					writer = new FileWriter(dir.getAbsolutePath() + "\\Test" + sff.SHAPEFILE_EXTENSIONS.get(i));
				} finally {
					writer.flush();
					writer.close();
				}
			}
			for (int i = 0; i < 5; i++) {
				try {
					writer = new FileWriter(dir.getAbsolutePath() + "\\Test" + i + ".tif");
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
		assertTrue(sff.findAll(dir.getAbsolutePath()).size() == 9);
	}

	@Test
	public void findFilesByExtensionListTest() {
		assertTrue(sff.findAllByExtensionList(dir.getAbsolutePath(),
				sff.SHAPEFILE_EXTENSIONS).size() == sff.SHAPEFILE_EXTENSIONS.size());
	}
	
	@Test
	public void findCsvFilesByExtensionList(){
		assertTrue(sff.findAllByExtensionList(dir.getAbsolutePath(), sff.FILE_EXTENSIONS_FOR_CSV).size() == 6);
	}

	@Test
	public void findByExtensionSingleStrTest() {
		assertTrue(sff.findAllBySingleExtension(dir.getAbsolutePath(),
				sff.SHAPEFILE_EXTENSIONS.get(0)).size() == 1);
	}

	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}
}