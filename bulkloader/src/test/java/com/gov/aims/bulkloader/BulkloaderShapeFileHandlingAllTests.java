/**
@author Stuart Garrigan
@version 1.0.0
@since 8/12/14
**/
package com.gov.aims.bulkloader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ShapeFileCSVParserTest.class, ShapeFileFinderTest.class,
		FileHandlerWrapperTest.class, ShapeFileSorterTest.class,
		ShapeFileZipperTest.class})
public class BulkloaderShapeFileHandlingAllTests {

}