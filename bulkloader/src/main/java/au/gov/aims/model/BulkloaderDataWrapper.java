/**
@author Zoe McIntosh
@version 1.0
@since 18/12/14
 */
package au.gov.aims.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Used to save the Http Address and User name to the XML file. 
 */
@XmlRootElement(name = "GeoServerDetails")
public class BulkloaderDataWrapper {
	private List<GeoServerDetails> geoServerDetailsList;

	@XmlElement(name = "GeoServerDetails")
	public List<GeoServerDetails> getDetails() {
	    return geoServerDetailsList;
	}

	public void setDetails(List<GeoServerDetails> geoServerDetailsList) {
	    this.geoServerDetailsList = geoServerDetailsList;
	}
}