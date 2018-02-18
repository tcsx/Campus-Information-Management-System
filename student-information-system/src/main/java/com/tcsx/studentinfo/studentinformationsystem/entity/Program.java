package com.tcsx.studentinfo.studentinformationsystem.entity;

import java.util.HashMap;

public class Program {
	private String id;
	private String name;
	private HashMap<String, Course> courses;
	
	public Program() {}

	public Program(String id, String name, HashMap<String, Course> courses) {
		super();
		this.id = id;
		this.name = name;
		this.courses = courses;
	}

	public Course getCourseById(String id) {
		return courses.get(id);
	}
	
	public Course deleteCourseById(String id) {
		for (Student student : courses.get(id).getStudents().values()) {
			student.deleteCourseById(id);
		}
		return courses.remove(id);
	}
	
	public Course addCourse(Course course) {
		return courses.put(course.getId(), course);
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

	public HashMap<String, Course> getCourses() {
		return courses;
	}

	public void setCourses(HashMap<String, Course> courses) {
		this.courses = courses;
	} 
	
}
