package com.tcsx.studentinfo.studentinformationsystem.entity;

import java.util.HashMap;
import java.util.Map.Entry;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Student {
	private Long studentId;
	private String name;
	private String image;
	@JsonIgnore
	private HashMap<String, Course> courses;
	private HashMap<String, String> courseNames;
	@JsonIgnore
	private Program program;
	private String programId;
	
	public Student() {}
	
	public Student(Long studentId, String name, String image, HashMap<String, Course> courses, Program program) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.image = image;
		this.courses = courses;
		this.program = program;
		setProgramId();
		setCourseNames();
	}
	
	
	public HashMap<String, String> getCourseNames() {
		return courseNames;
	}

	public void setCourseNames(HashMap<String, String> courseNames) {
		this.courseNames = courseNames;
	}
	
	public void setCourseNames() {
		if(courses != null) {
			courseNames = new HashMap<>();
			for (Entry<String, Course> entry: courses.entrySet()) {
				courseNames.put(entry.getKey(), entry.getValue().getName());
			}
		}
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramName(String programId) {
		this.programId = programId;
	}

	public void setProgramId() {
		if(program != null) this.programId = program.getName();
	}

	public Course getCourseById(String id) {
		return courses.get(id);
	}
	
	public Course deleteCourseById(String id) {
		courseNames.remove(id);
		Course course = courses.remove(id);
		course.getStudents().remove(studentId);
		return course;
	}
	
	public Course addCourse(Course course) {
		course.getStudents().put(studentId, this);
		Course newCourse = courses.put(course.getId(), course);
		courseNames.put(course.getId(), course.getName());
		return newCourse;
	}

	public HashMap<String, Course> getCourses() {
		return courses;
	}

	public void setCourses(HashMap<String, Course> courses) {
		this.courses = courses;
		setCourseNames();
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
		setProgramId();
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
