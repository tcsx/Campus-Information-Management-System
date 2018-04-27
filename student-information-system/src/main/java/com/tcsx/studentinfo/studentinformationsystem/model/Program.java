package com.tcsx.studentinfo.studentinformationsystem.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;


@DynamoDBTable(tableName = "Program")
public class Program {
	@DynamoDBHashKey
	private String id;
	@DynamoDBAttribute
	private String name;
	@DynamoDBAttribute
	@DynamoDBTypeConvertedJson
	private Set<String> courses;
	
	public Program() {
		courses = new HashSet<>();
	}

	public Program(String id, String name, Set<String> courses) {
		super();
		this.id = id;
		this.name = name;
		this.courses = courses;
	}

	public boolean deleteCourseById(String id) {
		return courses.remove(id);
	}
	
	public boolean addCourse(Course course) {
		return courses.add(course.getId());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getCourses() {
		return courses;
	}

	public void setCourses(Set<String> courses) {
		this.courses = courses;
	} 
	
}
