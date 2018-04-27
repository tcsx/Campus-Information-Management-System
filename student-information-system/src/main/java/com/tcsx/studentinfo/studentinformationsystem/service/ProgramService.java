package com.tcsx.studentinfo.studentinformationsystem.service;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.tcsx.studentinfo.studentinformationsystem.dao.AnnouncementRepository;
import com.tcsx.studentinfo.studentinformationsystem.dao.CourseRepository;
import com.tcsx.studentinfo.studentinformationsystem.dao.LectureRepository;
import com.tcsx.studentinfo.studentinformationsystem.dao.ProfessorRepository;
import com.tcsx.studentinfo.studentinformationsystem.dao.ProgramRepository;
import com.tcsx.studentinfo.studentinformationsystem.dao.StudentRepository;
import com.tcsx.studentinfo.studentinformationsystem.exception.EntityNotFoundException;
import com.tcsx.studentinfo.studentinformationsystem.model.Course;
import com.tcsx.studentinfo.studentinformationsystem.model.Professor;
import com.tcsx.studentinfo.studentinformationsystem.model.Program;
import com.tcsx.studentinfo.studentinformationsystem.model.Student;

@Service
public class ProgramService {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private ProgramRepository programRepository;
	@Autowired
	private AnnouncementRepository announcementRepository;
	@Autowired
	private LectureRepository lectureRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private AmazonSNSClient snsClient;  
	@Autowired
	private StudentService studentService;
	@Autowired
	private CourseService courseService;
	

	public Iterable<Program> findAll() {
		return programRepository.findAll();
	}

	public Program findByProgramId(String id) throws EntityNotFoundException {
		Optional<Program> program = programRepository.findById(id);
		if (!program.isPresent()) {
			throw new EntityNotFoundException(Program.class, id);
		}
		return program.get();
	}

	public void deleteById(String id) {
		programRepository.deleteById(id);
	}

	public Program save(Program program) {
		return programRepository.save(program);
	}

	public List<Course> findAllCourses(String programId) {
		List<Course> courses = new LinkedList<>();
		Set<String> courseIds = findByProgramId(programId).getCourses();
		for (String courseId : courseIds) {
			courses.add(courseRepository.findById(courseId).get());
		}
		return courses;
	}


	public Course addCourse(String programId, Course course) {
		Program program = findByProgramId(programId);
		program.addCourse(course);
		programRepository.save(program);
		for (String studentId : course.getStudents().keySet()) {
			Student student = studentService.findById(studentId);
			student.addCourse(course.getId());
		}
		try {
			Professor professor = courseService.getProfessorOfACourse(course);
			professor.addCourse(course);
			professorRepository.save(professor);
		} catch (EntityNotFoundException e) {
		}
		CreateTopicResult createTopicResult = snsClient.createTopic(course.getId());
		String topicArn = createTopicResult.getTopicArn();
		course.setTopicArn(topicArn);
		return courseRepository.save(course);
	}

	public Course deleteCourseById(String programId, String courseId) {
		Program program = findByProgramId(programId);
		program.deleteCourseById(courseId);
		programRepository.save(program);
		Course course = courseService.findCourseById(courseId);
		HashMap<String, String> students = course.getStudents();
		for (String studentId : students.keySet()) {
			Student student =studentService.findById(studentId);
			student.deleteCourseById(courseId);
			studentRepository.save(student);
		}
		Set<String> lectures = course.getLectures();
		for (String lectureId : lectures) {
			lectureRepository.deleteById(lectureId);
		}
		
		Set<String> announcements = course.getAnnouncements();
		for (String announcementId : announcements) {
			announcementRepository.deleteById(announcementId);
		}
		
		try {
			Professor professor = courseService.getProfessorOfACourse(course); 
			professor.deleteCourseById(courseId);
			professorRepository.save(professor);
		} catch (EntityNotFoundException e) { }
		
		snsClient.deleteTopic(course.getTopicArn());
		courseRepository.deleteById(courseId);
		return course;
	}
	
	

}
