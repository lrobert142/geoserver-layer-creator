/**
 * Author Zoe McIntosh
 */
package au.gov.aims.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
