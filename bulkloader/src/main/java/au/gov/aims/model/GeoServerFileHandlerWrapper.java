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
	
	public boolean initialWriteGeoServerFilesToCsv(List<File> file, String targetFileName){
		parser.writeFilesToCsv(file, targetFileName);
		
		return true;
		
	}
	
	public List<File> findFilesForUpload(String targetDirectory) {
		return ff.findAllByExtensionList(targetDirectory, ff.FILE_EXTENSIONS_FOR_CSV);
	}

	/**
	 * Used as a wrapper class for carrying out all file handling activities.
	 * 
	 * @param The target directory containing GeoServerFiles.
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
	
	
	public List<GeoServerFile> parseGeoServerFileUploadLayersCsvToBean(String csvFileName){
		List<GeoServerFile> geoServerFileBean = new ArrayList<GeoServerFile>();
		try {
			geoServerFileBean = parser.parseGeoServerFileToJavaBean(csvFileName);
			for (int i = 0; i < geoServerFileBean.size(); i++) {
				
				String baseName = FilenameUtils.getBaseName(geoServerFileBean.get(i).getStorePath());
				
				if(geoServerFileBean.get(i).getStorePath().endsWith(".shp")) {
					geoServerFileBean.get(i).setStorePath("/GS_LAYER_UPLOADER_ZIPS/" + baseName + ".zip");
				}
				
				String basePath = getRelativePath(csvFileName);
				
				geoServerFileBean.get(i).setStorePath(relativePathToAbstractPath(basePath, geoServerFileBean.get(i).getStorePath()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return geoServerFileBean;
	}
	private String relativePathToAbstractPath(String basePath, String relativePath) {
		return basePath + relativePath;
	}

	private String getRelativePath(String targetPath) {
		return targetPath.substring(0, targetPath.lastIndexOf("/"));
	}
	
}