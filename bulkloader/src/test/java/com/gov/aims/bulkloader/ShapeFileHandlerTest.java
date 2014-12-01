/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
*/

package com.gov.aims.bulkloader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gov.aims.model.ShapeFileHandler;
import com.gov.aims.utilities.FileFinder;

public class ShapeFileHandlerTest {
	
	static FileFinder ff;
	static ShapeFileHandler sfh;
	static File dir;
	static FileWriter writer;
	
	@BeforeClass
	public static void setUp(){
		ff = new FileFinder();
		sfh = new ShapeFileHandler();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}

	@Test
	public void sortShapeFilesTest() {
		List<File> files = new ArrayList<File>();
		files.addAll(ff.findFilesByExtension(dir.getAbsolutePath(), ff.SHAPEFILE_EXTENSIONS));
		assertTrue(sfh.sortShapeFiles(files).size() == 1);
	}
	
	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}
}
