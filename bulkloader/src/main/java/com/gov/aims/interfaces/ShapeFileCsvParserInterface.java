/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
package com.gov.aims.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.gov.aims.model.ShapeFile;

public interface ShapeFileCsvParserInterface extends FileParserInterface {
	public List<ShapeFile> parseShapeFileToJavaBean(String fileNameToParse) throws FileNotFoundException, IOException;
}