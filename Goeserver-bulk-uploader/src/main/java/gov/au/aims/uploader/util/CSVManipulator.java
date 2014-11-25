package gov.au.aims.uploader.util;

import gov.au.aims.uploader.model.Employee;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class CSVManipulator {
	private List<Employee> emps = new ArrayList<Employee>();

	public CSVManipulator() {
	}

	public List<Employee> getEmps() {
		return emps;
	}

	public void setEmps(List<Employee> emps) {
		this.emps = emps;
	}

	public void parseCSVToBeanList(String file) throws IOException {
		HeaderColumnNameTranslateMappingStrategy<Employee> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<Employee>();
		beanStrategy.setType(Employee.class);

		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("ID", "id");
		columnMapping.put("Name", "name");
		columnMapping.put("Role", "role");
		columnMapping.put("Salary", "salary");

		beanStrategy.setColumnMapping(columnMapping);

		CsvToBean<Employee> csvToBean = new CsvToBean<Employee>();
		CSVReader reader = new CSVReader(new FileReader(file));
		List<Employee> employees = csvToBean.parse(beanStrategy, reader);
		for(Employee employee : employees) {
			employee.setFileName(file);
		}
		emps.addAll(employees);
	}

	public void writeCSVData(List<Employee> emps, File file) throws IOException {
		FileWriter fWriter = new FileWriter(file);
		CSVWriter csvWriter = new CSVWriter(fWriter, ',');
		List<String[]> data = toStringArray(emps);
		csvWriter.writeAll(data);
		csvWriter.close();
	}

	private static List<String[]> toStringArray(List<Employee> emps) {
		List<String[]> records = new ArrayList<String[]>();
		// add header record
		records.add(new String[] { "File Path", "ID", "Name", "Role", "Salary" });
		Iterator<Employee> it = emps.iterator();
		while (it.hasNext()) {
			Employee emp = it.next();
			records.add(new String[] { emp.getFileName(), emp.getId(), emp.getName(),
					emp.getRole(), emp.getSalary() });
		}
		return records;
	}
}
