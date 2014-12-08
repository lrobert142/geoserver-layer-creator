/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
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
import com.gov.aims.utilities.FileFinder;
import com.gov.aims.utilities.TiffFileCsvParser;

/**
 * Handles all aspects of shape files: finding, sorting, zipping and creating a .csv
 * for uploading in one method.
 *
 */
public final class FileHandlerWrapper {
	//Attributes
	private FileFinder sff;
	private ShapeFileZipper sfz;
	private ShapeFileSorter sfs;
	private List<File> files;
	private List<List<File>> sortedFiles;
	private ShapeFileCsvParser shpParser;
	private TiffFileCsvParser tifParser;
	private Logger logger;
	
	//Constructor
	public FileHandlerWrapper(){
		sff = new FileFinder();
		sfz = new ShapeFileZipper();
		sfs = new ShapeFileSorter();
		shpParser = new ShapeFileCsvParser();
		tifParser = new TiffFileCsvParser();
		BasicConfigurator.configure();
		logger = Logger.getLogger(FileHandlerWrapper.class);
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
		
		shpParser.writeFilesToCsv(files, targetDirectory + "\\uploadLayers.csv");
		
	}
	
	/**
	 * Used as a wrapper class for carrying out all Tiff file handling activities.
	 * 
	 * @param The target directory containing Tiff files.
	 */
	public void setUpTiffFilesForUpload(String targetDirectory){
		files = new ArrayList<File>();
		
		files.addAll(sff.findAllBySingleExtension(targetDirectory, ".tif"));
		tifParser.writeFilesToCsv(files, targetDirectory + "\\uploadLayers.csv");
		
	}
	
	
	
	public List<ShapeFile> parseShapeFileUploadLayersCsvToBean(String directory){
		List<ShapeFile> shapeFileBean = new ArrayList<ShapeFile>();
		try {
			shapeFileBean = shpParser.parseShapeFileToJavaBean(directory + "\\uploadLayers.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return shapeFileBean;
	}
	
	public List<TiffFile> parseTiffFileUploadLayersCsvToBean(String directory){
		List<TiffFile> TiffFileBean = new ArrayList<TiffFile>();
		try {
			TiffFileBean = tifParser.parseTiffFileToJavaBean(directory + "\\uploadLayers.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return TiffFileBean;
	}
}