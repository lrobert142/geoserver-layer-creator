package com.gov.aims.bulkloader;

import static org.junit.Assert.*;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gov.aims.utilities.GeoserverDataReader;
import com.gov.aims.utilities.GeoserverDataReader.FilterType;

public class GeoServerDataReaderTest {
	private static final String restUrl = "http://localhost:8080/geoserver";
	private static final String username = "admin";
	private static final String password = "geoserver";

	private static final String workspaceName = "workspaceForJUnitTest";
	private static final String datastoreName = "dataStoreForJUnitTest";
	private static final String layerName = "JUnit_test_data";
	private static final File zipFile = new File("src/test/resources/JUnit_test_data.zip");
	
	@BeforeClass
	public static void setup() {
		GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(restUrl, username, password);
		try {
			publisher.createWorkspace(workspaceName);
			publisher.publishShp(workspaceName, datastoreName, layerName, zipFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllLayersFromGeoserver() {
		try {
			GeoserverDataReader reader = new GeoserverDataReader(restUrl, username,
					password);
			assertTrue(reader.getAllLayersFromGeoserver() != null);
		} catch (MalformedURLException e) {
			fail("MalformedURLException occurred!");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetLayersFromGeoserverWithStartsFilter() {
		try {
			GeoserverDataReader reader = new GeoserverDataReader(restUrl, username, password);
			assertTrue(reader.getLayersFromGeoserver("", "", FilterType.STARTSWITH) != null);
		} catch (MalformedURLException e) {
			fail("MalformedURLException occurred!");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetLayersFromGeoserverWithContainsFilter() {
		try {
			GeoserverDataReader reader = new GeoserverDataReader(restUrl, username, password);
			assertTrue(reader.getLayersFromGeoserver("", "", FilterType.CONTAINS) != null);
		} catch (MalformedURLException e) {
			fail("MalformedURLException occurred!");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetLayersFromGeoserverWithEndsFilter() {
		try {
			GeoserverDataReader reader = new GeoserverDataReader(restUrl, username, password);
			assertTrue(reader.getLayersFromGeoserver("", "", FilterType.ENDSWITH) != null);
		} catch (MalformedURLException e) {
			fail("MalformedURLException occurred!");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetLayersFromGeoserverWithRegexFilter() {
		try {
			GeoserverDataReader reader = new GeoserverDataReader(restUrl, username, password);
			assertTrue(reader.getLayersFromGeoserver("", "", FilterType.REGEX) != null);
		} catch (MalformedURLException e) {
			fail("MalformedURLException occurred!");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetLayersFromGeoserverWithInvalidFilter() {
		try {
			GeoserverDataReader reader = new GeoserverDataReader(restUrl, username, password);
			assertTrue(reader.getLayersFromGeoserver("", "", FilterType.UNKNOWN) == null);
		} catch (MalformedURLException e) {
			fail("MalformedURLException occurred!");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllLayersInWorkspace() {
		try {
			GeoserverDataReader reader = new GeoserverDataReader(restUrl, username, password);
			assertTrue(reader.getAllLayersInWorkspace(workspaceName) != null);
		} catch (MalformedURLException e) {
			fail("MalformedURLException occurred!");
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void cleanUp() {
		GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(restUrl, username, password);
		publisher.removeWorkspace(workspaceName, true);
	}
}