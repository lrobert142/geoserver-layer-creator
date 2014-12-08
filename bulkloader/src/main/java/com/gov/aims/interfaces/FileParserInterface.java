/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
package com.gov.aims.interfaces;

import java.io.File;
import java.util.List;

public interface FileParserInterface {
	public void writeFilesToCsv(List<File> file, String targetFileName);
	
	public List<String[]> toStringArray(List<File> fileList);
}