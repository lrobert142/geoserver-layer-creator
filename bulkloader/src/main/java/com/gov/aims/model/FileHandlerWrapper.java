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
	public static List<File> files;
	public static List<List<File>> sortedFiles;
	private ShapeFileCsvParser shpParser;
	private TiffFileCsvParser tifParser;
	
	//Constructor
	public FileHandlerWrapper(){
		sff = new FileFinder();
		sfz = new ShapeFileZipper();
		sfs = new ShapeFileSorter();
		shpParser = new ShapeFileCsvParser();
		tifParser = new TiffFileCsvParser();
	}

	/**
	 * Used as a wrapper class for carrying out all shape file handling activities.
	 * 
	 * @param The target directory containing shape files.
	 */
	public List<File> setUpShapeFilesForUpload(String targetDirectory){
		files = new ArrayList<File>();
		sortedFiles = new ArrayList<List<File>>();
		
		files.addAll(sff.findAllByExtensionList(targetDirectory, sff.SHAPEFILE_EXTENSIONS));
		sortedFiles.addAll(sfs.sortShapeFiles(files));
		
		files.clear();
		
		sfz.zipSortedShapeFiles(sortedFiles, targetDirectory);
		files.addAll(sff.findAllBySingleExtension(targetDirectory, ".zip"));
		
		shpParser.writeFilesToCsv(files, targetDirectory + "\\uploadLayers.csv");
		return files;
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