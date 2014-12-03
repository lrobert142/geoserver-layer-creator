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

import com.gov.aims.utilities.ShapeFileFinder;
import com.gov.aims.utilities.ShapeFileSorter;

public class ShapeFileSorterTest {
	
	static ShapeFileFinder sff;
	static ShapeFileSorter sfs;
	static File dir;
	static FileWriter writer;
	
	@BeforeClass
	public static void setUp(){
		sff = new ShapeFileFinder();
		sfs = new ShapeFileSorter();

		dir = new File("TestResources");
		dir.mkdir();

		try {
			for (int i = 0; i < sff.SHAPEFILE_EXTENSIONS.size(); i++) {
				try{
				writer = new FileWriter(dir.getAbsolutePath() + "\\Test"
						+ sff.SHAPEFILE_EXTENSIONS.get(i));
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
		files.addAll(sff.findAllByExtensionList(dir.getAbsolutePath(), sff.SHAPEFILE_EXTENSIONS));
		assertTrue(sfs.sortShapeFiles(files).size() == 1);
	}
	
	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}
}
