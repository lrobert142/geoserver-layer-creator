package interfaces;

import java.io.File;
import java.util.List;

public interface FileFinderInterface {
	
	public List<File> findAll(String targetDirectory);
	
	public List<File> findAllByExtensionList(String targetDirectory, List<String> extensions);
	
	public List<File> findAllBySingleExtension(String targetDirectory, String extension);
	
	
	

}
