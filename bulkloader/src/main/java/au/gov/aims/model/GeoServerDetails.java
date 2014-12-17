/**
 * Author Zoe McIntosh
 */
package au.gov.aims.model;

public class GeoServerDetails {

	private String GeoServerHttpAddress;
	private String Username;
	
	public GeoServerDetails() {
        this(null, null);
    }

	public GeoServerDetails(String GeoServerHttpAddress, String Username) {
		this.GeoServerHttpAddress = GeoServerHttpAddress;
        this.Username = Username;
	}
	
	public String getGeoServerHttpAddress() {
        return GeoServerHttpAddress;
    }

    public void setGeoServerHttpAddress(String GeoServerHttpAddress) {
        this.GeoServerHttpAddress = GeoServerHttpAddress;
    }

	public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

}
