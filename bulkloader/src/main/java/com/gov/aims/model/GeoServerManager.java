/**
@author Justin Osbaldiston
@version 1.0.0
@since 3/12/14
**/

package com.gov.aims.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder.ProjectionPolicy;
import it.geosolutions.geoserver.rest.encoder.datastore.GSShapefileDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import it.geosolutions.geoserver.rest.manager.GeoServerRESTStoreManager;

/** 
 * Handles all interaction between the system and the GeoServer
 */
public class GeoServerManager {
	/*
	 * Attributes
	 * geoServerURL is the URL of the GeoServer, for a local GeoServer it is http://localhost:8080/geoserver/
	 * userName is the login username to access the GeoServer
	 * password is the password to access the GeoServer
	 */
	private URL geoServerURL = new URL("http://localhost:8080/geoserver/");
	private String userName;
	private String password;
	
	private GeoServerRESTReader reader;
	private GeoServerRESTPublisher publisher;
	private GeoServerRESTManager manager;
	private GeoServerRESTStoreManager storeManager;
	private GSLayerEncoder layerEncoder;
	private GSFeatureTypeEncoder featureTypeEncoder;
	private Logger logger;
	
	/**
	 * Constructor - sets up all the managers and encoders and checks for a connection to the GeoServer
	 */
	public GeoServerManager(String geoServerUrl, String geoServeruserName, String geoServerPassword) throws MalformedURLException {
		userName = geoServeruserName;
		password = geoServerPassword;
		BasicConfigurator.configure();
		logger = Logger.getLogger(GeoServerManager.class);
		manager = new GeoServerRESTManager(geoServerURL, userName, password);
		reader = manager.getReader();
		publisher = manager.getPublisher();
		if(!connectionExists())
			logger.debug("No connection to GeoServer");
		layerEncoder = new GSLayerEncoder();
		featureTypeEncoder = new GSFeatureTypeEncoder();
		storeManager = new GeoServerRESTStoreManager(geoServerURL, userName, password);
	}
	
	/**
	 * Checks to see if a connection exists between the system and the GeoServer
	 * @return boolean - returns true if there is a connection, false if there is not
	 */
	private boolean connectionExists() {
		return reader.existGeoserver();
	}
	
	/**
	 * Checks to see if a workspace currently exists on the GeoServer
	 * @param workspaceName - the name of the workspace to search for
	 * @return boolean - returns true if the workspace exists, false if it does not
	 */
	public boolean checkForWorkspace(String workspaceName) {
		List<String> workspaces = reader.getWorkspaceNames();
		return workspaces.contains(workspaceName) ? true : false;
	}
	
	/**
	 * Creates a workspace on the GeoServer
	 * @param workspaceName - the name of the workspace you want to create
	 * @return boolean - returns true if workspace has been created, false if not
	 */
	public boolean createWorkspace(String workspaceName) {
		return publisher.createWorkspace(workspaceName);
	}
	
	/**
	 * Checks to see if a datastore currently exists in a workspace on the GeoServer
	 * @param workspaceName - the name of the workplace to search in
	 * @param storeName - the name of the datastore to search for
	 * @return boolean - returns true if the datastore exists, false if it does not
	 */
	public boolean checkForDataStore(String workspaceName, String storeName) {
		if(!checkForWorkspace(workspaceName))
			logger.debug("That workspace does not exist on the GeoServer - cannot search for a datastore inside a workspace that does not exist");
		return reader.getDatastores(workspaceName).getNames().contains(storeName);
	}
	
	/**
	 * Creates a datastore on the GeoServer in an already existing workspace, if the workspace does not exist then it will be created
	 * @param workspaceName - the name of the workspace that the store will be created in
	 * @param storeName - the name of the datastore to be created
	 * @return boolean - returns true if the datastore has been created, false if it has not
	 */
	public boolean createDataStore(String workspaceName, String storeName) {
		if (!checkForWorkspace(workspaceName)) {
			createWorkspace(workspaceName);
			logger.debug("Workspace did not exist previously on the GeoServer, but it has been created and does exist now");
		}
		
		GSShapefileDatastoreEncoder shapeFileStore;
		try {
			shapeFileStore = new GSShapefileDatastoreEncoder(storeName, new URL(geoServerURL.toString() + "rest/workspaces/" + workspaceName + "/datastores/" + storeName));
			return storeManager.create(workspaceName, shapeFileStore);
		} catch (MalformedURLException e) {
			logger.debug("The datastore URL is incorrect");
			return false;
		}
	}
	
	/**
	 * Sets up the Layer Encoder and the Feature Type Encoder for uploading
	 */
	private void setUpEncoders() {
		setUpLayerEncoder();
		setUpFeatureTypeEncoder();
	}
	
	/**
	 * Sets up the Layer Encoder for uploading
	 * - Reset the encoder to get rid of any prior settings from previous uploads
	 * - Enable the Layer
	 */
	private void setUpLayerEncoder() {
		resetLayerEncoder();
		layerEncoder.setEnabled(true);
	}
	
	/**
	 * Resets the Layer Encoder to get rid of any prior settings from previous uploads
	 */
	private void resetLayerEncoder() {
		layerEncoder = new GSLayerEncoder();
	}
	
	/**
	 * Sets the WMS Path of a Layer
	 * @param path
	 */
	private void setWMSPath(String path) {
		layerEncoder.setWmsPath(path);
	}
	
	/**
	 * Sets up the Feature Type Encoder for uploading
	 * - Reset the encoder to get rid of any prior settings for previous uploads
	 * - Enable the Feature Type
	 * - Delete the srs value. This is necessary otherwise the system will apply a default srs, whereas the GeoServer should apply 
	 *   the correct srs automatically upon uploading
	 * - Set the Projection Policy to <b>REPROJECT_TO_DECLARED</b>. This is necessary in order for the GeoServer to automatically
	 *   apply the correct srs
	 */
	private void setUpFeatureTypeEncoder() {
		resetFeatureTypeEncoder();
		featureTypeEncoder.setEnabled(true);
		featureTypeEncoder.delAttribute("srs");
		featureTypeEncoder.setProjectionPolicy(ProjectionPolicy.REPROJECT_TO_DECLARED);
	}
	
	/**
	 * Resets the Feature Type Encoder to get rid of any prior settings from previous uploads
	 */
	private void resetFeatureTypeEncoder() {
		featureTypeEncoder = new GSFeatureTypeEncoder();
	}
	
	/**
	 * Adds keywords to a Feature Type from a string of keywords
	 * @param keywords - the String of keywords to add to the Feature Type. <b>The keywords MUST be one String that is separated by commas</b>
	 * 					eg keyword1,keyword2 NOT keyword1, keyword2 or keyword1 keyword2
	 */
	private void addKeywords(String keywords) {
		for(String keyword : keywords.split(",")) {
			addKeyword(keyword);
		}
	}
	
	/**
	 * Adds a keyword to a Feature Type
	 * @param keyword - the keyword to add to the Feature Type
	 */
	private void addKeyword(String keyword) {
		featureTypeEncoder.addKeyword(keyword);
	}
	
	/**
	 * Adds a MetaData Link to a Feature Type
	 * @param link - the MetaData Link to add to the Feature Type
	 * 				 This function assumes that the link is in xml format and is of the type "TC211"
	 */
	private void addMetadataLink(String link) {
		featureTypeEncoder.addMetadataLinkInfo("text/xml", "TC211", link);
	}
	
	/**
	 * Sets the Abstract of a Feature Type 
	 * @param content - the Abstract to add to the Feature Type
	 */
	private void setAbstract(String content) {
		featureTypeEncoder.setAbstract(content);
	}
	
	/**
	 * Sets the Name of a Feature Type
	 * @param name - the name to set to the Feature Type
	 */
	private void setName(String name) {
		featureTypeEncoder.setName(name);
	}
	
	/**
	 * Gets the Native CRS of a shapefile from a part of the shapefile with extenstion .prj
	 * @param file - the .prj file to copy the Native CRS from.
	 * 				<b>NOTE: This MUST be a .prj file otherwise this code will not work</b>
	 * @return String - the Native CRS from the .prj file
	 */
	private String getNativeCRS(File file) {
		String lines = "";
		if(!FilenameUtils.getExtension(file.toString()).equals("prj")) {
			logger.debug("The file is not a .prj file. This function requires a .prj file to be passed to it in order to work");
			return lines;
		}
		Charset charset = Charset.forName("UTF-8");
		try {
			for(String line : Files.readAllLines(file.toPath(), charset)) {
				lines = line.trim();
			}
		} catch (IOException e) {
			logger.debug("The file could not be read from. It might be corrupt or the file name or path might be incorrect");
		}
		return lines;
	}
	
	/**
	 * Sets the Native CRS of a Feature Type and deletes the crs attribute of the Feature Type as this is not required and can
	 *    cause errors if not removed
	 * @param crs - the Native CRS to add to the Feature Type
	 */
	private void setNativeCRS(String crs) {
		featureTypeEncoder.setNativeCRS(crs);
		featureTypeEncoder.delAttribute("crs");
	}
	
	/**
	 * Sets the Title of a Feature Type
	 * @param title - the Title to set to the Feature Type
	 */
	private void setTitle(String title) {
		featureTypeEncoder.setTitle(title);
	}
	
	/**
	 * Uploads the Shapefile to the GeoServer with the following settings: Workspace Name, Store Name, Title, Abstract, MetaData Link, Keyword(s) and WMS Path
	 * @param workspaceName - the Name of the workspace on the GeoServer to upload the shapefile to. 
	 * 						  If the workspace does not exist then it will be created before uploading shapefile
	 * @param storeName - the Name of the datastore inside the workspace on the GeoServer to upload the shapefile to.
	 * 					  If the datastore does not exist then it will be created before uploading the shapefile
	 * @param zipFile - the .zip file that contains the shapefile. <b>NOTE: This must be a .zip file. If it is not then this function will not work</b>
	 * @param prjFile - the .prj file that contains the Native CRS data <b>NOTE: This must be a .prj file. If it is not then this function will not work </b>
	 * @param title - the Title of the layer (this is different to the name of the layer) 
	 * @param abstractText - the Abstract to add to the layer
	 * @param metadataLink - the link to the Metadata xml page for the layer
	 * @param keywords - the keywords to add to the layer. <b>The keywords MUST be one String that is separated by commas</b>
	 * 					eg keyword1,keyword2 NOT keyword1, keyword2 or keyword1 keyword2
	 * @param wmsPath - the WMS Path for the layer
	 * @return boolean - true if upload is successful, false if it is not
	 */
	public boolean upload(String workspaceName, String storeName, File zipFile, File prjFile, String title, String abstractText, String metadataLink, String keywords, String wmsPath) {
		if(!checkForWorkspace(workspaceName)) {
			createWorkspace(workspaceName);
			logger.debug("Workspace did not exist before, it has now been created and exists");
		}
		
		if(!checkForDataStore(workspaceName, storeName)) {
			createDataStore(workspaceName, storeName);
			logger.debug("Datastore did not exist before, it has now been created and exists");
		}
		
		if(!FilenameUtils.getExtension(zipFile.toString()).equals("zip")) {
			logger.debug("File entered is not a .zip file");
			return false;
		}
		
		String layerName = FilenameUtils.getBaseName(zipFile.toString());
		
		if(!FilenameUtils.getExtension(prjFile.toString()).equals("prj")) {
			logger.debug("File entered is not a .prj file. This function requires a .prj file to be passed to it in order to work");
			return false;
		}
		
		setUpEncoders();
		setName(layerName);
		setTitle(title);
		setAbstract(abstractText);
		addMetadataLink(metadataLink);
		addKeywords(keywords);
		setWMSPath(wmsPath);
		setNativeCRS(getNativeCRS(prjFile));
		
		try {
			publisher.publishShp(workspaceName, storeName, layerName, zipFile);
			publisher.unpublishFeatureType(workspaceName, storeName, layerName);
			publisher.publishDBLayer(workspaceName, storeName, featureTypeEncoder, layerEncoder);
			return true;
		} catch (FileNotFoundException e) {
			logger.debug("One or both of the files entered do not exist or cannot be found at that path.");
			return false;
		}
	}
}