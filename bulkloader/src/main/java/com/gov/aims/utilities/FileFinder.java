/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
 */

package com.gov.aims.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class FileFinder {

	// Constants for the shape file extensions.
	public final ArrayList<String> SHAPEFILE_EXTENSIONS = new ArrayList<String>(
			Arrays.asList(".dbf", ".prj", ".shp", ".shx"));

	// Attributes
	private ArrayList<File> allFiles;
	public Logger logger;

	// Constructor
	public FileFinder() {
		BasicConfigurator.configure();
		logger = Logger.getLogger(FileFinder.class);
	}

	/**
	 * Used to find all files/file types in the specified directory
	 * 
	 *
	 * @param this is the desired file path to the directory you want.
	 * @return Returns a list of files as type File.
	 */
	public ArrayList<File> findAllFilesInDir(String targetDirectory) {
		allFiles = new ArrayList<File>();

		try {

			File directory = new File(targetDirectory);
			allFiles = new ArrayList<File>();
			
			allFiles.addAll(listFileTree(directory));
		} catch (Exception e) {
			logger.debug(e + "error");
		}
		return allFiles;
	}

	/**
	 * Used to find all shape files in the specified directory
	 * 
	 *
	 * @param directory
	 *            - this is the desired file path to the directory you want.
	 * @param EXTENSIONS
	 *            - a list of the extension types desired. e.g. .csv.
	 * @return Returns a list of files.
	 */
	public ArrayList<File> findFilesByExtension(String targetDirectory,
			List<String> EXTENSIONS) {

		try {

			File directory = new File(targetDirectory);
			allFiles = new ArrayList<File>();
			
			allFiles.addAll(listFileTree(directory, EXTENSIONS));
		} catch (NullPointerException e) {
			logger.debug(e.getStackTrace() + "An error has occurred whilst iterating through a list of file objects.");
		} catch (FileSystemNotFoundException e){
			logger.debug(e.getStackTrace() + "An error has occured when attempting to open a directory.");
		} catch (Exception e){
			logger.debug(e.getStackTrace() + "ERROR - Some unspecified error has occured whilst attempting to open a directory.");
		}
		return allFiles;
	}

	/**
	 * Used to find all shape files in the specified directory
	 * 
	 *
	 * @param directory
	 *            - this is the desired file path to the directory you want.
	 * @param EXTENSION
	 *            - a single String of the extension type desired. e.g. .zip.
	 * @return Returns a list of files..
	 */
	public ArrayList<File> findFilesByExtension(String targetDirectory,
			String EXTENSION) {
		try {

			File directory = new File(targetDirectory);
			allFiles = new ArrayList<File>();
			
			allFiles.addAll(listFileTree(directory, EXTENSION));
		} catch (NullPointerException e) {
			logger.debug(e.getStackTrace() + "An error has occurred whilst iterating through a list of file objects.");
		} catch (FileSystemNotFoundException e){
			logger.debug(e.getStackTrace() + "An error has occured when attempting to open a directory.");
		} catch (Exception e){
			logger.debug(e.getStackTrace() + "ERROR - Some unspecified error has occured whilst attempting to open a directory.");
		}
		return allFiles;
	}
	
	//These methods handle finding all files in a directory/sub directories based on the input parameters.
	
	//List all from list of file extensions 
	final  Collection<File> listFileTree(File dir, List<String> EXTENSIONS) {
	    Set<File> fileTree = new HashSet<File>();
	    for (File entry : dir.listFiles()) {
	        if (entry.isFile()){
	        	for (String s : EXTENSIONS) {
					if (entry.getName().toString().endsWith(s)){
	        	fileTree.add(entry);
					}
	        	}
	        }
	        else fileTree.addAll(listFileTree(entry, EXTENSIONS));
	    }
	    return fileTree;
	}
	
	//List all with a specified file extension
	final  Collection<File> listFileTree(File dir, String EXTENSION) {
	    Set<File> fileTree = new HashSet<File>();
	    for (File entry : dir.listFiles()) {
	        if (entry.isFile()){
					if (entry.getName().toString().endsWith(EXTENSION)){
	        	fileTree.add(entry);
					}
	        }
	        else fileTree.addAll(listFileTree(entry, EXTENSION));
	    }
	    return fileTree;
	}
	
	//List all files in a directory/sub directory
	final  Collection<File> listFileTree(File dir) {
	    Set<File> fileTree = new HashSet<File>();
	    for (File entry : dir.listFiles()) {
	        if (entry.isFile()){
	        	fileTree.add(entry);
	        }
	        else fileTree.addAll(listFileTree(entry));
	    }
	    return fileTree;
	}
	
	
}
