/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
 */

package com.gov.aims.model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class CsvHandler {

	public List<ShapeFile> parseShapeFileCSVToBeanList(String filename)
			throws IOException {

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
		CSVReader reader = new CSVReader(new FileReader(filename));

		List<ShapeFile> shapeFile = csvToBean.parse(beanStrategy, reader);
//		System.out.println(shapeFile);
		reader.close();
		return shapeFile;
	}

	public void writeShapeFilesToCSVFromList(List<File> fileList,
			String fileName) throws IOException {
		FileWriter fileWriter = new FileWriter(fileName);
		CSVWriter csvWriter = new CSVWriter(fileWriter, ',');
		List<String[]> data = toStringArray(fileList);
		csvWriter.writeAll(data);

		csvWriter.close();
		fileWriter.close();
	}

	public List<String[]> toStringArray(List<File> fileList) {
		List<String[]> records = new ArrayList<String[]>();
		// add header record to the csv.
		records.add(new String[] { "storePath", "BASENAME", "storeName",
				"layerName", "workspace", "storeType", "title", "abstract",
				"metadataXmlHref", "keywords", "wmsPath", "styles",
				"uploadData", "uploadMetadata" });
		
		//Add new record per object in list with defaults entries to the csv.
		Iterator<File> it = fileList.iterator();
		while (it.hasNext()) {
			File file = it.next();
			records.add(new String[] { "./" + file.getName(),
					file.getName().substring(0, file.getName().length() - 11),
					"", "", "", "", "Shapefile", "", "something.xml",
					"e.g. Maritime Boundary", "", "", "FALSE", "TRUE" });
		}
		return records;
	}
}