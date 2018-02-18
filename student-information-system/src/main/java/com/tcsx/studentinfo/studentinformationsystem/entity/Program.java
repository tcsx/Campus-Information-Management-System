package com.tcsx.studentinfo.studentinformationsystem.entity;

import java.util.HashMap;

public class Program {
	private long id;
	private String name;
	private HashMap<Long, Course> courses;
	
	public Program() {}

	public Program(long id, String name, HashMap<Long, Course> courses) {
		super();
		this.id = id;
		this.name = name;
		this.courses = courses;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<Long, Course> getCourses() {
		return courses;
	}

	public void setCourses(HashMap<Long, Course> courses) {
		this.courses = courses;
	} 
	
}
