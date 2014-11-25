package gov.au.aims.uploader.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {

	private final StringProperty fileName;
	private final StringProperty id;
	private final StringProperty name;
	private final StringProperty role;
	private final StringProperty salary;

	public Employee() {
		this(null, null, null, null, null);
	}
	
	public Employee(String fileName, String id, String name, String role, String salary) {
		this.fileName= new SimpleStringProperty(fileName);
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.role = new SimpleStringProperty(role);
		this.salary = new SimpleStringProperty(salary);
	}

	public String getFileName() {
		return fileName.get();
	}

	public void setFileName(String fileName) {
		this.fileName.set(fileName);
	}

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getRole() {
		return role.get();
	}

	public void setRole(String role) {
		this.role.set(role);
	}

	public String getSalary() {
		return salary.get();
	}

	public void setSalary(String salary) {
		this.salary.set(salary);
	}

	@Override
	public String toString() {
		return "Filename=" + fileName + "ID=" + id + ",Name=" + name + ",Role=" + role + ",Salary="
				+ salary + "\n";
	}
}