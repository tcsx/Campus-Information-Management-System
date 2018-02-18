package com.tcsx.studentinfo.studentinformationsystem.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcsx.studentinfo.studentinformationsystem.entity.Course;
import com.tcsx.studentinfo.studentinformationsystem.entity.Lecture;
import com.tcsx.studentinfo.studentinformationsystem.entity.Material;
import com.tcsx.studentinfo.studentinformationsystem.entity.Note;
import com.tcsx.studentinfo.studentinformationsystem.entity.Program;
import com.tcsx.studentinfo.studentinformationsystem.entity.Student;
import com.tcsx.studentinfo.studentinformationsystem.exception.EntityNotFoundException;

@Service
public class ProgramService {

	private StudentService studentService;
	private HashMap<String, Program> programs = new HashMap<>();

	public ProgramService() {
	}

	//add sample data for tests
	@Autowired
	public ProgramService(StudentService studentService) {
		this.setStudentService(studentService);
		Program program = new Program("msis", "msis", null);
		HashMap<Long, Student> students = studentService.getStudents();
		Student student = students.get(1L);
		program.setCourses(student.getCourses());
		;
		student.setProgram(program);
		programs.put("msis", program);
	}

	public HashMap<String, Program> findAll() {
		return programs;
	}

	public Program getOne(String id) {
		Program program = programs.get(id);
		if (program == null) {
			throw new EntityNotFoundException(Program.class, id);
		}
		return program;
	}

	public Program deleteById(String id) {
		Program program = programs.remove(id);
		if (program == null) {
			throw new EntityNotFoundException(Program.class, id);
		}
		return program;
	}

	public Program save(Program program) {
		return programs.put(program.getId(), program);
	}

	public HashMap<String, Course> findAllCourses(String programId) {
		return getOne(programId).getCourses();
	}

	public Course findCourseById(String programId, String courseId) {
		Course course = findAllCourses(programId).get(courseId);
		if (course == null) {
			throw new EntityNotFoundException(Course.class, courseId);
		}
		return course;
	}

	public Course addCourse(String programId, Course course) {
		return getOne(programId).addCourse(course);
	}

	public Course deleteCourseById(String programId, String courseId) {
		Course course = getOne(programId).deleteCourseById(courseId);
		;
		if (course == null) {
			throw new EntityNotFoundException(Course.class, courseId);
		}
		return course;
	}

	public HashMap<Long, Student> getAllStudentsOfACourse(String programId, String courseId) {
		return findCourseById(programId, courseId).getStudents();
	}

	public Student getStudentByIdOfACourse(String programId, String courseId, long studentId) {
		Student student = getAllStudentsOfACourse(programId, courseId).get(studentId);
		if (student == null) {
			throw new EntityNotFoundException(Student.class, studentId);
		}
		return student;
	}

	public Student addStudentOfACourse(String programId, String courseId, Student student) {
		return findCourseById(programId, courseId).addStudent(student);
	}

	public Student deleteStudentByIdOfACourse(String programId, String courseId, long studentId) {
		Student student = findCourseById(programId, courseId).deleteStudentById(studentId);
		if (student == null) {
			throw new EntityNotFoundException(Student.class, studentId);
		}
		return student;
	}

	public HashMap<Long, Lecture> getAllLecturesOfACourse(String programId, String courseId) {
		return findCourseById(programId, courseId).getLectures();
	}

	public Lecture getLectureByIdOfACourse(String programId, String courseId, long lectureId) {
		Lecture lecture = findCourseById(programId, courseId).getLectureById(lectureId);
		if (lecture == null) {
			throw new EntityNotFoundException(Lecture.class, lectureId);
		}
		return lecture;
	}

	public Lecture addLectureOfACourse(String programId, String courseId, Lecture lecture) {
		return findCourseById(programId, courseId).addLecture(lecture);
	}

	public Lecture deleteLectureByIdOfACourse(String programId, String courseId, long lectureId) {
		Lecture lecture = findCourseById(programId, courseId).deleteLectureById(lectureId);
		if (lecture == null) {
			throw new EntityNotFoundException(Lecture.class, lectureId);
		}
		return lecture;
	}

	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public HashMap<Long, Material> getMaterials(String programId, String courseId, long lectureId) {
		return getLectureByIdOfACourse(programId, courseId, lectureId).getMaterials();
	}
	
	
	public Material addMaterial(String programId, String courseId, long lectureId, Material material) {
		return getMaterials(programId, courseId, lectureId).put(material.getId(), material);
	}
	
	public Material deleteMaterial(String programId, String courseId, 
			long lectureId, long materialId) {
		return getMaterials(programId, courseId, lectureId).remove(materialId);
	}
	
	public Material getMaterialById(String programId, String courseId, 
			long lectureId, long materialId) {
		return getMaterials(programId, courseId, lectureId).get(materialId);
	}
	
	public HashMap<Long, Note> getNotes(String programId, String courseId, long lectureId) {
		return getLectureByIdOfACourse(programId, courseId, lectureId).getNotes();
	}
	
	
	public Note addNote(String programId, String courseId, long lectureId, Note note) {
		return getNotes(programId, courseId, lectureId).put(note.getId(), note);
	}
	
	public Note deleteNote(String programId, String courseId, 
			long lectureId, long noteId) {
		return getNotes(programId, courseId, lectureId).remove(noteId);
	}
	
	public Note getNoteById(String programId, String courseId, 
			long lectureId, long noteId) {
		return getNotes(programId, courseId, lectureId).get(noteId);
	}

}
