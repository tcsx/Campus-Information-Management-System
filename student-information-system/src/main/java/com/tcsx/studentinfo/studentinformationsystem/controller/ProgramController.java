package com.tcsx.studentinfo.studentinformationsystem.controller;

import java.net.URI;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tcsx.studentinfo.studentinformationsystem.entity.Board;
import com.tcsx.studentinfo.studentinformationsystem.entity.Course;
import com.tcsx.studentinfo.studentinformationsystem.entity.Lecture;
import com.tcsx.studentinfo.studentinformationsystem.entity.Material;
import com.tcsx.studentinfo.studentinformationsystem.entity.Note;
import com.tcsx.studentinfo.studentinformationsystem.entity.Program;
import com.tcsx.studentinfo.studentinformationsystem.entity.Roster;
import com.tcsx.studentinfo.studentinformationsystem.entity.Student;
import com.tcsx.studentinfo.studentinformationsystem.exception.EntityNotFoundException;
import com.tcsx.studentinfo.studentinformationsystem.service.ProgramService;

@RestController
@RequestMapping("/programs")
public class ProgramController {
	@Autowired
	private ProgramService programService;
	
	@GetMapping
	public  HashMap<String, Program> getAllPrograms() {
		return programService.findAll();
	}
	
	@GetMapping("/{id}")
	public Program getProgramById(@PathVariable String id) {
		Program program = programService.getOne(id);
		if (program == null) {
			throw new EntityNotFoundException(Program.class,id);
		}
		return program;
	}
	
	@PostMapping
	public ResponseEntity<Object> createStudent(@RequestBody Student student) {
		programService.getStudentService().save(student);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(student.getStudentId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	public void updateProgram(@RequestBody Program program, @PathVariable String id) {
		programService.deleteById(id);
		programService.save(program);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProgram(@PathVariable String id) {
		Program program = programService.deleteById(id);
		if (program == null) {
			throw new EntityNotFoundException(Program.class,id);
		}
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{programId}/courses")
	public HashMap<String, Course> getAllCourses(@PathVariable String programId) { 
		return programService.findAllCourses(programId);
	}
	
	@GetMapping("/{programId}/courses/{courseId}")
	public Course getCourseById(@PathVariable String programId, @PathVariable String courseId) {
		return programService.findCourseById(programId, courseId);
	}
	
	
	@PostMapping("/{programId}/courses")
	public ResponseEntity<Object> addCourse(@PathVariable String programId, @RequestBody Course course) {
		programService.addCourse(programId, course);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(course.getId()).toUri();
			return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{programId}/courses/{courseId}")
	public void updateCourse(@PathVariable String programId, @PathVariable String courseId, @RequestBody Course course) {
		programService.deleteCourseById(programId, courseId);
		programService.addCourse(programId, course);
	}
	
	@DeleteMapping("/{programId}/courses/{courseId}")
	public ResponseEntity<Object> deleteProgram(@PathVariable String programId, @PathVariable String courseId) {
		programService.deleteCourseById(programId, courseId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{programId}/courses/{courseId}/students")
	public HashMap<Long, Student> getAllStudentsOfACourse(@PathVariable String programId, @PathVariable String courseId) {
		return programService.getAllStudentsOfACourse(programId, courseId);
	}
	
	@PostMapping("/{programId}/courses/{courseId}/students")
	public ResponseEntity<Object> addStudentOfACourse(@PathVariable String programId, 
			@PathVariable String courseId, @RequestBody Student student) {
		programService.getStudentService().save(student);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(student.getStudentId()).toUri();
			return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{programId}/courses/{courseId}/students/{studentId}")
	public void updateStudentInfoForACourse(@PathVariable String programId, 
			@PathVariable String courseId, @PathVariable long studentId, @RequestBody Student student) {
		programService.getStudentService().deleteById(studentId);
		programService.getStudentService().save(student);
	}
	
	@DeleteMapping("/{programId}/courses/{courseId}/students/{studentId}")
	public ResponseEntity<Object> deleteStudentByIdOfACourse(@PathVariable String programId, 
			@PathVariable String courseId, @PathVariable long studentId) {
		programService.getStudentService().deleteById(studentId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{programId}/courses/{courseId}/students/{studentId}")
	public Student getStudentByIdOfACourse(@PathVariable String programId, 
			@PathVariable String courseId, @PathVariable long studentId) {
		return programService.getStudentByIdOfACourse(programId, courseId, studentId);
	}
	
	@GetMapping("/{programId}/courses/{courseId}/lectures")
	public HashMap<Long, Lecture> getLectures(@PathVariable String programId, @PathVariable String courseId){
		return programService.getAllLecturesOfACourse(programId, courseId);
	}
	
	@GetMapping("/{programId}/courses/{courseId}/lectures/{lectureId}")
	public Lecture getLectureById(@PathVariable String programId, 
			@PathVariable String courseId, @PathVariable long lectureId) {
		return programService.getLectureByIdOfACourse(programId, courseId, lectureId);
	}
	
	@PostMapping("/{programId}/courses/{courseId}/lectures")
	public ResponseEntity<Object> addLecture(@PathVariable String programId, 
			@PathVariable String courseId, @RequestBody Lecture lecture) {
		programService.addLectureOfACourse(programId, courseId, lecture);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(lecture.getId()).toUri();
			return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{programId}/courses/{courseId}/lectures/{lectureId}")
	public void updateLecture(@PathVariable String programId, 
			@PathVariable String courseId, @PathVariable long lectureId, @RequestBody Lecture lecture) {
		programService.deleteLectureByIdOfACourse(programId, courseId, lectureId);
		programService.addLectureOfACourse(programId, courseId, lecture);
	}
	
	@DeleteMapping("/{programId}/courses/{courseId}/lectures/{lectureId}")
	public ResponseEntity<Object> deleteLecture(@PathVariable String programId, 
			@PathVariable String courseId, @PathVariable long lectureId) {
		programService.deleteLectureByIdOfACourse(programId, courseId, lectureId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{programId}/courses/{courseId}/roster")
	public Roster getRoster(@PathVariable String programId, 
			@PathVariable String courseId) {
		return programService.findCourseById(programId, courseId).getRoster();
	}
	
	@PutMapping("/{programId}/courses/{courseId}/roster")
	public void updateRoster(@PathVariable String programId, 
			@PathVariable String courseId, @RequestBody Roster roster) {
		programService.findCourseById(programId, courseId).setRoster(roster);
	}
	
	@GetMapping("/{programId}/courses/{courseId}/board")
	public Board getBoard(@PathVariable String programId, 
			@PathVariable String courseId) {
		return programService.findCourseById(programId, courseId).getBoard();
	}
	
	@PutMapping("/{programId}/courses/{courseId}/board")
	public void updateBoard(@PathVariable String programId, 
			@PathVariable String courseId, @RequestBody Board board) {
		programService.findCourseById(programId, courseId).setBoard(board);;
	}
	
	@GetMapping("/{programId}/courses/{courseId}/lectures/{lectureId}/materials")
	public HashMap<Long, Material> getMaterials(@PathVariable String programId, 
			@PathVariable String courseId, @PathVariable long lectureId) {
		return programService.getMaterials(programId, courseId, lectureId);
	}
	
	@GetMapping("/{programId}/courses/{courseId}/lectures/{lectureId}/materials/{materialId}")
	public Material getMaterialById(@PathVariable String programId, 
			@PathVariable String courseId, 
			@PathVariable long lectureId,
			@PathVariable long materialId) {
		return programService.getMaterialById(programId, courseId, lectureId, materialId);
	}
	
	@PostMapping("/{programId}/courses/{courseId}/lectures/{lectureId}/materials")
	public ResponseEntity<Object> addMaterial(@PathVariable String programId, 
			@PathVariable String courseId, 
			@PathVariable long lectureId,
			@RequestBody Material material) {
		programService.addMaterial(programId, courseId, lectureId, material);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(material.getId()).toUri();
			return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{programId}/courses/{courseId}/lectures/{lectureId}/materials/{materialId}")
	public void updateMaterial(@PathVariable String programId, 
			@PathVariable String courseId,
			@PathVariable long lectureId, 
			@PathVariable long materialId,
			@RequestBody Material material) {
		programService.deleteMaterial(programId, courseId, lectureId, materialId);
		programService.addMaterial(programId, courseId, lectureId, material);
	}
	
	@DeleteMapping("/{programId}/courses/{courseId}/lectures/{lectureId}/materials/{materialId}")
	public ResponseEntity<Object> deleteMaterial(@PathVariable String programId, 
			@PathVariable String courseId, 
			@PathVariable long lectureId, 
			@PathVariable long materialId) {
		programService.getLectureByIdOfACourse(programId, courseId, lectureId).getMaterials().remove(materialId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{programId}/courses/{courseId}/lectures/{lectureId}/notes")
	public HashMap<Long, Note> getNotes(@PathVariable String programId, 
			@PathVariable String courseId, @PathVariable long lectureId) {
		return programService.getNotes(programId, courseId, lectureId);
	}
	
	@GetMapping("/{programId}/courses/{courseId}/lectures/{lectureId}/notes/{noteId}")
	public Note getNoteById(@PathVariable String programId, 
			@PathVariable String courseId, 
			@PathVariable long lectureId,
			@PathVariable long noteId) {
		return programService.getNoteById(programId, courseId, lectureId, noteId);
	}
	
	@PostMapping("/{programId}/courses/{courseId}/lectures/{lectureId}/notes")
	public ResponseEntity<Object> addNote(@PathVariable String programId, 
			@PathVariable String courseId, 
			@PathVariable long lectureId,
			@RequestBody Note note) {
		programService.addNote(programId, courseId, lectureId, note);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(note.getId()).toUri();
			return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{programId}/courses/{courseId}/lectures/{lectureId}/notes/{noteId}")
	public void updateNote(@PathVariable String programId, 
			@PathVariable String courseId,
			@PathVariable long lectureId, 
			@PathVariable long noteId,
			@RequestBody Note note) {
		programService.deleteNote(programId, courseId, lectureId, noteId);
		programService.addNote(programId, courseId, lectureId, note);
	}
	
	@DeleteMapping("/{programId}/courses/{courseId}/lectures/{lectureId}/notes/{noteId}")
	public ResponseEntity<Object> deleteNote(@PathVariable String programId, 
			@PathVariable String courseId, 
			@PathVariable long lectureId, 
			@PathVariable long noteId) {
		programService.getLectureByIdOfACourse(programId, courseId, lectureId).getNotes().remove(noteId);
		return ResponseEntity.noContent().build();
	}
}
