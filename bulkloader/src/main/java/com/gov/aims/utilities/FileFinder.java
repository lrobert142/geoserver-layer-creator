/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
 */

package com.gov.aims.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.gov.aims.model.ShapeFileHandler;

public class FileFinder {

	// Constants for the shape file extensions.
	public final ArrayList<String> SHAPEFILE_EXTENSIONS = new ArrayList<String>(
			Arrays.asList(".dbf", ".prj", ".shp", ".shx"));

	// Attributes
	private ArrayList<File> shapeFiles;
	private ArrayList<File> allFiles;
	private Logger logger;

	// Constructor
	public FileFinder() {
		BasicConfigurator.configure();
		logger = Logger.getLogger(ShapeFileHandler.class);
	}

	/**
	 * Used to find all files/file types in the specified directory
	 * 
	 *
	 * @param this is the desired file path to the directory you want.
	 * @return Returns a list of files as type File.
	 */
	public ArrayList<File> findAllFilesInDir(String directory) {
		allFiles = new ArrayList<File>();

		try {

			File[] files = new File(directory).listFiles();

			for (File file : files) {
				if (file.isFile()) {
					allFiles.add(file);
				}
			}
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
	public ArrayList<File> findFilesByExtension(String directory,
			List<String> EXTENSIONS) {
		shapeFiles = new ArrayList<File>();

		try {

			File[] files = new File(directory).listFiles();

			for (File file : files) {
				if (file.isFile()) {
					for (String s : EXTENSIONS) {
						if (file.getName().toString().endsWith(s))
							shapeFiles.add(file);
					}
				}
			}
		} catch (Exception e) {
			logger.debug(e + "error");
		}
		return shapeFiles;
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
	public ArrayList<File> findFilesByExtension(String directory,
			String EXTENSION) {
		shapeFiles = new ArrayList<File>();

		try {

			File[] files = new File(directory).listFiles();

			for (File file : files) {
				if (file.isFile()) {
					if (file.getName().toString().endsWith(EXTENSION))
						shapeFiles.add(file);
				}

			}
		} catch (Exception e) {
			logger.debug(e + "error");
		}
		return shapeFiles;
	}
}
