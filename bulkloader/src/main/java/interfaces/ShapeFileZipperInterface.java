package interfaces;

import java.io.File;
import java.util.List;

public interface ShapeFileZipperInterface extends FileZipperInterface {
	public void zipSortedShapeFiles(List<List<File>> sortedShapeFiles,
			String outputFileLocation);
}