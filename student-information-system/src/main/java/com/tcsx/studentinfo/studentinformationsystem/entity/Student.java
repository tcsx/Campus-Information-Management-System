package com.tcsx.studentinfo.studentinformationsystem.entity;

public class Student {
	private Long studentId;
	private String name;
	private String image;
	private String course;
	private String program;
	
	public Student() {}

	public Student(Long studentId, String name, String image, String course, String program) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.image = image;
		this.course = course;
		this.program = program;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
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
