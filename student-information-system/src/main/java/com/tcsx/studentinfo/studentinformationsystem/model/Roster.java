package com.tcsx.studentinfo.studentinformationsystem.model;

import java.util.HashMap;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;

@DynamoDBDocument
public class Roster {
	@DynamoDBTypeConvertedJson
	private HashMap<String, String> students;
	
	public Roster() {
		students = new HashMap<>();
	}

	public Roster(HashMap<String, String> students) {
		super();
		this.students = students;
	}

	public HashMap<String, String> getStudents() {
		return students;
	}

	public void setStudents(HashMap<String, String> students) {
		this.students = students;
	}

	public void add(Student student) {
		students.put(student.getStudentId(), student.getName());
	}
	
	public String remove(String studentId) {
		return students.remove(studentId);
	}
	

	
}
