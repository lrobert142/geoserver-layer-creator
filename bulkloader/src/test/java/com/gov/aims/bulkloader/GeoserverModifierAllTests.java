package com.gov.aims.bulkloader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GeoServerDataReaderTest.class, GeoserverDataUpdaterTest.class,
		GeoserverToCsvWriterTest.class })
public class GeoserverModifierAllTests {

}