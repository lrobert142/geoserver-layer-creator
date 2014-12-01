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

import com.gov.aims.model.ShapeFileHandler;
import com.gov.aims.utilities.FileFinder;
import com.gov.aims.utilities.FileZipper;

public class FileZipperTest {
	
	static FileFinder ff;
	static FileZipper fz;
	static ShapeFileHandler sfh;
	static File dir;
	static FileWriter writer;
	
	@BeforeClass
	public static void setUp(){
		ff = new FileFinder();
		fz = new FileZipper();
		sfh = new ShapeFileHandler();
		
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
	public void zipListOfShapeFilesTest() {
		List<File> files = new ArrayList<File>();
		List<List<File>> sortedFiles = new ArrayList<List<File>>();
		
		files.addAll(ff.findFilesByExtension(dir.getAbsolutePath(), ff.SHAPEFILE_EXTENSIONS));
		sortedFiles.addAll(sfh.sortShapeFiles(files));
		
		fz.zipSortedShapeFiles(sortedFiles, dir.getAbsolutePath());
	}
	
	@AfterClass
	public static void cleanUp() {
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();
	}

}
