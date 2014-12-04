package com.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gov.aims.model.ShapeFileHandlerWrapper;
import com.gov.aims.utilities.ShapeFileFinder;

public class ShapeFileHandlerWrapperTest {
	static ShapeFileHandlerWrapper handler;
	static File dir;
	static FileWriter writer;
	static ShapeFileFinder sff;
	
	@BeforeClass
	public static void setUp(){
		handler = new ShapeFileHandlerWrapper();
		sff = new ShapeFileFinder();
		dir = new File("TestResources");
		dir.mkdir();

		try {
			for (int i = 0; i < sff.SHAPEFILE_EXTENSIONS.size(); i++) {
				try{
				writer = new FileWriter(dir.getAbsolutePath() + "\\Test"
						+ sff.SHAPEFILE_EXTENSIONS.get(i));
				} finally {
					writer.flush();
					writer.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void setUpShapeFilesForUploadTest(){
		handler.setUpShapeFilesForUpload(dir.getAbsolutePath());
	}
	
	@Test
	public void parseUploadLayersCsvToBeanTest() {
		handler.setUpShapeFilesForUpload(dir.getAbsolutePath());
		assertTrue(handler.parseUploadLayersCsvToBean(dir.getAbsolutePath()).size() ==  2);
	}
	
	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}
}