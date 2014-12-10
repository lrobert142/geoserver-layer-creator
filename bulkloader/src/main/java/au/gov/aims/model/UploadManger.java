/**
@author Justin Osbaldiston
@version 1.0.0
@since 4/12/14
**/

package au.gov.aims.model;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Runs program to create CSV, then upload files with settings from the CSV to the GeoSever
 */
public class UploadManger {
	private FileHandlerWrapper fileHandler;
	private GeoServerManager geoServerManager;
	private Logger logger;
	
	/**
	 * Constructor - sets up the directory for use and makes a connection to the GeoServer
	 */
	public UploadManger() {
		BasicConfigurator.configure();
		logger = Logger.getLogger(UploadManger.class);
		fileHandler = new FileHandlerWrapper();
		geoServerManager = new GeoServerManager("http://localhost:8080/geoserver/", "admin", "geoserver");
	}
	
	/**
	 * Creates .zip and CSV files in the directory and fills CSV with arbitrary data
	 * @param csvDirectory - the @param csvDirectory - the directory where the shapefiles are. This is also where the .zip and CSV files will be created
	 */
	public void setUpShapeFiles(String csvDirectory) {
		fileHandler.setUpShapeFilesForUpload(csvDirectory);
		logger.debug("Shape files ready for upload");
	}
	
	/**
	 * Creates CSV file in the directory and fills CSV with arbitrary data
	 * @param targetDirectory - the directory where the tiff are. This is also where the CSV file will be created
	 */
	public void setUpTiffFiles(String targetDirectory) {
		fileHandler.setUpTiffFilesForUpload(targetDirectory);
		logger.debug("Tiff files ready for upload");
	}
	
	/**
	 * Uploads each ShapeFile to the GeoServer, with the settings defined in the CSV
	 * @param csvDirectory - csvDirectory - the directory where the shapefiles, .zip files and CSV are
	 * @return boolean - true if the upload was successful for all files, false if one or more files failed to upload. If one file fails to upload then the uploader will stop, it will not upload the rest of the files
	 */
	public boolean uploadShapeFilesToGeoServer(String csvDirectory) {
		List<ShapeFile> shapeFiles = fileHandler.parseShapeFileUploadLayersCsvToBean(csvDirectory);
		for(ShapeFile shapeFile : shapeFiles) {
			File zipFile = new File(changeFilePathExtension("zip", shapeFile.getStorePath()));
			File prjFile = new File(changeFilePathExtension("prj", shapeFile.getStorePath()));
			if(Boolean.parseBoolean(shapeFile.getUploadData())) {
				if(!geoServerManager.uploadShapeFile(shapeFile.getWorkspace(), shapeFile.getStoreName(), zipFile, prjFile, shapeFile.getTitle(), shapeFile.getLayerAbstract(), shapeFile.getMetadataXmlHref(), shapeFile.getKeywords(), shapeFile.getWmsPath())) {
					logger.debug("Shape file " + FilenameUtils.getBaseName(zipFile.getName()) + " caused an error during uploading. Upload has been stopped");
					return false;
				}
			}
		}
		logger.debug("All shape files successfully uploaded");
		return true;
	}
	
	/**
	 * Uploads each TiffFile to the GeoServer, with the settings defined in the CSV
	 * @param targetDirectory - the directory where the tiff files and CSV are
	 * @return boolean - true if the upload was successful for all files, false if one or more files failed to upload. If one file fails to upload then the uploader will stop, it will not upload the rest of the files
	 */
	public boolean uploadTiffFilesToGeoServer(String targetDirectory) {
		List<TiffFile> tifFiles = fileHandler.parseTiffFileUploadLayersCsvToBean(targetDirectory);
		for(TiffFile tifFile : tifFiles) {
			File file = new File(tifFile.getStorePath());
			if(Boolean.parseBoolean(tifFile.getUploadData())) {
				if (!geoServerManager.uploadGeoTIFFFile(tifFile.getWorkspace(), tifFile.getStoreName(), file, tifFile.getTitle(), tifFile.getLayerAbstract(), tifFile.getMetadataXmlHref(), tifFile.getKeywords(), tifFile.getWmsPath())) {
					logger.debug("Tiff file " + FilenameUtils.getBaseName(file.getName()) + " caused an error during uploading. Upload has been stopped");
					return false;
				}
			}
		}
		logger.debug("All tiff files successfully uploaded");
		return true;
	}
	
	/**
	 * Sets the extension of a filename
	 * @param extension - the extension to set to the path. <b>NOTE: Do not include the full stop, only include the extension<b> eg zip NOT .zip
	 * @param path - the path of the file to change the extension of
	 * @return String - the path with the changed extension
	 */
	private String changeFilePathExtension(String extension, String path) {
		return path.substring(0, path.lastIndexOf(".") + 1) + extension;
	}
	
	public boolean deleteWorkspace(String workspaceName) {
		return geoServerManager.deleteWorkspace(workspaceName);
	}
}