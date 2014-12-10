/*
@author	Stuart Garrigan
@version 1.0
@since 8-12-2014
 */
package au.gov.aims.bulkloader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BulkloaderShapeFileHandlingAllTests.class,
		TiffFileHandlingAllTests.class })
public class BulkloaderCompleteTestSuite {

}