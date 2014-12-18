/**
@author Justin Osbaldiston
@version 1.0.0
@since 4/12/14
**/

package au.gov.aims.bulkloader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GeoServerManagerTest.class, UploadManagerCreateCSVTest.class,
		UploadManagerUploadFromCSVTest.class })
public class GeoServerUploadManagerAllTests {
}