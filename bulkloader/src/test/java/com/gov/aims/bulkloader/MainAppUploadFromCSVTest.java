package com.gov.aims.bulkloader;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gov.aims.mainapp.MainApp;

public class MainAppUploadFromCSVTest {
	private static MainApp app;
	private String shapeFileDirectory = "C:\\Users\\josbaldi\\Desktop\\Internship\\Project Resources\\test-datasets\\AU_GA_Maritime-boundaries";
	private String tifFileDirectory = "C:\\Users\\josbaldi\\Desktop\\Internship\\Project Resources\\test-datasets\\truecolour-small";
	
	@BeforeClass
	public static void init() {
		app = new MainApp();
	}
	
	@Test
	public void uploadShapeFilesFromCSV() {
		assertTrue(app.uploadShapeFilesToGeoServer(shapeFileDirectory));
	}
	
	@Test
	public void uploadTifFilesFromCSV() {
		assertTrue(app.uploadTiffFilesToGeoServer(tifFileDirectory));
	}
}