package au.gov.aims.bulkloader;

import org.junit.BeforeClass;
import org.junit.Test;

import au.gov.aims.model.UploadManger;

public class MainAppCreateCSVTest {
	private static UploadManger app;
	private String shapeFileDirectory = "C:\\Users\\josbaldi\\Desktop\\Internship\\Project Resources\\test-datasets\\AU_GA_Maritime-boundaries";
	private String tifFileDirectory = "C:\\Users\\josbaldi\\Desktop\\Internship\\Project Resources\\test-datasets\\truecolour-small";
	
	@BeforeClass
	public static void init() {
		app = new UploadManger();
	}
	
	//@Test
	public void setUpShapeFiles() {
		app.setUpShapeFiles(shapeFileDirectory);
	}
	
	//@Test
	public void setUpTiffFiles() {
		app.setUpTiffFiles(tifFileDirectory);
	}
}