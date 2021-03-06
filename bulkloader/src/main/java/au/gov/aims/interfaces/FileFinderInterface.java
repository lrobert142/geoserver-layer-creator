/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
package au.gov.aims.interfaces;

import java.io.File;
import java.util.List;

public interface FileFinderInterface {
	public List<File> findAll(String targetDirectory);
	
	public List<File> findAllByExtensionList(String targetDirectory, List<String> extensions);
	
	public List<File> findAllBySingleExtension(String targetDirectory, String extension);
}