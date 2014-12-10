package au.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.AfterClass;
import org.junit.Test;

import au.gov.aims.utilities.GeoServerDataReader;
import au.gov.aims.utilities.GeoServerToCSVWriter;;

public class GeoServerToCSVWriterTest {
	private static final String restUrl = "http://localhost:8080/geoserver";
	private static final String username = "admin";
	private static final String password = "geoserver";
	private static final String fileName = "/testModifyData.csv";
	
	@Test
	public void testStoreLayerDataInCsv() {
		GeoServerToCSVWriter writer = new GeoServerToCSVWriter();
		try {
			GeoServerDataReader reader = new GeoServerDataReader(restUrl, username, password);
			assertTrue(writer.storeLayerDataInCsv(reader.getAllLayersFromGeoserver(), reader, fileName));
		} catch (MalformedURLException e) {
			fail("An exception occurred!");
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void cleanup() {
		File file = new File(fileName);
		file.delete();
	}
}