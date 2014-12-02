package com.gov.aims.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.gov.aims.utilities.FileFinder;
import com.gov.aims.utilities.FileZipper;

/**
 * Handles all aspects of shape files: finding, sorting, zipping and creating a .csv
 * for uploading in one method.
 *
 */
public class OneStopShapeFileHandler {
	
	//Attributes
	private FileFinder ff;
	private FileZipper fz;
	private ShapeFileHandler sfh;
	private List<File> files;
	private List<List<File>> sortedFiles;
	private CsvHandler ch;
	private Logger logger;
	
	//Constructor
	public OneStopShapeFileHandler(){
		ff = new FileFinder();
		fz = new FileZipper();
		sfh = new ShapeFileHandler();
		ch = new CsvHandler();
		BasicConfigurator.configure();
		logger = Logger.getLogger(OneStopShapeFileHandler.class);
	}

	/**
	 * Used as a wrapper class for carrying out all shape file handling activities.
	 * 
	 * @param The target directory containing shape files.
	 */
	public void setUpShapeFilesForUpload(String targetDirectory){
		files = new ArrayList<File>();
		sortedFiles = new ArrayList<List<File>>();
		
		files.addAll(ff.findFilesByExtension(targetDirectory, ff.SHAPEFILE_EXTENSIONS));
		sortedFiles.addAll(sfh.sortShapeFiles(files));
		
		files.clear();
		
		fz.zipSortedShapeFiles(sortedFiles, targetDirectory);
		files.addAll(ff.findFilesByExtension(targetDirectory, ".zip"));
		
		try {
			ch.writeShapeFilesToCSVFromList(files, targetDirectory + "\\uploadLayers.csv");
		} catch (IOException e) {
			logger.debug(e.getStackTrace().toString() + "An error occured whilst trying to write to a .csv");
		}
		
	}
	
	public List<ShapeFile> parseUploadLayersCsvToBean(String directory){
		List<ShapeFile> shapeFileBean = new ArrayList<ShapeFile>();
		try {
			shapeFileBean = ch.parseShapeFileCSVToBeanList(directory + "\\uploadLayers.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shapeFileBean;
	}
}
