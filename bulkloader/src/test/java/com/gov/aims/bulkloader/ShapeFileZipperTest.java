/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
*/

package com.gov.aims.bulkloader;

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
import com.gov.aims.utilities.ShapeFileZipper;

public class ShapeFileZipperTest {
	
	static ShapeFileFinder sff;
	static ShapeFileZipper sfz;
	static ShapeFileSorter sfh;
	static File dir;
	static FileWriter writer;
	
	@BeforeClass
	public static void setUp(){
		sff = new ShapeFileFinder();
		sfz = new ShapeFileZipper();
		sfh = new ShapeFileSorter();
		
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
	public void zipListOfShapeFilesTest() {
		List<File> files = new ArrayList<File>();
		List<List<File>> sortedFiles = new ArrayList<List<File>>();
		
		files.addAll(sff.findAllByExtensionList(dir.getAbsolutePath(), sff.SHAPEFILE_EXTENSIONS));
		sortedFiles.addAll(sfh.sortShapeFiles(files));
		
		sfz.zipSortedShapeFiles(sortedFiles, dir.getAbsolutePath());
	}
	
	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}

}