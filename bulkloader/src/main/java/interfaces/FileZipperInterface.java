package interfaces;

import java.io.File;
import java.util.List;

public interface FileZipperInterface {
	public void zipAll(List<File> files, String outputFileLocation);
}