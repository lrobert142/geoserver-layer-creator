package com.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.AfterClass;
import org.junit.Test;

import com.gov.aims.utilities.GeoserverDataReader;
import com.gov.aims.utilities.GeoserverToCsvWriter;

public class GeoserverToCsvWriterTest {
	private static final String restUrl = "http://localhost/geoserver";
	private static final String username = "admin";
	private static final String password = "geoserver";
	private static final String fileName = "resources/testModify.csv";
	
	@Test
	public void testStoreLayerDataInCsv() {
		GeoserverToCsvWriter writer = new GeoserverToCsvWriter();
		try {
			GeoserverDataReader reader = new GeoserverDataReader(restUrl, username, password);
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
