package interfaces;

import java.io.File;
import java.util.List;

public interface FileParserInterface {
	public void writeFilesToCsv(List<File> file, String targetFileName);
	
	public List<String[]> toStringArray(List<File> fileList);
}