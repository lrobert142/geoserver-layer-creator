package com.gov.aims.bulkloader;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gov.aims.mainapp.MainApp;

public class MainAppTest {
	private static MainApp app;
	
	@BeforeClass
	public static void init() {
		app = new MainApp();
	}
	
	//@Test
	public void setUp() {
		app.setUp("C:\\Users\\josbaldi\\Desktop\\Internship\\Project Resources\\test-datasets\\AU_GA_Maritime-boundaries");
	}
	
	@Test
	public void uploadFromCSV() {
		assertTrue(app.uploadLayersToGeoServer("C:\\Users\\josbaldi\\Desktop\\Internship\\Project Resources\\test-datasets\\AU_GA_Maritime-boundaries"));
	}
}