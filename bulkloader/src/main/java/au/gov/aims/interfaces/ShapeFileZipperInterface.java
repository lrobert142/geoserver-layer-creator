/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
package au.gov.aims.interfaces;

import java.io.File;
import java.util.List;

public interface ShapeFileZipperInterface extends FileZipperInterface {
	public void zipSortedShapeFiles(List<List<File>> sortedShapeFiles,
			String outputFileLocation);
}