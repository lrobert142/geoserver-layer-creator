/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
package au.gov.aims.utilities;

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

import au.gov.aims.interfaces.FileFinderInterface;

public class FileFinder implements FileFinderInterface {
	// Constants for the shape file extensions.
	public final ArrayList<String> SHAPEFILE_EXTENSIONS = new ArrayList<String>(Arrays.asList(".dbf", ".prj", ".shp", ".shx"));
	public final ArrayList<String> FILE_EXTENSIONS_FOR_CSV = new ArrayList<String>(Arrays.asList(".shp", ".tif"));

	// Attributes
	private ArrayList<File> allFiles;
	public Logger logger;
	
	// Constructor
	public FileFinder() {
		logger = Logger.getLogger(FileFinder.class);
	}

	/**
	 * Finds all files in a given directory.
	 * 
	 * @param targetDirectory - The absolute path to the directory.
	 * 
	 * @return A list of file objects.
	 */
	@Override
	public List<File> findAll(String targetDirectory) {
		allFiles = new ArrayList<File>();

		try {
			File directory = new File(targetDirectory);
			allFiles = new ArrayList<File>();
				
			allFiles.addAll(listFileTree(directory));
		} catch (Exception e) {
			logger.debug("An error has occured whilst seraching: " + targetDirectory);
		}
		Collections.sort(allFiles);
		return allFiles;
	}

	/**
	 * Finds all files in a given directory with the extension specified in a list.
	 * 
	 * @param targetDirectory - The absolute path to the directory.
	 * @param extensions - A list of String extensions e.g. .shp, .dbf...
	 * 
	 * @return A list of file objects.
	 */
	@Override
	public List<File> findAllByExtensionList(String targetDirectory, List<String> extensions) {
		try {
			File directory = new File(targetDirectory);
			allFiles = new ArrayList<File>();
				
			allFiles.addAll(listFileTree(directory, extensions));
		} catch (NullPointerException e) {
			logger.debug("ERROR - An error has occurred whilst iterating through a list of file objects.");
		} catch (FileSystemNotFoundException e){
			logger.debug("ERROR - An error has occured when attempting to open a directory.");
		} catch (Exception e){
			logger.debug("ERROR - Some unspecified error has occured whilst attempting to open a directory.");
		}
		Collections.sort(allFiles);
		return allFiles;
	}

	/**
	 * Finds all files in a given directory with the extension specified.
	 * 
	 * @param targetDirectory - The absolute path to the directory.
	 * @param extension - A String extension e.g. .shp
	 * 
	 * @return A list of file objects.
	 */
	@Override
	public List<File> findAllBySingleExtension(String targetDirectory, String extension) {
		try {
			File directory = new File(targetDirectory);
			allFiles = new ArrayList<File>();
				
			allFiles.addAll(listFileTree(directory, extension));
		} catch (NullPointerException e) {
			logger.debug("ERROR - An error has occurred whilst iterating through a list of file objects.");
		} catch (FileSystemNotFoundException e){
			logger.debug("ERROR - An error has occured when attempting to open a directory.");
		} catch (Exception e){
			logger.debug("ERROR - Some unspecified error has occured whilst attempting to open a directory.");
		}
		Collections.sort(allFiles);
		return allFiles;
	}
		
	//These helper methods handle finding all files in a directory/sub directories based on the input parameters
	//using recursion.
		
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