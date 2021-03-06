/**
@author Stuart Garrigan
@version 1.0.1
@since 11/12/14
 **/

package au.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import au.gov.aims.model.GeoServerFileHandlerWrapper;
import au.gov.aims.utilities.FileFinder;

public class GeoServerFileHandlerWrapperTest {
	static GeoServerFileHandlerWrapper handler;
	static File dir;
	static FileWriter writer;
	static FileFinder ff;
	static List<File> fileList;
	static String writeFile;

	@BeforeClass
	public static void setUp() {
		handler = new GeoServerFileHandlerWrapper();
		ff = new FileFinder();
		
		dir = new File("TestResources");
		writeFile = dir.getAbsolutePath() +  "\\upLoadLayers.csv";
		dir.mkdir();

		try {
			for (int i = 0; i < ff.SHAPEFILE_EXTENSIONS.size(); i++) {
				try {
					writer = new FileWriter(dir.getAbsolutePath() + "\\Test" + ff.SHAPEFILE_EXTENSIONS.get(i));
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
	public void setUpGeoServerFilesForUpload() {
		fileList = ff.findAllByExtensionList(dir.getAbsolutePath(), ff.FILE_EXTENSIONS_FOR_CSV);
		
		assertTrue(handler.initialWriteGeoServerFilesToCsv(fileList, writeFile) == true);
		
		handler.setUpFilesForUpload(dir.getAbsolutePath());
		assertTrue(handler.parseGeoServerFileUploadLayersCsvToBean(writeFile).size() == 6);
	}

	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			if(file.isDirectory()){
				File[] files = file.listFiles();
				for(File f : files){
					f.delete();
				}
			}
			file.delete();
		}
		dir.delete();
	}
}