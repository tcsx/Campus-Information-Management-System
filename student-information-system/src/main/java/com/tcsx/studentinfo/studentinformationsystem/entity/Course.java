package com.tcsx.studentinfo.studentinformationsystem.entity;

import java.util.HashMap;

public class Course {
	private long id;
	private String name;
	private Board board;
	private Roster roster;
	private HashMap<Long, Student> students;
	private HashMap<Long, Lecture> lectures;
	
	public Course() {}
	
	public Course(Long id, String name, Board board, Roster roster, HashMap<Long, Student> students,
			HashMap<Long, Lecture> lectures) {
		super();
		this.id = id;
		this.name = name;
		this.board = board;
		this.roster = roster;
		this.students = students;
		this.lectures = lectures;
	}
	
	public Lecture getLectureById(long id) {
		return lectures.get(id);
	}
	
	public Lecture deleteLectureById(long id) {
		return lectures.remove(id);
	}
	
	public Lecture addLecture(Lecture lecture) {
		return lectures.put(lecture.getId(), lecture);
	}
	public Student getStudentById(long id) {
		return students.get(id);
	}
	
	public Student deleteStudentById(long id) {
		return students.remove(id);
	}
	
	public Student addStudent(Student student) {
		return students.put(student.getStudentId(), student);
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
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public Roster getRoster() {
		return roster;
	}
	public void setRoster(Roster roster) {
		this.roster = roster;
	}
	public HashMap<Long, Student> getStudents() {
		return students;
	}
	public void setStudents(HashMap<Long, Student> students) {
		this.students = students;
	}
	public HashMap<Long, Lecture> getLectures() {
		return lectures;
	}
	public void setLectures(HashMap<Long, Lecture> lectures) {
		this.lectures = lectures;
	}

}
