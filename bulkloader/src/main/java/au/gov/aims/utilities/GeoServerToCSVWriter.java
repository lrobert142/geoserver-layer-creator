package au.gov.aims.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import au.gov.aims.model.ShapeFile;

/**
 * Handles writing of data from the GeoServer instance to a .csv file.
 * 
 * @version 1.0.0
 * @since 9/12/14
 * @author Lachlan Robertson
 */
public class GeoServerToCSVWriter {
	private Logger logger;

	/**
	 * Default constructor. Exists to initialize attributes.
	 */
	public GeoServerToCSVWriter() {
		logger = Logger.getLogger(GeoServerToCSVWriter.class);
	}

	/**
	 * Writes data from the <code>dataSets</code> list and from the GeoServer
	 * instance into a single .csv file.
	 * 
	 * @param dataSets
	 *            A list of <code>ShapeFile</code> objects containing data from
	 *            the GeoServer.
	 * @param dataRetriever
	 *            A reader to get data from the GeoServer instance.
	 * @param fileName
	 *            The name of the .csv file to save to.
	 * @return <code>true</code> if all data could be successfully written or
	 *         <code>false</code> otherwise.
	 * @throws MalformedURLException
	 *             If the URL is incorrectly formatted, has invalid protocol or
	 *             cannot be properly parsed.
	 */
	public boolean storeLayerDataInCsv(List<List<String>> dataSets,
			GeoServerDataReader dataRetriever, String fileName) throws MalformedURLException {
		String workspace;
		String layerName;
		String layerType;
		List<ShapeFile> layerDataSets = new ArrayList<ShapeFile>();

		for (List<String> layerData : dataSets) {
			workspace = layerData.get(0);
			layerName = layerData.get(1);
			layerType = layerData.get(2);
			if (layerType == "VECTOR")
				layerDataSets.add(dataRetriever.getLayerDataFromGeoserver(workspace, layerName));
			else
				layerDataSets.add(dataRetriever.getCoverageDataFromGeoserver(workspace, layerName));
		}
		return writeDataSetsToCsvFile(layerDataSets, fileName);
	}

	/**
	 * Writes data from a list of <code>ShapeFile</code> objects to a single
	 * .csv file using {@link HeaderColumnNameTranslateMappingStrategy}
	 * 
	 * @param dataSets
	 *            A list of <code>ShapeFile</code> objects containing data from
	 *            the GeoServer.
	 * @param targetFileName
	 *            The .csv file name to save the data to.
	 * @return <code>true</code> if all data could be successfully written or
	 *         <code>false</code> otherwise.
	 */
	protected boolean writeDataSetsToCsvFile(List<ShapeFile> dataSets, String targetFileName) {
		FileWriter fWriter = null;
		CSVWriter csvWriter = null;
		boolean successful = true;
		try {
			fWriter = new FileWriter(new File(targetFileName));
			csvWriter = new CSVWriter(fWriter, ',');
			List<String[]> dataToWrite = new ArrayList<String[]>();
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

			dataToWrite.add(new String[] { "storePath", "BASENAME", "storeName", "layerName", "workspace", "storeType",
					"title", "abstract", "metadataXmlHref", "keywords", "wmsPath", "styles", "uploadData", "uploadMetadata" });

			for (ShapeFile dataSet : dataSets) {
				dataToWrite.add(new String[] { dataSet.getStorePath(),
						dataSet.getBaseName(), dataSet.getStoreName(),
						dataSet.getLayerName(), dataSet.getWorkspace(),
						dataSet.getStoreType(), dataSet.getTitle(),
						dataSet.getLayerAbstract(),
						dataSet.getMetadataXmlHref(), dataSet.getKeywords(),
						dataSet.getWmsPath(), dataSet.getStyles(),
						dataSet.getUploadData(), dataSet.getUploadMetadata() });
			}
			csvWriter.writeAll(dataToWrite);
		} catch (IOException e) {
			logger.error("File not found or could not be written to!");
			successful = false;
		} finally {
			try {
				csvWriter.close();
				fWriter.close();
			} catch (Exception ex) {
			}
		}
		return successful;
	}
}