package com.tcsx.studentinfo.studentinformationsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcsx.studentinfo.studentinformationsystem.dao.CourseRepository;
import com.tcsx.studentinfo.studentinformationsystem.dao.StudentRepository;
import com.tcsx.studentinfo.studentinformationsystem.exception.EntityNotFoundException;
import com.tcsx.studentinfo.studentinformationsystem.model.Course;
import com.tcsx.studentinfo.studentinformationsystem.model.Student;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CourseService courseService;

	public Iterable<Student> findAll() {
		return studentRepository.findAll();
	}

	public Student findById(String studentId) throws EntityNotFoundException {
		Optional<Student> student = studentRepository.findById(studentId);
		if (student.isPresent())
			return student.get();
		throw new EntityNotFoundException(Student.class, studentId);
	}

	public void deleteById(String studentId) throws EntityNotFoundException {
		Student student = findById(studentId);
		studentRepository.deleteById(studentId);
		for (String courseId : student.getCourses()) {
			Course course = courseService.findCourseById(courseId);
			course.deleteStudentById(studentId);
			courseRepository.save(course);
		}

	}

	public Student save(Student student) {
		for (String courseId : student.getCourses()) {
			Course course = courseService.findCourseById(courseId);
			course.addStudent(student);
			courseRepository.save(course);
		}
		return studentRepository.save(student);
	}
}
