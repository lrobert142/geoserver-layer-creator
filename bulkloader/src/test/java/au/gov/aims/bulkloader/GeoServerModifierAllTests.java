package au.gov.aims.bulkloader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GeoServerDataReaderTest.class, GeoServerDataUpdaterTest.class, GeoServerToCSVWriterTest.class })
public class GeoServerModifierAllTests {
}