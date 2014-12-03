/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
*/
package com.gov.aims.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Handles the sorting of shape files into like groups.
 *
 */
public class ShapeFileSorter {
	
	//Attributes
	public Logger logger;
	private ArrayList<File> sameShapeFiles;
	private ArrayList<List<File>> sortedShapeFiles;
	

	//Constructor
	public ShapeFileSorter(){
		BasicConfigurator.configure();
		logger = Logger.getLogger(ShapeFileSorter.class);
	}
		

	/**
	 * Handles the sorting of a list of shape files into groups using the files
	 * short name less extensions.
	 *
	 * @param A list of File objects to be sorted.
	 * @return Returns an ArrayList of Lists of File objects that have been sorted.
	 */
	public ArrayList<List<File>> sortShapeFiles(List<File> shapeFiles){
		String targetFilename="";
		String currentFilename="";
		sameShapeFiles = new ArrayList<File>();
		sortedShapeFiles = new ArrayList<List<File>>();
		
		try {
		
		//Handles the sorting.
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
		//Catches the last list which doesn't trigger the original add to list.
		sortedShapeFiles.add(new ArrayList<File>(sameShapeFiles));
		
		} catch (IndexOutOfBoundsException e) {
			logger.debug(e.getStackTrace() + "An error has occured whilst sorting shape files 'i' has referenced something unknown");
		} catch (Exception e){
			logger.debug(e.getStackTrace() + "An error has occured whilst sorting shape files, most likely a file does not exist.");
		}
		
		return sortedShapeFiles;
			
		}
		
}
