package com.gov.aims.bulkloader;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gov.aims.mainapp.MainApp;

public class MainAppCreateCSVTest {
	private static MainApp app;
	private String shapeFileDirectory = "C:\\Users\\josbaldi\\Desktop\\Internship\\Project Resources\\test-datasets\\AU_GA_Maritime-boundaries";
	private String tifFileDirectory = "C:\\Users\\josbaldi\\Desktop\\Internship\\Project Resources\\test-datasets\\truecolour-small";
	
	@BeforeClass
	public static void init() {
		app = new MainApp();
	}
	
	@Test
	public void setUpShapeFiles() {
		app.setUpShapeFiles(shapeFileDirectory);
	}
	
	@Test
	public void setUpTiffFiles() {
		app.setUpTiffFiles(tifFileDirectory);
	}
}