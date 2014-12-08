/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/

package com.gov.aims.utilities;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import com.gov.aims.interfaces.TiffFileCsvParserInterface;
import com.gov.aims.model.TiffFile;

public class TiffFileCsvParser implements TiffFileCsvParserInterface {

	// Attributes
	public Logger logger;

	// Constructor
	public TiffFileCsvParser() {
		logger = Logger.getLogger(TiffFileCsvParser.class);
	}
	
	/**
	 * Takes a list of files and the target directory then writes the files to
	 * the .csv
	 * 
	 * @param A list of file objects.
	 * @param The target directory to write to.
	 */
	@Override
	public void writeFilesToCsv(List<File> file, String targetFileName) {
		try {
			Collections.sort(file);
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
	 * Takes a list of files to be converted to a string array for writing to .csv.
	 * 
	 * @param A list of file objects.
	 */
	@Override
	public List<String[]> toStringArray(List<File> fileList) {
		List<String[]> records = new ArrayList<String[]>();
		// add header record to the csv.
		records.add(new String[] { "storePath", "ORIG_STORE", "REGION", "SUBREGION", "YEAR", "MONTH", "SCENE1", "SCENE2", "storeName", "layerName",
				"workspace", "storeType", "date", "title", "abstract", "metadataXmlHref", "keywords", "wmsPath", "styles",
				"uploadData", "uploadMetadata" });

		// Add new record per object in list with defaults entries to the csv.
		Iterator<File> it = fileList.iterator();
		while (it.hasNext()) {
			try {
				File file = it.next();
				records.add(new String[] {
						backslashToForwardslash(file.getAbsolutePath()),
						file.getName().substring(0, file.getName().length() - 4), "",
						"", "", "", "", "", "", "", "", "GeoTIFF", "", "", "", "", "", "", "", "TRUE", "TRUE" });
			} catch (IndexOutOfBoundsException e) {
				logger.debug(e.getStackTrace() + "An error occured when writing object to .csv - file open or caused by too many defaults or not enough to match number of columns");
			} catch (Exception e) {
				logger.debug(e.getStackTrace() + "Some other error has occured whilst trying to write an object to a .csv");
			}
		}
		return records;
	}

	/**
	 * Loads a tiff/raster .csv and maps the rows to TiffFile objects. A list of TiffFile Objects is returned.
	 * 
	 * @param The target .csv file to read and parse to java beans.
	 */
	@Override
	public List<TiffFile> parseTiffFileToJavaBean(String fileNameToParse) throws IOException {
		HeaderColumnNameTranslateMappingStrategy<TiffFile> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<TiffFile>();
		beanStrategy.setType(TiffFile.class);

		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("storePath", "storePath");
		columnMapping.put("ORIG_STORE", "ORIG_STORE");
		columnMapping.put("REGION", "REGION");
		columnMapping.put("SUBREGION", "SUBREGION");
		columnMapping.put("YEAR", "YEAR");
		columnMapping.put("MONTH", "MONTH");
		columnMapping.put("SCENE1", "SCENE1");
		columnMapping.put("SCENE2", "SCENE2");
		columnMapping.put("storeName", "storeName");
		columnMapping.put("layerName", "layerName");
		columnMapping.put("workspace", "workspace");
		columnMapping.put("storeType", "storeType");
		columnMapping.put("date", "date");
		columnMapping.put("title", "title");
		columnMapping.put("abstract", "layerAbstract");
		columnMapping.put("metadataXmlHref", "metadataXmlHref");
		columnMapping.put("keywords", "keywords");
		columnMapping.put("wmsPath", "wmsPath");
		columnMapping.put("styles", "styles");
		columnMapping.put("uploadData", "uploadData");
		columnMapping.put("uploadMetadata", "uploadMetadata");

		beanStrategy.setColumnMapping(columnMapping);

		CsvToBean<TiffFile> csvToBean = new CsvToBean<TiffFile>();
		CSVReader reader = new CSVReader(new FileReader(fileNameToParse));

		List<TiffFile> TiffFiles = csvToBean.parse(beanStrategy, reader);
		reader.close();
		return TiffFiles;
	}

	/**
	 * Converts absolute file paths containing '\' characters and converts them
	 * to '/' characters
	 *
	 * @param Absoulte
	 *            path for a file to be converted.
	 * 
	 * @return The converted string.
	 */
	public String backslashToForwardslash(String path) {
		StringBuilder builder = new StringBuilder();
		char[] pathArray = path.toCharArray();

		for (int j = 0; j < pathArray.length; j++) {
			if (pathArray[j] == '\\')
				pathArray[j] = '/';
			builder.append(pathArray[j]);
		}
		return builder.toString();
	}
}