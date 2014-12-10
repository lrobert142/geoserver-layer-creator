package com.gov.aims.bulkloader;

import static org.junit.Assert.*;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.gov.aims.utilities.GeoserverDataUpdater;
import com.gov.aims.model.ShapeFile;
import com.gov.aims.utilities.ShapeFileCsvParser;

public class GeoserverDataUpdaterTest {
	private static final String restUrl = "http://localhost:8080/geoserver";
	private static final String username = "admin";
	private static final String password = "geoserver";
	private static final String fileName = "src/test/resources/testModifyData.csv";

	@Test
	public void testUpdateAllData() {
		GeoserverDataUpdater updater = new GeoserverDataUpdater();
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
