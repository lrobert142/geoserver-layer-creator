package au.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import au.gov.aims.model.UploadManger;

public class UploadManagerUploadFromCSVTest {
	private static UploadManger uploadManager;
	private static String geoServerFileDirectory = "/testData";
	private static File zipDirectory;
	private static String filesDirectory;
	
	@BeforeClass
	public static void init() {
		uploadManager = new UploadManger("http://localhost:8080/geoserver/", "admin", "geoserver");
		filesDirectory = UploadManagerCreateCSVTest.class.getResource(geoServerFileDirectory).toString().substring(6);
	}
	
	@Test
	public void uploadGeoServerFilesFromCSV() {
		uploadManager.setUpFiles(filesDirectory);
		zipDirectory = new File(filesDirectory + "\\GS_LAYER_UPLOADER_ZIPS");
		assertTrue(zipDirectory.exists());
		assertTrue(uploadManager.uploadGeoServerFilesToGeoServer(filesDirectory + "\\uploadLayersFILLEDIN.csv"));
	}
	
	@AfterClass
	public static void close() {
		uploadManager.deleteWorkspace("workspace");
	}
}