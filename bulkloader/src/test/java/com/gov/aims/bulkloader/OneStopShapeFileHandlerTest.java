package com.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gov.aims.model.OneStopShapeFileHandler;
import com.gov.aims.utilities.FileFinder;

public class OneStopShapeFileHandlerTest {
	
	static OneStopShapeFileHandler handler;
	static File dir;
	static FileWriter writer;
	static FileFinder ff;
	
	@BeforeClass
	public static void setUp(){
		handler = new OneStopShapeFileHandler();
		ff = new FileFinder();
		dir = new File("TestResources");
		dir.mkdir();

		try {
			for (int i = 0; i < ff.SHAPEFILE_EXTENSIONS.size(); i++) {
				try{
				writer = new FileWriter(dir.getAbsolutePath() + "\\Test"
						+ ff.SHAPEFILE_EXTENSIONS.get(i));
				}finally{
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
	public void parseUploadLayersCsvToBeanTest(){
		handler.setUpShapeFilesForUpload(dir.getAbsolutePath());
		assertTrue(handler.parseUploadLayersCsvToBean(dir.getAbsolutePath()).size() ==  1);
	}
	
	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}

}
