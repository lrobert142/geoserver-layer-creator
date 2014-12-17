/**
@author Justin Osbaldiston
@version 1.0.0
@since 4/12/14
**/

package au.gov.aims.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import au.gov.aims.utilities.PathsHandler;

/**
 * Runs program to create CSV, then upload files with settings from the CSV to the GeoSever
 */
public class UploadManger {
	private GeoServerFileHandlerWrapper fileHandler;
	private GeoServerManager geoServerManager;
	private Logger logger;
	private int nextIndex;
	private List<GeoServerFile> geoServerFiles, failedUploads;
	private boolean stopUpload, uploadComplete = false;
	
	/**
	 * Constructor - sets up the directory for use
	 */
	public UploadManger() {
		BasicConfigurator.configure();
		logger = Logger.getLogger(UploadManger.class);
		fileHandler = new GeoServerFileHandlerWrapper();
	}
	
	/**
	 * Makes a connection to the GeoServer
	 * @param geoServerUrl - The URL of the GeoServer. On a local machine this will be http://localhost:8080/geoserver/
	 * @param geoServerUserName - the Username used to login to GeoServer
	 * @param geoServerPassword - the Password used to login to GeoServer
	 */
	public void login(String geoServerUrl, String geoServerUserName, String geoServerPassword) {
		geoServerManager = new GeoServerManager(geoServerUrl, geoServerUserName, geoServerPassword);
	}
	
	/**
	 * Checks to see if a connection exists between the system and the GeoServer
	 * @return boolean - returns true if there is a connection, false if there is not
	 */
	public boolean checkForConnection() {
		return geoServerManager.checkConnectionExists();
	}
	
	/**
	 * Creates .zip files in a temporary directory
	 * @param filesDirectory - the directory where the GeoServer files are. This is also where the CSV file will be created
	 */
	public void setUpFiles(String filesDirectory) {
		fileHandler.setUpFilesForUpload(filesDirectory);
		logger.debug("GeoServer files ready for upload");
	}
	
	/**
	 * Finds files to upload and creates a CSV based on those files
	 * @param filesDirectory - the directory to search for files in, the the directory where the CSV will be created
	 */
	public void writeCSV(String filesDirectory) {
		List<File> filesFound = fileHandler.findFilesForUpload(filesDirectory);
		fileHandler.initialWriteGeoServerFilesToCsv(filesFound, filesDirectory + "\\uploadLayers.csv");
	}
	
	/**
	 * Uploads each ShapeFile to the GeoServer, with the settings defined in the CSV
	 * If one or more uploads fail, the CSV will be re-written witf the files that failed for re-upload
	 * @param csvFileName - the csv file path
	 * @return boolean - true if the upload was successful for all files, false if one or more files failed to upload. If one file fails to upload then the uploader will stop, it will not upload the rest of the files
	 */
	public boolean uploadGeoServerFilesToGeoServer(String csvFileName) {
		nextIndex = 1;
		geoServerFiles = fileHandler.parseGeoServerFileUploadLayersCsvToBean(csvFileName);
		failedUploads = new ArrayList<GeoServerFile>();
		for(GeoServerFile geoServerFile : geoServerFiles) {
			if(!stopUpload) {
				if(Boolean.parseBoolean(geoServerFile.getUploadData())) {
					switch (geoServerFile.getStoreType()) {
					case "Shapefile":
						File zipFile = new File(geoServerFile.getStorePath());
						if(!geoServerManager.uploadShapeFile(geoServerFile.getWorkspace(), geoServerFile.getStoreName(), zipFile, geoServerFile.getTitle(), geoServerFile.getLayerAbstract(), geoServerFile.getMetadataXmlHref(), geoServerFile.getKeywords(), geoServerFile.getWmsPath())) {
							failedUploads.add(geoServerFile);
							logger.debug("File " + FilenameUtils.getBaseName(zipFile.getName()) + " caused an error during uploading.");
						}
						break;
					case "GeoTiff":
						File tifFile = new File(geoServerFile.getStorePath());
						if(!geoServerManager.uploadGeoTIFFFile(geoServerFile.getWorkspace(), geoServerFile.getStoreName(), geoServerFile.getLayerName(), tifFile, geoServerFile.getTitle(), geoServerFile.getLayerAbstract(), geoServerFile.getMetadataXmlHref(), geoServerFile.getKeywords(), geoServerFile.getWmsPath())) {
							failedUploads.add(geoServerFile);
							logger.debug("File " + FilenameUtils.getBaseName(tifFile.getName()) + " caused an error during uploading.");
						}
						break;
					default:
						logger.debug(geoServerFile.getLayerName() + " is not a valid file type");
					}
				}
				nextIndex += 1;
			} else
				break;
		}
		return endUpload(PathsHandler.getBasePath(csvFileName));
	}
	
	/**
	 * Processes the end of uploading and dealing with failed uploads
	 * @param folderPath - the path of the folder to perform end operations in
	 * @return boolean - returns true if all files uploaded successfully, false if they didn't
	 */
	private boolean endUpload(String folderPath) {
		uploadComplete = true;
		if(failedUploads != null) {
			if(failedUploads.isEmpty()) {
				logger.debug("All GeoServer files successfully uploaded");
				deleteFolder(folderPath + "\\GS_LAYER_UPLOADER_ZIPS");
				return true;
			} else {
				fileHandler.rewriteFailedUploadsToCSV(failedUploads, folderPath + "\\uploadFails.csv");
				logger.debug("Not all files to upload were successful, the failed uploads have been written to a csv file named failedUploads.csv, please fix them and try again");
				return false;
			}
		} else
			return false;
	}
	
	public int getCurrentIndex() {
		return nextIndex - 1;
	}
	
	public boolean isUploadComplete() {
		return uploadComplete;
	}
	
	public String getCurrentUpload(int index) {
		if(geoServerFiles != null)
			return geoServerFiles.get(index - 1).getStorePath();
		else
			return "null";
	}
	
	/**
	 * Stops the uploading of files to GeoServer, adds the remaining files to the failed uploads list, and performs end of upload functions
	 * @param directoryPath - the directory to perform end of upload functions in
	 */
	public void stopUpload(String directoryPath) {
		if(!stopUpload) {
			stopUpload = true;
			if(geoServerFiles != null) {
				for(int i = nextIndex; i < geoServerFiles.size(); ++i) {
					failedUploads.add(geoServerFiles.get(i));
				}
			}
			endUpload(directoryPath);
		}
	}
	
	/**
	 * Deletes the folder specified, along with everything inside it 
	 * @param folderPath - the path of the folder to be deleted
	 */
	public void deleteFolder(String folderPath) {
		File temporaryFolder = new File(folderPath);
		if(temporaryFolder.exists()) {
			for(File fileName : temporaryFolder.listFiles()) {
				fileName.delete();
			}
			temporaryFolder.delete();
		}
	}
	
	/**
	 * Deletes a workspace on the GeoServer
	 * @param workspaceName - the name of the workspace to be deleted
	 * @return boolean - true if the workspace was successfully deleted, false if it was not
	 */
	public boolean deleteWorkspace(String workspaceName) {
		return geoServerManager.deleteWorkspace(workspaceName);
	}
}