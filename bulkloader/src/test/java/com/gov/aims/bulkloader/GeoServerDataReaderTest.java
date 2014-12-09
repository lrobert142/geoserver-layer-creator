package com.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import org.junit.Test;

import com.gov.aims.utilities.GeoserverDataReader;

public class GeoServerDataReaderTest {
	private static final String restUrl = "http://localhost/geoserver";
	private static final String username = "admin";
	private static final String password = "geoserver";

	@Test
	public void testGetAllLayersFromGeoserver() {
		try {
			GeoserverDataReader reader = new GeoserverDataReader(restUrl, username,
					password);
			assertTrue(reader.getAllLayersFromGeoserver() != null);
		} catch (MalformedURLException e) {
			fail("An exception occurred!");
			e.printStackTrace();
		}
	}

}
