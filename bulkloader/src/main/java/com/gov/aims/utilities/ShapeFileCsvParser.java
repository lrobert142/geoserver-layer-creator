/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
 */

package com.gov.aims.utilities;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import com.gov.aims.interfaces.ShapeFileCsvParserInterface;
import com.gov.aims.model.ShapeFile;

/**
 * Handles the writing and parsing of shape files to and from .csv.
 *
 */

public class ShapeFileCsvParser implements ShapeFileCsvParserInterface {
	// Attributes
	public Logger logger;

	// Constructor
	public ShapeFileCsvParser() {
		logger = Logger.getLogger(ShapeFileCsvParser.class);
	}

	/**
	 * Access a .csv and parse each row into a java bean. Using
	 * HeadingColumnNameTranslateMappingStrategy.
	 *
	 * @param The
	 *            absolute path of the desired shapeFile .csv to parse.
	 * @return Returns a a list of files parsed to a java bean.
	 */
	@Override
	public List<ShapeFile> parseShapeFileToJavaBean(String fileNameToParse) throws IOException {
		HeaderColumnNameTranslateMappingStrategy<ShapeFile> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<ShapeFile>();
		beanStrategy.setType(ShapeFile.class);

		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("storePath", "storePath");
		columnMapping.put("BASENAME", "baseName");
		columnMapping.put("storeName", "storeName");
		columnMapping.put("layerName", "layerName");
		columnMapping.put("workspace", "workspace");
		columnMapping.put("storeType", "storeType");
		columnMapping.put("title", "title");
		columnMapping.put("abstract", "layerAbstract");
		columnMapping.put("metadataXmlHref", "metadataXmlHref");
		columnMapping.put("keywords", "keywords");
		columnMapping.put("wmsPath", "wmsPath");
		columnMapping.put("styles", "styles");
		columnMapping.put("uploadData", "uploadData");
		columnMapping.put("uploadMetadata", "uploadMetadata");

		beanStrategy.setColumnMapping(columnMapping);

		CsvToBean<ShapeFile> csvToBean = new CsvToBean<ShapeFile>();
		CSVReader reader = new CSVReader(new FileReader(fileNameToParse));

		List<ShapeFile> shapeFile = csvToBean.parse(beanStrategy, reader);
		reader.close();
		return shapeFile;
	}

	/**
	 * Write a list of files to a specified .csv. Takes a list of files which
	 * are converted to a List of String Arrays before being written the .csv.
	 *
	 * @param The
	 *            list of files to be written to a .csv
	 * @param The
	 *            absolute path of the desired output location of the written
	 *            .csv.
	 */
	@Override
	public void writeFilesToCsv(List<File> file, String targetFileName) {
		try {
			FileWriter fileWriter = new FileWriter(targetFileName);
			CSVWriter csvWriter = new CSVWriter(fileWriter, ',');
			
			List<String[]> data = toStringArray(file);
			csvWriter.writeAll(data);

			csvWriter.close();
			fileWriter.close();
		} catch (Exception e) {
			logger.debug(e.getStackTrace() + "An error has occured when writing to a .csv");
		}
	}
	
	/**
	 * Converts absolute file paths containing '\' characters and converts them to '/' characters
	 *
	 * @param Absoulte path for a file to be converted.
	 * 
	 * @return The converted string.
	 */
	public String backslashToForwardslash(String path) {
		StringBuilder builder = new StringBuilder();
		char[] pathArray = path.toCharArray();
		
		for (int j = 0; j < pathArray.length; j++) {
			if(pathArray[j] == '\\')
				pathArray[j] = '/';
			builder.append(pathArray[j]);
		}
		return builder.toString();
	}
	
	/**
	 * Convert a list of File objects to a List of String Arrays.
	 *
	 * @param The
	 *            list of files to be written to be converted.
	 * 
	 * @return a List of String Arrays.
	 */
	public List<String[]> toStringArray(List<File> fileList) {
		List<String[]> records = new ArrayList<String[]>();
		// add header record to the csv.
		records.add(new String[] { "storePath", "BASENAME", "storeName",
				"layerName", "workspace", "storeType", "title", "abstract",
				"metadataXmlHref", "keywords", "wmsPath", "styles",
				"uploadData", "uploadMetadata" });

		// Add new record per object in list with defaults entries to the csv.
		Iterator<File> it = fileList.iterator();
		while (it.hasNext()) {
			try {
				File file = it.next();
				records.add(new String[] {backslashToForwardslash(file.getAbsolutePath()),
						file.getName().substring(0, file.getName().length() - 4),
						"", "", "", "", "Shapefile", "", "something.xml",
						"e.g. Maritime Boundary", "", "", "FALSE", "TRUE" });
			
			} catch (IndexOutOfBoundsException e) {
				logger.debug(e.getStackTrace() + "An error occured when writing object to .csv, likely caused by too many defaults or not enough to match number of columns");
			} catch (Exception e) {
				logger.debug(e.getStackTrace() + "Some other error has occured whilst trying to write an object to a .csv");
			}
		}
		return records;
	}
}