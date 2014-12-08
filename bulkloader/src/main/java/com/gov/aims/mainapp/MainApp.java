package com.gov.aims.mainapp;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.gov.aims.model.GeoServerManager;
import com.gov.aims.model.ShapeFile;
import com.gov.aims.model.FileHandlerWrapper;

public class MainApp {
	private FileHandlerWrapper shapeFileHandler;
	private GeoServerManager geoServerManager;
	private Logger logger;
	
	public MainApp() {
		BasicConfigurator.configure();
		logger = Logger.getLogger(MainApp.class);
		shapeFileHandler = new FileHandlerWrapper();
		try {
			geoServerManager = new GeoServerManager("http://localhost:8080/geoserver/", "admin", "geoserver");
		} catch (MalformedURLException e) {
			logger.error(e.getStackTrace() + "Could not connect to GeoServer, the combination of URL, username and password is incorrect");
		}
	}
	
	private List<ShapeFile> parseCSVToShapeFileBean(String csvDirectory) {
		return shapeFileHandler.parseShapeFileUploadLayersCsvToBean(csvDirectory);
	}
	
	public void setUp(String csvDirectory) {
		shapeFileHandler.setUpShapeFilesForUpload(csvDirectory);
	}
	
	public boolean uploadLayersToGeoServer(String csvDirectory) {
		List<ShapeFile> shapeFiles = parseCSVToShapeFileBean(csvDirectory);
		for(ShapeFile shapeFile : shapeFiles) {
			File zipFile = new File(changeFilePathExtension("zip", shapeFile.getStorePath()));
			File prjFile = new File(changeFilePathExtension("prj", shapeFile.getStorePath()));
			if(!geoServerManager.upload(shapeFile.getWorkspace(), shapeFile.getStoreName(), zipFile, prjFile, shapeFile.getTitle(), shapeFile.getLayerAbstract(), shapeFile.getMetadataXmlHref(), shapeFile.getKeywords(), shapeFile.getWmsPath())) {
				return false;
			}
		}
		return true;
	}
	
	private String changeFilePathExtension(String extension, String path) {
		return path.substring(0, path.length() - 3) + extension;
	}
}