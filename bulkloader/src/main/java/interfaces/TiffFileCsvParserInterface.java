/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
package interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.gov.aims.model.TiffFile;

public interface TiffFileCsvParserInterface extends FileParserInterface{
	
	public List<TiffFile> parseTiffFileToJavaBean(String fileNameToParse) throws FileNotFoundException, IOException;

}
