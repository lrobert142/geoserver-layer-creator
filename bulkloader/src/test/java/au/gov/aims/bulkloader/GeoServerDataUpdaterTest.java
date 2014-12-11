package au.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;

import org.junit.Test;

import au.gov.aims.model.GeoServerFile;
import au.gov.aims.utilities.GeoServerDataUpdater;
import au.gov.aims.utilities.GeoServerFileCsvParser;

public class GeoServerDataUpdaterTest {
	private static final String restUrl = "http://localhost:8080/geoserver";
	private static final String username = "admin";
	private static final String password = "geoserver";
	private static final String fileName = "/testModifyData.csv";

	@Test
	public void testUpdateAllData() {
		GeoServerDataUpdater updater = new GeoServerDataUpdater();
		GeoServerFileCsvParser parser = new GeoServerFileCsvParser();
		try {
			GeoServerRESTReader reader = new GeoServerRESTReader(restUrl, username, password);
			GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(restUrl, username, password);
			List<GeoServerFile> dataSets = parser.parseGeoServerFileToJavaBean(fileName);
			assertTrue(updater.updateAllData(reader, publisher, dataSets, fileName));
		} catch (IOException e) {
			fail("An exception occurred!");
			e.printStackTrace();
		}
	}
}