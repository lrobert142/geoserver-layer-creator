package com.gov.aims.utilities;

import interfaces.FileFinderInterface;

import java.io.File;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class ShapeFileFinder implements FileFinderInterface {

	
	// Constants for the shape file extensions.
		public final ArrayList<String> SHAPEFILE_EXTENSIONS = new ArrayList<String>(
				Arrays.asList(".dbf", ".prj", ".shp", ".shx"));

		// Attributes
		private ArrayList<File> allFiles;
		public Logger logger;

		// Constructor
		public ShapeFileFinder() {
			BasicConfigurator.configure();
			logger = Logger.getLogger(ShapeFileFinder.class);
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
			return allFiles;
		}

		@Override
		public List<File> findAllByExtensionList(String targetDirectory,
				List<String> extensions) {
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
			return allFiles;
		}

		@Override
		public List<File> findAllBySingleExtension(String targetDirectory,
				String extension) {
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
