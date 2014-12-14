/*
@author	Stuart Garrigan
@version 1.0.1
@since 11-12-2014
 */
package au.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import au.gov.aims.utilities.FileFinder;
import au.gov.aims.utilities.GeoServerFileCsvParser;
import au.gov.aims.utilities.RelativePathConvertor;
import au.gov.aims.utilities.ShapeFileSorter;

public class GeoServerFileCSVParserTest {
	static String writeFile;
	static List<File> fileList;
	static GeoServerFileCsvParser parser;
	static FileFinder ff;
	static ShapeFileSorter sfs;
	static File dir;
	static FileWriter writer;

	@BeforeClass
	public static void setUp() throws IOException {
		ff = new FileFinder();
		sfs = new ShapeFileSorter();
		parser = new GeoServerFileCsvParser();
		dir = new File("TestResources");
		dir.mkdir();
		writeFile = dir.getAbsolutePath() +  "\\testWrite.csv";

		try {
			for (int i = 0; i < ff.SHAPEFILE_EXTENSIONS.size(); i++) {
				try{
				writer = new FileWriter(dir.getAbsolutePath() + "\\Test"
						+ ff.SHAPEFILE_EXTENSIONS.get(i));
				
				} finally {
					writer.flush();
					writer.close();
				}
			}
			for (int i = 0; i < 5; i++) {
				try{
				writer = new FileWriter(dir.getAbsolutePath() + "\\Test" + i + ".tif");
						
				
				} finally {
					writer.flush();
					writer.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	//Testing absolute file path conversion.
//	@Test
//	public void test(){
//		fileList = ff.findAllByExtensionList("H:\\", ff.FILE_EXTENSIONS_FOR_CSV);
//		for(File f : fileList){
//			
//		System.out.println(RelativePathConvertor.convertToRelativePath(f.getAbsolutePath(), f.getParent()) + "/" + f.getName());
//		}
//	}

	@Test
	public void writeToCSVTest() throws IOException {
		fileList = ff.findAllByExtensionList(dir.getAbsolutePath(), ff.FILE_EXTENSIONS_FOR_CSV);
		parser.writeFilesToCsv(fileList, writeFile);
	}
	
	@Test
	public void parseCSVToBeanTest() throws IOException {
		fileList = ff.findAllByExtensionList(dir.getAbsolutePath(), ff.FILE_EXTENSIONS_FOR_CSV);

		parser.writeFilesToCsv(fileList, writeFile);
		
		assertTrue(parser.parseGeoServerFileToJavaBean(writeFile).size() ==  6);
	}
	
	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}
}