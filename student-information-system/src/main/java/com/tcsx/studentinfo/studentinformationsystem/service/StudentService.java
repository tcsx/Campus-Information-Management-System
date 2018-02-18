package com.tcsx.studentinfo.studentinformationsystem.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.tcsx.studentinfo.studentinformationsystem.entity.Board;
import com.tcsx.studentinfo.studentinformationsystem.entity.Course;
import com.tcsx.studentinfo.studentinformationsystem.entity.Lecture;
import com.tcsx.studentinfo.studentinformationsystem.entity.Material;
import com.tcsx.studentinfo.studentinformationsystem.entity.Note;
import com.tcsx.studentinfo.studentinformationsystem.entity.Roster;
import com.tcsx.studentinfo.studentinformationsystem.entity.Student;
import com.tcsx.studentinfo.studentinformationsystem.exception.EntityNotFoundException;

@Service
public class StudentService {
	private HashMap<Long, Student> students = new HashMap<>();
	
	//sample data for tests
	public StudentService() {
		HashMap<String, Course> courses = new HashMap<>();
		Student student = new Student(1L, "Lee", "/hehe", null, null);
		students.put(1L, student);
		HashMap<Long, Note> notes = new HashMap<>();
		HashMap<Long, Material> materials = new HashMap<>();
		notes.put(2L, new Note(2L, "elb"));
		materials.put(4L, new Material(4L, "aws"));
		Lecture lecture = new Lecture(1L, notes, materials);
		HashMap<Long, Lecture> lectures = new HashMap<>();
		lectures.put(1L, lecture);
		Course course = new Course("csye6225", "cloud", new Board("board"), new Roster("roster"), students, lectures);
		courses.put("csye6225", course);
		student.setCourses(courses);
	}
	
	public  HashMap<Long, Student> findAll() {
		return students;
	}
	
	public Student getOne(long id) {
		Student student = students.get(id);
		if (student == null) {
			throw new EntityNotFoundException(Student.class,id);
		}
		return student;
	}
	
	public Student deleteById(long id) {
		Student student = students.remove(id);
		if (student == null) {
			throw new EntityNotFoundException(Student.class,id);
		}
		return student;
	}
	
	public Student save(Student student) {
		
		return students.put(student.getStudentId(), student);
	}
	
	public HashMap<Long, Student> getStudents(){
		return students;
	}

}
