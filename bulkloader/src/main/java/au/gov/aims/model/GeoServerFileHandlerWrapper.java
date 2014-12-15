/**
@author Stuart Garrigan
@version 1.0.1
@since 11/12/14
**/
package au.gov.aims.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import au.gov.aims.utilities.FileFinder;
import au.gov.aims.utilities.GeoServerFileCsvParser;
import au.gov.aims.utilities.ShapeFileSorter;
import au.gov.aims.utilities.ShapeFileZipper;

/**
 * Handles all aspects of shape files: finding, sorting, zipping and creating a .csv
 * for uploading in one method.
 *
 */
public final class GeoServerFileHandlerWrapper {
	//Attributes
	private FileFinder ff;
	private ShapeFileZipper sfz;
	private ShapeFileSorter sfs;
	public static List<File> files;
	public static List<List<File>> sortedFiles;
	private GeoServerFileCsvParser parser;
	private File tempDirectory;
	public Logger logger;
	
	//Constructor
	public GeoServerFileHandlerWrapper(){
		ff = new FileFinder();
		sfz = new ShapeFileZipper();
		sfs = new ShapeFileSorter();
		parser = new GeoServerFileCsvParser();
		Logger.getLogger(GeoServerFileHandlerWrapper.class);
	}
	
	/**
	 * Creates a .csv file and writes the contents of a list of files to it. This is a wrapper class 
	 * for the GeoServerFileCsvParser().
	 * 
	 * @param files -  The list of file objects to be written to csv.
	 * @param targetFileName - The directory and csv name. e.g. C:/../../uploadLayers.csv
	 * 
	 * @return On successful write returns true.
	 */
	public boolean initialWriteGeoServerFilesToCsv(List<File> files, String targetFileName) {
		parser.writeFilesToCsv(files, targetFileName);
		
		return true;
	}
	
	/**
	 * Creates a .csv file and writes the contents of a list of GeoServerFile objects to it. This is a wrapper class 
	 * for the GeoServerFileCsvParser(). 
	 * 
	 * @param files -  The list of GeoServerFile objects to be written to csv.
	 * @param targetFileName - The directory and csv name. e.g. C:/../../uploadLayers.csv
	 * 
	 * @return On successful write returns true.
	 */
	public boolean rewriteFailedUploadsToCSV(List<GeoServerFile> files, String targetFileName) {
		parser.writeFailedFilesToCsv(files, targetFileName);
		return true;
	}
	
	public List<File> findFilesForUpload(String targetDirectory) {
		return ff.findAllByExtensionList(targetDirectory, ff.FILE_EXTENSIONS_FOR_CSV);
	}

	/**
	 * Used as a wrapper class for carrying out all file handling activities for setting up files
	 * for upload to a geoserver. Includes finding, sorting and zipping.
	 * 
	 * @param targetDirectory - The target directory containing .shp and .tif file types.
	 * 
	 * @return A list of file objects.
	 */
	public List<File> setUpFilesForUpload(String targetDirectory){
		
		tempDirectory = new File(targetDirectory + "/GS_LAYER_UPLOADER_ZIPS");
		tempDirectory.mkdir();
		
		files = new ArrayList<File>();
		sortedFiles = new ArrayList<List<File>>();
		
		files.addAll(ff.findAllByExtensionList(targetDirectory, ff.SHAPEFILE_EXTENSIONS));
		
		sortedFiles.addAll(sfs.sortShapeFiles(files));
		
		files.clear();
		
		sfz.zipSortedShapeFiles(sortedFiles, tempDirectory.getAbsolutePath());
		
		files.addAll(ff.findAllBySingleExtension(targetDirectory, ".tif"));
		
		files.addAll(ff.findAllBySingleExtension(tempDirectory.getAbsolutePath(), ".zip"));
		
		return files;
	}
	
	/**
	 * A wrapper for GeoServerFileCsvParser(), takes a given csvFilePath and converts it into a usable format
	 * for geoserver e.g.'\' -> '/'. The csv is then parsed and mapped to a GeoServerFile object. For any files in the csv
	 * found with a .shp extension. They are converted to .zip for upload. 
	 * 
	 * @param csvFileName - The file path to the .csv file being parsed.
	 * 
	 * @return A list of GeoServerFile objects.
	 */
	public List<GeoServerFile> parseGeoServerFileUploadLayersCsvToBean(String csvFileName){
		csvFileName = backslashToForwardslash(csvFileName);
		List<GeoServerFile> geoServerFileBean = new ArrayList<GeoServerFile>();
		try {
			geoServerFileBean = parser.parseGeoServerFileToJavaBean(csvFileName);
			for (int i = 0; i < geoServerFileBean.size(); i++) {
				
				String baseName = FilenameUtils.getBaseName(geoServerFileBean.get(i).getStorePath());
				
				if(geoServerFileBean.get(i).getStorePath().endsWith(".shp")) {
					geoServerFileBean.get(i).setStorePath("/GS_LAYER_UPLOADER_ZIPS/" + baseName + ".zip");
				}
				
				String basePath = getRelativePath(csvFileName);
				
				geoServerFileBean.get(i).setStorePath(relativePathToAbsolutePath(basePath, geoServerFileBean.get(i).getStorePath()));
			}
		} catch (IOException e) {
			logger.debug("An error has occured whilst attempting to parse the specified .csv, most likely the file was not found or the content does not match the requirements of a GeoServerFile Object." + e);
		}
		return geoServerFileBean;
	}
	
	/**
	 * Gets the absolute path of a GeoServerFile by concatenating the files base path and relative path.
	 * 
	 * @param basePath - The base path of the file.
	 * @param relativePath - The relative path of the file.
	 * 
	 * @return The constructed string.
	 */
	private String relativePathToAbsolutePath(String basePath, String relativePath) {
		return basePath + relativePath;
	}

	/**
	 * Gets the relative path of a file by taking a substring of the given absolute path.
	 * 
	 * @param targetPath - The absolute path of the file.
	 * 
	 * @return The constructed string.
	 */
	private String getRelativePath(String targetPath) {
		return targetPath.substring(0, targetPath.lastIndexOf("/"));
	}
	
	/**
	 * Converts absolute file paths containing '\' characters and converts them to '/' characters
	 * @param path - Absolute path for a file to be converted.
	 * @return The converted string.
	 */
	public String backslashToForwardslash(String path) {
		StringBuilder builder = new StringBuilder();
		char[] pathArray = path.toCharArray();
		
		for (int j = 0; j < pathArray.length; j++) {
			if(pathArray[j] == '\\')
				pathArray[j] = '/';
			builder.append(pathArray[j]);
		}
		return builder.toString();
	}
	
}