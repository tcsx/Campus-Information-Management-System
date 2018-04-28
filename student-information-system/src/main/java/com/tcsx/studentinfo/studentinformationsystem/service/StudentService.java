package com.tcsx.studentinfo.studentinformationsystem.service;

import java.util.Optional;

import javax.enterprise.inject.New;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.util.json.Jackson;
import com.tcsx.studentinfo.studentinformationsystem.dao.CourseRepository;
import com.tcsx.studentinfo.studentinformationsystem.dao.StudentRepository;
import com.tcsx.studentinfo.studentinformationsystem.exception.EntityNotFoundException;
import com.tcsx.studentinfo.studentinformationsystem.model.Course;
import com.tcsx.studentinfo.studentinformationsystem.model.Student;

import net.bytebuddy.agent.builder.AgentBuilder.CircularityLock.Inactive;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CourseService courseService;
	@Autowired
	private RestTemplate restTemplate;

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
//		for (String courseId : student.getCourses()) {
//			Course course = courseService.findCourseById(courseId);
//			course.addStudent(student);
//			courseRepository.save(course);
//		}
		String courseId = "";
		for (String course : student.getCourses()) {
			courseId = course;
			break;
		}
		System.out.println(student.getCourses());
		restTemplate.postForEntity("https://m08dx8mceb.execute-api.us-west-2.amazonaws.com/prod/registration", 
				new RegInfo(student.getStudentId(), 
						courseId, 
						student.getEmail(), 
						student.getName(), 
						"arn:aws:states:us-west-2:479797748665:stateMachine:registration"), 
				String.class);
		
		return studentRepository.save(student);
	}
	
	
	public Student charge(String studentId) {
		Student student = findById(studentId);
		student.setTuition(student.getTuition() + 1500);
		return studentRepository.save(student);
	}
	
	public Student inactivate(String studentId) {
		Student student = findById(studentId);
		student.setActive(false);;
		return studentRepository.save(student);
	}
}
