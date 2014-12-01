/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
*/

package com.gov.aims.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;

public class FileZipper {

	private List<String> fileToZip;

	public void zipSortedShapeFiles(List<List<File>> sortedShapeFiles,
			String outputFileLocation) {

		byte[] buffer = new byte[1024];
		String currentFilenameForZip;

		try {

//			System.out.println("Output to Zip : " + outputFileLocation);

			for (int i = 0; i < sortedShapeFiles.size(); i++) {
				
				currentFilenameForZip = FilenameUtils.removeExtension(sortedShapeFiles.get(i).get(0).getName());
				
				FileOutputStream fos = new FileOutputStream(outputFileLocation + "\\" + currentFilenameForZip + ".gs.shp.zip");
				ZipOutputStream zos = new ZipOutputStream(fos);
				
				for (File file : sortedShapeFiles.get(i)) {

//					System.out.println("File Added : " + file);
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

			

//			System.out.println("Done");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void zipListOfFiles(List<File> filesToZip, String outputFileLocation) {

		byte[] buffer = new byte[1024];

		try {

			FileOutputStream fos = new FileOutputStream(outputFileLocation);
			ZipOutputStream zos = new ZipOutputStream(fos);

//			System.out.println("Output to Zip : " + outputFileLocation);

			for (File file : filesToZip) {

//				System.out.println("File Added : " + file);
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

//			System.out.println("Done");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public List<String> getFileToZip() {
		return fileToZip;
	}

	public void setFileToZip(List<String> fileToZip) {
		this.fileToZip = fileToZip;
	}

}
