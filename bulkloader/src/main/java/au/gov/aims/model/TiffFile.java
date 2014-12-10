/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
package au.gov.aims.model;

public class TiffFile {
//Attributes
	private String storePath;
	private String ORIG_STORE;
	private String REGION;
	private String SUBREGION;
	private String YEAR;
	private String MONTH;
	private String SCENE1;
	private String SCENE2;
	private String storeName;
	private String layerName;
	private String workspace;
	private String storeType;
	private String date;
	private String title;
	private String layerAbstract;
	private String metadataXmlHref;
	private String keywords;
	private String wmsPath;
	private String styles;
	private String uploadData;
	private String uploadMetadata;
	
//Getters and setters.		
	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public String getORIG_STORE() {
		return ORIG_STORE;
	}

	public void setORIG_STORE(String oRIG_STORE) {
		ORIG_STORE = oRIG_STORE;
	}

	public String getREGION() {
		return REGION;
	}

	public void setREGION(String rEGION) {
		REGION = rEGION;
	}

	public String getYEAR() {
		return YEAR;
	}

	public void setYEAR(String yEAR) {
		YEAR = yEAR;
	}

	public String getMONTH() {
		return MONTH;
	}

	public void setMONTH(String mONTH) {
		MONTH = mONTH;
	}

	public String getSCENE1() {
		return SCENE1;
	}

	public void setSCENE1(String sCENE1) {
		SCENE1 = sCENE1;
	}

	public String getSCENE2() {
		return SCENE2;
	}

	public void setSCENE2(String sCENE2) {
		SCENE2 = sCENE2;
	}

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	@Override
	public String toString() {
		return "TiffFile [storePath=" + storePath + ", ORIG_STORE="
				+ ORIG_STORE + ", REGION=" + REGION + ", YEAR=" + YEAR
				+ ", MONTH=" + MONTH + ", SCENE1=" + SCENE1 + ", SCENE2="
				+ SCENE2 + ", storeName=" + storeName + ", layerName="
				+ layerName + ", workspace=" + workspace + ", storeType="
				+ storeType + ", Date=" + date + ", title=" + title
				+ ", layerAbstract=" + layerAbstract + ", metadataXmlHref="
				+ metadataXmlHref + ", keywords=" + keywords + ", wmsPath="
				+ wmsPath + ", styles=" + styles + ", uploadData=" + uploadData
				+ ", uploadMetadata=" + uploadMetadata + "]";
	}

	public String getSUBREGION() {
		return SUBREGION;
	}

	public void setSUBREGION(String sUBREGION) {
		SUBREGION = sUBREGION;
	}
}