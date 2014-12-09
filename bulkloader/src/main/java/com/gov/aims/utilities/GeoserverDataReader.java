package com.gov.aims.utilities;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.HTTPUtils;
import it.geosolutions.geoserver.rest.decoder.RESTCoverage;
import it.geosolutions.geoserver.rest.decoder.RESTFeatureType;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import it.geosolutions.geoserver.rest.decoder.RESTLayerList;
import it.geosolutions.geoserver.rest.decoder.RESTWorkspaceList;
import it.geosolutions.geoserver.rest.decoder.utils.JDOMBuilder;
import it.geosolutions.geoserver.rest.encoder.metadatalink.GSMetadataLinkInfoEncoder;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.gov.aims.model.ShapeFile;

/**
 * Handles reading data from a GeoServer instance into more programmer friendly
 * data types such as <code>ShapeFile</code> objects.
 * 
 * @version 1.0.0
 * @since 9/12/14
 * @author Lachlan Robertson
 */
public class GeoserverDataReader {
	private Logger logger;
	private String restUrl;
	private String username;
	private String password;
	private GeoServerRESTReader reader;

	/**
	 * Creates a reader for the specified GeoServer instance using the url,
	 * username and password for the server.
	 * 
	 * @param restUrl
	 *            The <code>URL</code> of the GeoServer instance. Normally
	 *            <code>http://localhost:8080/geoserver/web/</code> if server is
	 *            run locally.
	 * @param username
	 *            An authorized username on the GeoServer instance.
	 * @param password
	 *            The authorized password for the given username
	 * @throws MalformedURLException
	 *             If the URL is incorrectly formatted, has invalid protocol or
	 *             cannot be properly parsed.
	 */
	public GeoserverDataReader(String restUrl, String username, String password)
			throws MalformedURLException {
		this.restUrl = restUrl;
		this.username = username;
		this.password = password;

		logger = Logger.getLogger(GeoserverDataReader.class);
		reader = new GeoServerRESTReader(restUrl, username, password);
	}

	// List in format Workspace, LayerName, Type (VECTOR / OTHER)
	/**
	 * Gets all layers from all workspaces from the GeoServer instance.
	 * 
	 * @return A list of <code>List&ltString&gt</code> objects containing all
	 *         layer data including what type of data it is (VECTOR or OTHER).<br>
	 *         Each inner list is in the form
	 *         <code>[workspace, layerName, layerType]</code>
	 */
	public List<List<String>> getAllLayersFromGeoserver() {
		List<List<String>> allLayerDetails = new ArrayList<List<String>>();
		String currentLayerName = "";
		String currentWorkspaceName = "";

		RESTLayerList allLayers = reader.getLayers();
		RESTWorkspaceList allWorkspaces = reader.getWorkspaces();

		for (int i = 0; i < allWorkspaces.size(); i++) {
			currentWorkspaceName = allWorkspaces.get(i).getName();
			for (int j = 0; j < allLayers.size(); j++) {
				currentLayerName = allLayers.get(j).getName();
				RESTLayer currentLayer = reader.getLayer(currentWorkspaceName,
						currentLayerName);
				if (currentLayer != null) {
					List<String> currentLayerDetails = new ArrayList<String>();
					currentLayerDetails.add(currentWorkspaceName);
					currentLayerDetails.add(currentLayerName);
					if (currentLayer.getType().toString() == "VECTOR") {
						currentLayerDetails.add("VECTOR");
					} else {
						currentLayerDetails.add("OTHER");
					}
					allLayerDetails.add(currentLayerDetails);
				}
			}
		}
		logger.info("All layers successfully retrieved!");
		return allLayerDetails;
	}

	/**
	 * Gets data for a <code>Feature</code> in a given layer and generates a
	 * <code>ShapeFile</code> object containing this data.<br>
	 * Note that the <code>uploadData</code> and <code>uploadMetadata</code>
	 * attributes will default to "FALSE" and <code>storePath</code> will
	 * default to "N/A"
	 * 
	 * @param workspaceName
	 *            The name of the workspace the layer is part of.
	 * @param layerName
	 *            The name of the layer.
	 * @return A <code>ShapeFile</code> object containing relevant data from the
	 *         GeoServer instance.
	 * @throws MalformedURLException
	 *             If the URL is incorrectly formatted, has invalid protocol or
	 *             cannot be properly parsed.
	 */
	protected ShapeFile getLayerDataFromGeoserver(String workspaceName,
			String layerName) throws MalformedURLException {
		RESTLayer layer = reader.getLayer(workspaceName, layerName);
		String url = restUrl + "/rest/layers/" + workspaceName + ":"
				+ layerName + ".xml";
		Element baseLayerElement = getLayerElement(url);
		RESTFeatureType feature = reader.getFeatureType(layer);
		ShapeFile shpFile = new ShapeFile();

		String baseName = feature.getNativeName();
		String storeName = feature.getStoreName();
		String storeType = feature.getStoreType();
		String title = feature.getTitle();
		String abstractText = feature.getAbstract();
		String metaDataXmlHref = getMetaDataXmlHref(feature);
		String keywords = feature.getKeywords().toString();
		keywords = keywords.substring(1, keywords.length() - 1);
		String wmsPath = getWmsPath(baseLayerElement);
		String styles = getStyles(layer.getDefaultStyle(), baseLayerElement);
		String storePath = "N/A";

		logger.info("Getting data for feature " + workspaceName + ":"
				+ layerName);

		shpFile.setStorePath(storePath == null ? "" : storePath);
		shpFile.setBaseName(baseName == null ? "" : baseName);
		shpFile.setStoreName(storeName == null ? "" : storeName);
		shpFile.setLayerName(layerName == null ? "" : layerName);
		shpFile.setWorkspace(workspaceName == null ? "" : workspaceName);
		shpFile.setStoreType(storeType == null ? "" : storeType);
		shpFile.setTitle(title == null ? "" : title);
		shpFile.setLayerAbstract(abstractText == null ? "" : abstractText);
		shpFile.setMetadataXmlHref(metaDataXmlHref == null ? ""
				: metaDataXmlHref);
		shpFile.setKeywords(keywords == null ? "" : keywords);
		shpFile.setWmsPath(wmsPath == null ? "" : wmsPath);
		shpFile.setStyles(styles == null ? "" : styles);
		shpFile.setUploadData("FALSE");
		shpFile.setUploadMetadata("FALSE");
		return shpFile;
	}

	/**
	 * Gets data for a <code>Coverage</code> in a given layer and generates a
	 * <code>ShapeFile</code> object containing this data.<br>
	 * Note that the <code>uploadData</code> and <code>uploadMetadata</code>
	 * attributes will default to "FALSE" and <code>storePath</code> will
	 * default to "N/A"
	 * 
	 * @param workspaceName
	 *            The name of the workspace the layer is part of.
	 * @param layerName
	 *            The name of the layer.
	 * @return A <code>ShapeFile</code> object containing relevant data from the
	 *         GeoServer instance.
	 * @throws MalformedURLException
	 *             If the URL is incorrectly formatted, has invalid protocol or
	 *             cannot be properly parsed.
	 */
	protected ShapeFile getCoverageDataFromGeoserver(String workspaceName,
			String layerName) throws MalformedURLException {
		RESTCoverage coverage = reader.getCoverage(reader.getLayer(
				workspaceName, layerName));
		String url = restUrl + "/rest/layers/" + workspaceName + ":"
				+ layerName + ".xml";
		Element baseLayerElement = getLayerElement(url);
		ShapeFile shpFile = new ShapeFile();

		String baseName = coverage.getNativeName();
		String storeName = coverage.getStoreName();
		String storeType = coverage.getStoreType();
		String title = coverage.getTitle();
		String abstractText = coverage.getAbstract();
		String metaDataXmlHref = getMetaDataXmlHref(coverage);
		String keywords = coverage.getKeywords().toString();
		keywords = keywords.substring(1, keywords.length() - 1);
		String wmsPath = getWmsPath(baseLayerElement);
		String styles = getStyles("", baseLayerElement);
		String storePath = "N/A";

		logger.info("Getting data for coverage " + workspaceName + ":"
				+ layerName);
		shpFile.setStorePath(storePath == null ? "" : storePath);
		shpFile.setBaseName(baseName == null ? "" : baseName);
		shpFile.setStoreName(storeName == null ? "" : storeName);
		shpFile.setLayerName(layerName == null ? "" : layerName);
		shpFile.setWorkspace(workspaceName == null ? "" : workspaceName);
		shpFile.setStoreType(storeType == null ? "" : storeType);
		shpFile.setTitle(title == null ? "" : title);
		shpFile.setLayerAbstract(abstractText == null ? "" : abstractText);
		shpFile.setMetadataXmlHref(metaDataXmlHref == null ? ""
				: metaDataXmlHref);
		shpFile.setKeywords(keywords == null ? "" : keywords);
		shpFile.setWmsPath(wmsPath == null ? "" : wmsPath);
		shpFile.setStyles(styles == null ? "" : styles);
		shpFile.setUploadData("FALSE");
		shpFile.setUploadMetadata("FALSE");

		return shpFile;
	}

	/**
	 * Retrieves all metadata 'href' attributes for a given feature and returns
	 * them as a <code>String</code>
	 * 
	 * @param feature
	 *            The feature to retrieve all metadata 'href' attributes for.
	 * @return A string representation of all metadata 'href' attributes,
	 *         separated with a comma and a space (, ) or an empty string if
	 *         none exist.
	 */
	private String getMetaDataXmlHref(RESTFeatureType feature) {
		List<String> metaDataHrefList = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		List<GSMetadataLinkInfoEncoder> metaDataEncs = feature
				.getEncodedMetadataLinkInfoList();
		if (metaDataEncs == null)
			return "";

		for (int i = 0; i < metaDataEncs.size(); i++) {
			GSMetadataLinkInfoEncoder metaDataEnc = metaDataEncs.get(i);
			metaDataHrefList.add(metaDataEnc.getContent());
		}

		for (String s : metaDataHrefList) {
			sb.append(s).append(", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}

	/**
	 * Retrieves all metadata 'href' attributes for a given coverage and returns
	 * them as a <code>String</code>
	 * 
	 * @param feature
	 *            The feature to retrieve all metadata 'href' attributes for.
	 * @return A string representation of all metadata 'href' attributes,
	 *         separated with a comma and a space (, ) or an empty string if
	 *         none exist.
	 */
	private String getMetaDataXmlHref(RESTCoverage coverage) {
		List<String> metaDataHrefList = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		List<GSMetadataLinkInfoEncoder> metaDataEncs = coverage
				.getEncodedMetadataLinkInfoList();
		if (metaDataEncs == null)
			return "";

		for (int i = 0; i < metaDataEncs.size(); i++) {
			GSMetadataLinkInfoEncoder metaDataEnc = metaDataEncs.get(i);
			metaDataHrefList.add(metaDataEnc.getContent());
		}

		for (String s : metaDataHrefList) {
			sb.append(s).append(", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}

	/**
	 * Gets the 'wmsPath' for a given element in an XML document on the
	 * GeoServer instance.
	 * 
	 * @param layerEl
	 *            The base layer element in an XML document.
	 * @return A string representation of the returned XML value or null if it
	 *         doesn't exist.
	 */
	private String getWmsPath(Element layerEl) {
		return layerEl.getChildText("path") == null ? null : layerEl
				.getChildText("path");
	}

	/**
	 * Gets all styles for a given element from an XML document on the GeoServer
	 * instance, including the default style, and returns them as a single
	 * string separated by a comma and a single space (, ).
	 * 
	 * @param defaultStyle
	 *            The default style. Can be null
	 * @param layerEl
	 *            The base layer element in an XML document.
	 * @return A string representation of all styles for the element. If no
	 *         styles are available then an empty string will be returned.
	 * @throws MalformedURLException
	 *             If the URL is incorrectly formatted, has invalid protocol or
	 *             cannot be properly parsed.
	 */
	@SuppressWarnings("unchecked")
	private String getStyles(String defaultStyle, Element layerEl)
			throws MalformedURLException {
		String stylesString = "";
		if (defaultStyle != null)
			stylesString += defaultStyle + ", ";
		Element stylesEl = layerEl.getChild("styles");
		if (stylesEl == null) {
			stylesString = stylesString.substring(0, stylesString.length() - 2);
			return stylesString;
		}

		List<Element> styleElements = stylesEl.getChildren();
		for (Element styleEl : styleElements) {
			String styleValue = styleEl.getValue().trim().replace("\n", " ")
					.replace(" ", "");
			if (!styleValue.isEmpty())
				stylesString += styleValue + ", ";
		}
		stylesString = stylesString.substring(0, stylesString.length() - 2);
		return stylesString;
	}

	/**
	 * Builds an element from the passed in <code>response</code>.
	 * 
	 * @param response
	 *            A HTTP response made to the GeoServer instance
	 * @return A single <code>Element</code> object or null if response if null.
	 */
	private Element build(String response) {
		if (response == null)
			return null;
		return JDOMBuilder.buildElement(response);
	}

	/**
	 * Creates and returns an element from the GeoServer instance at the given
	 * URL
	 * 
	 * @param url
	 *            The URL of the element location
	 * @return An <code>Element</code> object on success of null on failure.
	 */
	private Element getLayerElement(String url) {
		String response = HTTPUtils.get(url, username, password).toString();
		return build(response);
	}

	public String getRestUrl() {
		return restUrl;
	}

	public void setRestUrl(String restUrl) {
		this.restUrl = restUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public GeoServerRESTReader getReader() {
		return reader;
	}

	public void setReader(GeoServerRESTReader reader) {
		this.reader = reader;
	}
}
