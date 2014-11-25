package gov.au.aims.uploader.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestEmployee {

	@Test
	public void testConstructorNoValues() {
		Employee e = new Employee();
		assertTrue(e != null);
	}
	
	@Test
	public void testConstructorWithValues() {
		Employee e = new Employee("sample.txt", "5", "John Smith", "Test Case", "123AUD");
		assertTrue(e != null);
	}
}
