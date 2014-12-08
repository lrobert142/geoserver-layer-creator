/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
package com.gov.aims.utilities;

import java.io.File;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gov.aims.interfaces.FileFinderInterface;

public class FileFinder implements FileFinderInterface {
	// Constants for the shape file extensions.
	public final ArrayList<String> SHAPEFILE_EXTENSIONS = new ArrayList<String>(Arrays.asList(".dbf", ".prj", ".shp", ".shx"));

	// Attributes
	private ArrayList<File> allFiles;
	public Logger logger;
	
	// Constructor
	public FileFinder() {
		logger = Logger.getLogger(FileFinder.class);
	}

	@Override
	public List<File> findAll(String targetDirectory) {
		allFiles = new ArrayList<File>();

		try {
			File directory = new File(targetDirectory);
			allFiles = new ArrayList<File>();
				
			allFiles.addAll(listFileTree(directory));
		} catch (Exception e) {
			logger.debug(e + "error");
		}
		Collections.sort(allFiles);
		return allFiles;
	}

	@Override
	public List<File> findAllByExtensionList(String targetDirectory, List<String> extensions) {
		try {
			File directory = new File(targetDirectory);
			allFiles = new ArrayList<File>();
				
			allFiles.addAll(listFileTree(directory, extensions));
		} catch (NullPointerException e) {
			logger.debug(e.getStackTrace() + "An error has occurred whilst iterating through a list of file objects.");
		} catch (FileSystemNotFoundException e){
			logger.debug(e.getStackTrace() + "An error has occured when attempting to open a directory.");
		} catch (Exception e){
			logger.debug(e.getStackTrace() + "ERROR - Some unspecified error has occured whilst attempting to open a directory.");
		}
		Collections.sort(allFiles);
		return allFiles;
	}

	@Override
	public List<File> findAllBySingleExtension(String targetDirectory, String extension) {
		try {
			File directory = new File(targetDirectory);
			allFiles = new ArrayList<File>();
				
			allFiles.addAll(listFileTree(directory, extension));
		} catch (NullPointerException e) {
			logger.debug(e.getStackTrace() + "An error has occurred whilst iterating through a list of file objects.");
		} catch (FileSystemNotFoundException e){
			logger.debug(e.getStackTrace() + "An error has occured when attempting to open a directory.");
		} catch (Exception e){
			logger.debug(e.getStackTrace() + "ERROR - Some unspecified error has occured whilst attempting to open a directory.");
		}
		Collections.sort(allFiles);
		return allFiles;
	}
		
	//These methods handle finding all files in a directory/sub directories based on the input parameters.
		
	//List all from list of file extensions 
	final  Collection<File> listFileTree(File dir, List<String> EXTENSIONS) {
		Set<File> fileTree = new HashSet<File>();
		for (File entry : dir.listFiles()) {
			if (entry.isFile()){
				for (String s : EXTENSIONS) {
					if (entry.getName().toString().endsWith(s)) {
						fileTree.add(entry);
					}
		        }
		    }
		    else 
		    	fileTree.addAll(listFileTree(entry, EXTENSIONS));
		}
		return fileTree;
	}
		
	//List all with a specified file extension
	final  Collection<File> listFileTree(File dir, String EXTENSION) {
		Set<File> fileTree = new HashSet<File>();
		for (File entry : dir.listFiles()) {
		    if (entry.isFile()){
				if (entry.getName().toString().endsWith(EXTENSION)) {
		        	fileTree.add(entry);
				}
		    }
		    else 
		    	fileTree.addAll(listFileTree(entry, EXTENSION));
		}
		return fileTree;
	}
		
	//List all files in a directory/sub directory
	final  Collection<File> listFileTree(File dir) {
		Set<File> fileTree = new HashSet<File>();
		for (File entry : dir.listFiles()) {
		    if (entry.isFile()) {
		        fileTree.add(entry);
		    }
		    else 
		    	fileTree.addAll(listFileTree(entry));
		}
		return fileTree;
	}
}