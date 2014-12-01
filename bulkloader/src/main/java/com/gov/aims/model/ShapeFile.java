/*
@author	Stuart Garrigan
@version 1.0
@since 27-11-2014
*/
package com.gov.aims.model;

public class ShapeFile {
	
	private String storePath;
	private String baseName;
	private String storeName;
	private String layerName;
	private String workspace;
	private String storeType;
	private String title;
	private String layerAbstract;
	private String metadataXmlHref;
	private String keywords;
	private String wmsPath;
	private String styles;
	private String uploadData;
	private String uploadMetadata;
	
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getLayerName() {
		return layerName;
	}
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}
	public String getWorkspace() {
		return workspace;
	}
	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLayerAbstract() {
		return layerAbstract;
	}
	public void setLayerAbstract(String layerAbstract) {
		this.layerAbstract = layerAbstract;
	}
	public String getMetadataXmlHref() {
		return metadataXmlHref;
	}
	public void setMetadataXmlHref(String metadataXmlHref) {
		this.metadataXmlHref = metadataXmlHref;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getWmsPath() {
		return wmsPath;
	}
	public void setWmsPath(String wmsPath) {
		this.wmsPath = wmsPath;
	}
	public String getStyles() {
		return styles;
	}
	public void setStyles(String styles) {
		this.styles = styles;
	}
	public String getUploadData() {
		return uploadData;
	}
	public void setUploadData(String uploadData) {
		this.uploadData = uploadData;
	}
	public String getUploadMetadata() {
		return uploadMetadata;
	}
	public void setUploadMetadata(String uploadMetadata) {
		this.uploadMetadata = uploadMetadata;
	}
	
		public String getStorePath() {
		return storePath;
	}
	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}
	public String getBaseName() {
		return baseName;
	}
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}
	
	@Override
	public String toString() {
		return "ShapeFile [storePath=" + storePath + ", baseName=" + baseName
				+ ", storeName=" + storeName + ", layerName=" + layerName
				+ ", workspace=" + workspace + ", storeType=" + storeType
				+ ", title=" + title + ", layerAbstract=" + layerAbstract
				+ ", metadataXmlHref=" + metadataXmlHref + ", keywords="
				+ keywords + ", wmsPath=" + wmsPath + ", styles=" + styles
				+ ", uploadData=" + uploadData + ", uploadMetadata="
				+ uploadMetadata + "]";
	}
	
	
	
	
	
	
	
	

}
