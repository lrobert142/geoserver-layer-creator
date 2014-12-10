package au.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;

import org.junit.Test;

import au.gov.aims.model.ShapeFile;
import au.gov.aims.utilities.GeoServerDataUpdater;
import au.gov.aims.utilities.ShapeFileCsvParser;

public class GeoServerDataUpdaterTest {
	private static final String restUrl = "http://localhost:8080/geoserver";
	private static final String username = "admin";
	private static final String password = "geoserver";
	private static final String fileName = "/testModifyData.csv";

	@Test
	public void testUpdateAllData() {
		GeoServerDataUpdater updater = new GeoServerDataUpdater();
		ShapeFileCsvParser parser = new ShapeFileCsvParser();
		try {
			GeoServerRESTReader reader = new GeoServerRESTReader(restUrl, username, password);
			GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(restUrl, username, password);
			List<ShapeFile> dataSets = parser.parseShapeFileToJavaBean(fileName);
			assertTrue(updater.updateAllData(reader, publisher, dataSets, fileName));
		} catch (IOException e) {
			fail("An exception occurred!");
			e.printStackTrace();
		}
	}
}