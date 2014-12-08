/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
package com.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gov.aims.model.FileHandlerWrapper;
import com.gov.aims.utilities.FileFinder;

public class FileHandlerWrapperTest {
	static FileHandlerWrapper handler;
	static File dir;
	static FileWriter writer;
	static FileFinder sff;
	
	@BeforeClass
	public static void setUp(){
		handler = new FileHandlerWrapper();
		sff = new FileFinder();
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
			for (int i = 0; i < 5; i++) {
			try {
				writer = new FileWriter(dir.getAbsolutePath() + "\\Test" + i + ".tif");
			
			} finally{
				writer.flush();
				writer.close();
			}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void setUpShapeFilesForUpload() {
		handler.setUpShapeFilesForUpload(dir.getAbsolutePath());
		assertTrue(handler.parseShapeFileUploadLayersCsvToBean(dir.getAbsolutePath()).size() ==  1);
	}
	
	@Test
	public void setUpTiffFilesForUpload(){
		handler.setUpTiffFilesForUpload(dir.getAbsolutePath());
		assertTrue(handler.parseTiffFileUploadLayersCsvToBean(dir.getAbsolutePath()).size() == 5);
	}
	
	
	
	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}
}