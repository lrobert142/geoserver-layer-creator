/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
*/

package com.gov.aims.utilities;

import interfaces.ShapeFileZipperInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Handles the zipping of shape files.
 *
 */
public class ShapeFileZipper implements ShapeFileZipperInterface {

	//Attributes
	public List<String> fileToZip;
	public Logger logger;
	private final static String outputZipExtension = ".zip";

	/**
	 * Handles the zipping of sorted shape files
	 */
	public ShapeFileZipper(){
		BasicConfigurator.configure();
		logger = Logger.getLogger(ShapeFileZipper.class);
	}
	
	/**
	 * Takes a List of File object lists and zips each list to a desired output location. 
	 *
	 * @param The sorted List of File object Lists to be zipped.
	 * @param The absolute path to the desired output location for the zips.
	 */
	public void zipSortedShapeFiles(List<List<File>> sortedShapeFiles, String outputFileLocation) {

		byte[] buffer = new byte[1024];
		String currentFilenameForZip;

		try {
			for (int i = 0; i < sortedShapeFiles.size(); i++) {
				String parentDirectory;
				currentFilenameForZip = FilenameUtils.removeExtension(sortedShapeFiles.get(i).get(0).getName());
				
				outputFileLocation = sortedShapeFiles.get(i).get(0).getParent();
				
				FileOutputStream fos = new FileOutputStream(outputFileLocation + "\\" + currentFilenameForZip + outputZipExtension);
				ZipOutputStream zos = new ZipOutputStream(fos);
				
				for (File file : sortedShapeFiles.get(i)) {
					parentDirectory = file.getParent();
					outputFileLocation = parentDirectory;

					ZipEntry ze = new ZipEntry(file.getName());
					zos.putNextEntry(ze);

					FileInputStream in = new FileInputStream(file);

					int len;
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
					in.close();
				}
				zos.closeEntry();
				zos.close();
			}

		} catch (IOException e){
			logger.debug(e.getStackTrace() + "An error has occured whilst attempting to open a directory.");
		} catch (IndexOutOfBoundsException e){
			logger.debug(e.getStackTrace() + "An error has occured whilst attempting to iterate through a list of files, most likely referenced an index that does not exist.");
		} catch (Exception e){
			logger.debug(e.getStackTrace() + "ERROR - Some other unspecified error has occured whilst iterating through a list of files.");
		}
	}

	@Override
	public void zipAll(List<File> files, String outputFileLocation) {
		byte[] buffer = new byte[1024];

		try {
				FileOutputStream fos = new FileOutputStream(outputFileLocation + outputZipExtension);
				ZipOutputStream zos = new ZipOutputStream(fos);
				
				for (File file : files) {
					ZipEntry ze = new ZipEntry(file.getName());
					zos.putNextEntry(ze);

					FileInputStream in = new FileInputStream(file);

					int len;
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
					in.close();
				}
				zos.closeEntry();
				zos.close();

		} catch (IOException e){
			logger.debug(e.getStackTrace() + "An error has occured whilst attempting to open a directory.");
		} catch (IndexOutOfBoundsException e){
			logger.debug(e.getStackTrace() + "An error has occured whilst attempting to iterate through a list of files, most likely referenced an index that does not exist.");
		} catch (Exception e){
			logger.debug(e.getStackTrace() + "ERROR - Some other unspecified error has occured whilst iterating through a list of files.");
		}
	}
}