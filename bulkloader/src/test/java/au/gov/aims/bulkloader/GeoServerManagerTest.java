/**
@author Justin Osbaldiston
@version 1.0.0
@since 3/12/14
**/

package au.gov.aims.bulkloader;

import static org.junit.Assert.*;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;

import java.io.File;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import au.gov.aims.model.GeoServerManager;

public class GeoServerManagerTest {
	private static GeoServerManager manager;
	private static GeoServerRESTPublisher publisher;
	private String testWorkspaceName = "testWorkspace";
	private String testStoreName = "testStore";
	
	@BeforeClass
	public static void init() {
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

	@Test
	public void createWorkspaceTest() {
		assertTrue(manager.createWorkspace(testWorkspaceName));
		assertTrue(manager.checkForWorkspace(testWorkspaceName));
	}
	
	@Test
	public void createDataStoreTest() {
		assertTrue(manager.createWorkspace(testWorkspaceName));
		assertTrue(manager.checkForWorkspace(testWorkspaceName));
		assertTrue(manager.createDataStore(testWorkspaceName, testStoreName));
		assertTrue(manager.checkForDataStore(testWorkspaceName, testStoreName));
	}
	
	@Test
	public void uploadShapeFileTest() {
		File fileToUpload = new File(getClass().getResource("/cs_poly.zip").toString().substring(5));
		assertTrue(manager.uploadShapeFile(testWorkspaceName, testStoreName, fileToUpload, "PNG Fish", "This is the abstract", null, "keyword1,keyword2", "Boundaries/Australian Maritime Boundaries (GA)"));
		assertTrue(manager.checkForLayer(testWorkspaceName, "cs_poly"));
	}
	
	@Test
	public void uploadRasterFileTest() {
		File fileToUpload = new File(getClass().getResource("/L5097067_06719900709.tif").toString().substring(5));
		assertTrue(manager.uploadGeoTIFFFile(testWorkspaceName, testStoreName, "testLayer", fileToUpload, "TiffFile", "Abstract", null, "keyword1,keyword2", "Boundaries/Australian Maritime Boundaries (GA)"));
		assertTrue(manager.checkForLayer(testWorkspaceName, "testLayer"));
	}
}