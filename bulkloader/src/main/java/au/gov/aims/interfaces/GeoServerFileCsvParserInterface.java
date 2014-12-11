/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
package au.gov.aims.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import au.gov.aims.model.GeoServerFile;

public interface GeoServerFileCsvParserInterface extends FileParserInterface {
	public List<GeoServerFile> parseGeoServerFileToJavaBean(String fileNameToParse) throws FileNotFoundException, IOException;
}