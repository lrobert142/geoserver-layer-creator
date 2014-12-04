/**
@author Justin Osbaldiston
@version 1.0.0
@since 3/12/14
**/

package com.gov.aims.bulkloader;

import static org.junit.Assert.*;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gov.aims.model.GeoServerManager;

public class GeoServerManagerTest {
	private static GeoServerManager manager;
	private static GeoServerRESTPublisher publisher;
	private String testWorkspaceName = "testWorkspace";
	private String testStoreName = "testStore";
	
	@BeforeClass
	public static void init() throws MalformedURLException {
		manager = new GeoServerManager("http://localhost:8080/geoserver/", "admin", "geoserver");
		publisher = new GeoServerRESTPublisher("http://localhost:8080/geoserver/", "admin", "geoserver");
	}
	
	@After
	public void deleteWorkspace() {
		if(manager.checkForWorkspace(testWorkspaceName)) {
			publisher.removeWorkspace(testWorkspaceName, true);
			assertFalse(manager.checkForWorkspace(testWorkspaceName));
		}
	}

	//@Test
	public void createWorkspaceTest() throws MalformedURLException {
		assertTrue(manager.createWorkspace(testWorkspaceName));
		assertTrue(manager.checkForWorkspace(testWorkspaceName));
	}
	
	//@Test
	public void createStoreTest() throws MalformedURLException {
		assertTrue(manager.createWorkspace(testWorkspaceName));
		assertTrue(manager.checkForWorkspace(testWorkspaceName));
		assertTrue(manager.createDataStore(testWorkspaceName, testStoreName));
		assertTrue(manager.checkForDataStore(testWorkspaceName, testStoreName));
	}
	
	@Test
	public void uploadToServerTest() throws IllegalArgumentException, IOException {
		File fileToUpload = new File("C:\\Users\\josbaldi\\Desktop\\Internship\\Project Resources\\test-datasets\\AU_GA_Maritime-boundaries\\cs_poly.zip");
		File prjFile = new File("C:\\Users\\josbaldi\\Desktop\\Internship\\Project Resources\\test-datasets\\AU_GA_Maritime-boundaries\\cs_poly.prj");
		//assertTrue(manager.createWorkspace(testWorkspaceName));
		//assertTrue(manager.createDataStore(testWorkspaceName, testStoreName));
		assertTrue(manager.upload(testWorkspaceName, testStoreName, fileToUpload, prjFile, "PNG Fish", "This is the abstract", null, "keyword1,keyword2", "Boundaries/Australian Maritime Boundaries (GA)"));
	}
}