/**
@author Justin Osbaldiston
@version 1.0.0
@since 4/12/14
**/

package au.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import au.gov.aims.model.UploadManager;

public class UploadManagerCreateCSVTest {
	private static UploadManager uploadManager;
	private static String geoServerFileDirectory = "/testData";
	private static File csv;
	private static String filesDirectory;
	
	@BeforeClass
	public static void init() {
		uploadManager = new UploadManager();
		uploadManager.login("http://localhost:8080/geoserver/", "admin", "geoserver");
		filesDirectory = UploadManagerCreateCSVTest.class.getResource(geoServerFileDirectory).toString().substring(6);
	}
	
	@Test
	public void writeCSV() {
		uploadManager.writeCSV(filesDirectory);
		csv = new File(getClass().getResource(geoServerFileDirectory + "/uploadLayers.csv").toString().substring(6));
		assertTrue(csv.exists());
	}
	
	@AfterClass
	public static void close() {
		csv.delete();
		assertFalse(csv.exists());
	}
}