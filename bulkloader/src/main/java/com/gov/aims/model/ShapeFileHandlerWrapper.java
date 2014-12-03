package com.gov.aims.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.gov.aims.utilities.ShapeFileCsvParser;
import com.gov.aims.utilities.ShapeFileSorter;
import com.gov.aims.utilities.ShapeFileZipper;
import com.gov.aims.utilities.ShapeFileFinder;

/**
 * Handles all aspects of shape files: finding, sorting, zipping and creating a .csv
 * for uploading in one method.
 *
 */
public final class ShapeFileHandlerWrapper {
	
	//Attributes
	private ShapeFileFinder sff;
	private ShapeFileZipper sfz;
	private ShapeFileSorter sfs;
	private List<File> files;
	private List<List<File>> sortedFiles;
	private ShapeFileCsvParser parser;
	private Logger logger;
	
	//Constructor
	public ShapeFileHandlerWrapper(){
		sff = new ShapeFileFinder();
		sfz = new ShapeFileZipper();
		sfs = new ShapeFileSorter();
		parser = new ShapeFileCsvParser();
		BasicConfigurator.configure();
		logger = Logger.getLogger(ShapeFileHandlerWrapper.class);
	}

	/**
	 * Used as a wrapper class for carrying out all shape file handling activities.
	 * 
	 * @param The target directory containing shape files.
	 */
	public void setUpShapeFilesForUpload(String targetDirectory){
		files = new ArrayList<File>();
		sortedFiles = new ArrayList<List<File>>();
		
		files.addAll(sff.findAllByExtensionList(targetDirectory, sff.SHAPEFILE_EXTENSIONS));
		sortedFiles.addAll(sfs.sortShapeFiles(files));
		
		files.clear();
		
		sfz.zipSortedShapeFiles(sortedFiles, targetDirectory);
		files.addAll(sff.findAllBySingleExtension(targetDirectory, ".zip"));
		
		parser.writeFilesToCsv(files, targetDirectory + "\\uploadLayers.csv");
		
	}
	
	public List<ShapeFile> parseUploadLayersCsvToBean(String directory){
		List<ShapeFile> shapeFileBean = new ArrayList<ShapeFile>();
		try {
			shapeFileBean = parser.parseShapeFileToJavaBean(directory + "\\uploadLayers.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shapeFileBean;
	}
}
