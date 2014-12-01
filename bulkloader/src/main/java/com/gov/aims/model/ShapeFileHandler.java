/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
*/
package com.gov.aims.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class ShapeFileHandler {
	
	public Logger logger;
	private ArrayList<File> sameShapeFiles;
	private ArrayList<List<File>> sortedShapeFiles;
	


	public ShapeFileHandler(){
		BasicConfigurator.configure();
		logger = Logger.getLogger(ShapeFileHandler.class);
	}
		

	
	public ArrayList<List<File>> sortShapeFiles(List<File> shapeFiles){
		String targetFilename="";
		String currentFilename="";
		sameShapeFiles = new ArrayList<File>();
		sortedShapeFiles = new ArrayList<List<File>>();
		
		
		for (int i = 0; i < shapeFiles.size(); i++) {
			
			currentFilename = FilenameUtils.removeExtension(shapeFiles.get(i).getName());
			if(i == 0){
				targetFilename = currentFilename;
				sameShapeFiles.add(shapeFiles.get(i));
			}
			
			else if (targetFilename.equals(currentFilename)) {
				sameShapeFiles.add(shapeFiles.get(i));
				
			}
			
			else if(!targetFilename.equals(currentFilename)){
				sortedShapeFiles.add(new ArrayList<File>(sameShapeFiles));
				sameShapeFiles.clear();
				
				targetFilename = currentFilename;
				sameShapeFiles.add(shapeFiles.get(i));
				
			}
			else{
				sameShapeFiles.add(shapeFiles.get(i));
			}
			
		}
		sortedShapeFiles.add(new ArrayList<File>(sameShapeFiles));
		return sortedShapeFiles;
			
		}
		
}
