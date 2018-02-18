package com.tcsx.studentinfo.studentinformationsystem.entity;

import java.util.HashMap;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Student {
	private Long studentId;
	private String name;
	private String image;
	@JsonIgnore
	private HashMap<Long, Course> courses;
	private HashMap<Long, String> courseNames;
	@JsonIgnore
	private Program program;
	private String programName;
	
	public Student() {}
	
	public Student(Long studentId, String name, String image, HashMap<Long, Course> courses, Program program) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.image = image;
		this.courses = courses;
		this.program = program;
		setProgramName();
		setCourseNames();
	}
	
	public HashMap<Long, String> getCourseNames() {
		return courseNames;
	}

	public void setCourseNames(HashMap<Long, String> courseNames) {
		this.courseNames = courseNames;
	}
	
	public void setCourseNames() {
		if(courses != null) {
			courseNames = new HashMap<>();
			for (Entry<Long, Course> entry: courses.entrySet()) {
				courseNames.put(entry.getKey(), entry.getValue().getName());
			}
		}
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public void setProgramName() {
		if(program != null) this.programName = program.getName();
	}

	public Course getCourseById(long id) {
		return courses.get(id);
	}
	
	public Course deleteCourseById(long id) {
		courseNames.remove(id);
		return courses.remove(id);
	}

	public HashMap<Long, Course> getCourses() {
		return courses;
	}

	public void setCourses(HashMap<Long, Course> courses) {
		this.courses = courses;
		setCourseNames();
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
		setProgramName();
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
