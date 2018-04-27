package com.tcsx.studentinfo.studentinformationsystem.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import com.tcsx.studentinfo.studentinformationsystem.model.Announcement;
import com.tcsx.studentinfo.studentinformationsystem.model.Board;
import com.tcsx.studentinfo.studentinformationsystem.model.Course;
import com.tcsx.studentinfo.studentinformationsystem.model.Lecture;
import com.tcsx.studentinfo.studentinformationsystem.model.Material;
import com.tcsx.studentinfo.studentinformationsystem.model.Note;
import com.tcsx.studentinfo.studentinformationsystem.model.Professor;
import com.tcsx.studentinfo.studentinformationsystem.model.Roster;
import com.tcsx.studentinfo.studentinformationsystem.model.Student;
import com.tcsx.studentinfo.studentinformationsystem.service.CourseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/courses")
public class CourseController {
	@Autowired
	CourseService courseService;
	
	@GetMapping
    @ApiOperation(value = "Get All Courses", notes = "Get all available courses")
    @ApiResponse(code = 404, message = "Not Found")
	public Iterable<Course> findAllCourses(){
		return courseService.findAllCourses();
	}
	
	@GetMapping("/{courseId}")
    @ApiOperation(value = "Get A Course By courseId",notes = "Get the course specified by courseId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "Course Id", required = true, dataType = "String", paramType = "path"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "No CourseId Provided"),
    })
	public Course getCourseById(@PathVariable String courseId) {
		return courseService.findCourseById(courseId);
	}
	
	@PutMapping("/{courseId}")
    @ApiOperation(value = "Update A Course", notes = "Update the specified course")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "No CourseId Provided"),
    })
	public Course updateCourse(@PathVariable String courseId, @RequestBody Course course) {
		return courseService.updateCourse(courseId, course);
	}

	@GetMapping("/{courseId}/students")
	public List<Student> getAllStudentsOfACourse(@PathVariable String courseId) {
		return courseService.getAllStudentsOfACourse(courseId);
	}
	
	@PostMapping("/{courseId}/students")
	@ApiOperation(value = "Add Student to A Course", notes = "Add student to the course specified by courseId")
	public ResponseEntity<Object> addStudentOfACourse(
			@PathVariable String courseId, @RequestBody Student student) {
		courseService.addStudentOfACourse(courseId, student);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(student.getStudentId()).toUri();
			return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{courseId}/students/{studentId}")
	@ApiOperation(value = "Add Student to A Course", notes = "Add student to the course specified by courseId")
	public void updateStudentInfoForACourse(
			@PathVariable String courseId, @PathVariable String studentId, @RequestBody Student student) {
		courseService.deleteStudentByIdOfACourse(courseId, studentId);
		courseService.addStudentOfACourse(courseId, student);
	}
	
	@DeleteMapping("/{courseId}/students/{studentId}")
	@ApiOperation(value = "Delete the specified student to a course")
	public ResponseEntity<Object> deleteStudentByIdOfACourse(
			@PathVariable String courseId, @PathVariable String studentId) {
		courseService.deleteStudentByIdOfACourse(courseId, studentId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{courseId}/students/{studentId}")
	public Student getStudentByIdOfACourse(
			@PathVariable String courseId, @PathVariable String studentId) {
		return courseService.getStudentByIdOfACourse(courseId, studentId);
	}
	
	@GetMapping("/{courseId}/lectures")
	@ApiOperation(value = "Get all lectures of a course")
	public List<Lecture> getLectures(@PathVariable String courseId){
		return courseService.getAllLecturesOfACourse(courseId);
	}
	
	@GetMapping("/{courseId}/lectures/{lectureId}")
	@ApiOperation(value = "Get the specified lectures of a course")
	public Lecture getLectureById(
			@PathVariable String courseId, @PathVariable String lectureId) {
		return courseService.getLectureByIdOfACourse(courseId, lectureId);
	}
	
	@PostMapping("/{courseId}/lectures")
	@ApiOperation(value = "Add a lecture to a course")
	public ResponseEntity<Object> addLecture(
			@PathVariable String courseId, @RequestBody Lecture lecture) {
		courseService.addLectureOfACourse(courseId, lecture);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(lecture.getId()).toUri();
			return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{courseId}/lectures/{lectureId}")
	@ApiOperation(value = "Update the lecture to the specified course")
	public void updateLecture(
			@PathVariable String courseId, @PathVariable String lectureId, @RequestBody Lecture lecture) {
		courseService.deleteLectureByIdOfACourse(courseId, lectureId);
		courseService.addLectureOfACourse(courseId, lecture);
	}
	
	@DeleteMapping("/{courseId}/lectures/{lectureId}")
	@ApiOperation(value = "Delete the lecture to the specified course")
	public ResponseEntity<Object> deleteLecture(
			@PathVariable String courseId, @PathVariable String lectureId) {
		courseService.deleteLectureByIdOfACourse(courseId, lectureId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{courseId}/roster")
	public Roster getRoster(
			@PathVariable String courseId) {
		return courseService.getRosterOfACourse(courseId);
	}
	
	@PutMapping("/{courseId}/roster")
	public void updateRoster(
			@PathVariable String courseId, @RequestBody Roster roster) {
		courseService.setRosterOfACourse(courseId, roster);
	}
	
	@DeleteMapping("/{courseId}/roster")
	public void deleteRoster(
			@PathVariable String courseId) {
		courseService.deleteRosterOfACourse(courseId);
	}
	
	@GetMapping("/{courseId}/board")
	public Board getBoard(
			@PathVariable String courseId) {
		return courseService.getBoardOfACourse(courseId);
	}
	
	@PutMapping("/{courseId}/board")
	public void updateBoard(
			@PathVariable String courseId, @RequestBody Board board) {
		courseService.setBoardOfACourse(courseId, board);
	}
	
	@DeleteMapping("/{courseId}/board")
	public void deleteBoard(
			@PathVariable String courseId) {
		courseService.deleteBoardOfACourse(courseId);
	}
	
	@GetMapping("/{courseId}/lectures/{lectureId}/materials")
	public HashMap<String, Material> getMaterials(
			@PathVariable String courseId, @PathVariable String lectureId) {
		return courseService.getMaterials(courseId, lectureId);
	}
	
	@GetMapping("/{courseId}/lectures/{lectureId}/materials/{materialId}")
	public Material getMaterialById(
			@PathVariable String courseId, 
			@PathVariable String lectureId,
			@PathVariable String materialId) {
		return courseService.getMaterialById(courseId, lectureId, materialId);
	}
	
	@PostMapping("/{courseId}/lectures/{lectureId}/materials")
	public ResponseEntity<Object> addMaterial(
			@PathVariable String courseId, 
			@PathVariable String lectureId,
			@RequestBody Material material) {
		courseService.addMaterial(courseId, lectureId, material);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(material.getId()).toUri();
			return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{courseId}/lectures/{lectureId}/materials/{materialId}")
	public void updateMaterial(
			@PathVariable String courseId,
			@PathVariable String lectureId, 
			@PathVariable String materialId,
			@RequestBody Material material) {
		courseService.deleteMaterial(courseId, lectureId, materialId);
		courseService.addMaterial(courseId, lectureId, material);
	}
	
	@DeleteMapping("/{courseId}/lectures/{lectureId}/materials/{materialId}")
	public ResponseEntity<Object> deleteMaterial(
			@PathVariable String courseId, 
			@PathVariable String lectureId, 
			@PathVariable String materialId) {
		courseService.getLectureByIdOfACourse(courseId, lectureId).getMaterials().remove(materialId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{courseId}/lectures/{lectureId}/notes")
	public HashMap<String, Note> getNotes(
			@PathVariable String courseId, @PathVariable String lectureId) {
		return courseService.getNotes(courseId, lectureId);
	}
	
	@GetMapping("/{courseId}/lectures/{lectureId}/notes/{noteId}")
	public Note getNoteById(
			@PathVariable String courseId, 
			@PathVariable String lectureId,
			@PathVariable String noteId) {
		return courseService.getNoteById(courseId, lectureId, noteId);
	}
	
	@PostMapping("/{courseId}/lectures/{lectureId}/notes")
	public ResponseEntity<Object> addNote(
			@PathVariable String courseId, 
			@PathVariable String lectureId,
			@RequestBody Note note) {
		courseService.addNote(courseId, lectureId, note);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(note.getId()).toUri();
			return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{courseId}/lectures/{lectureId}/notes/{noteId}")
	public void updateNote(
			@PathVariable String courseId,
			@PathVariable String lectureId, 
			@PathVariable String noteId,
			@RequestBody Note note) {
		courseService.deleteNote(courseId, lectureId, noteId);
		courseService.addNote(courseId, lectureId, note);
	}
	
	@DeleteMapping("/{courseId}/lectures/{lectureId}/notes/{noteId}")
	public ResponseEntity<Object> deleteNote(
			@PathVariable String courseId, 
			@PathVariable String lectureId, 
			@PathVariable String noteId) {
		courseService.getLectureByIdOfACourse(courseId, lectureId).getNotes().remove(noteId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{courseId}/professor")
	public Professor getProfessorOfACourse(
			@PathVariable String courseId) {
		return courseService.getProfessorOfACourse(courseId);
	}
	
	@PutMapping("/{courseId}/professor")
	public Professor updateProfessorOfACourse(
			@PathVariable String courseId, @RequestBody Professor professor) {
		courseService.deleteProfessorOfACourse(courseId);
		return courseService.setProfessorOfACourse(courseId, professor);
	}
	
	@DeleteMapping("/{courseId}/professor")
	public ResponseEntity<Object> deleteProfessorOfACourse(
			@PathVariable String courseId) {
		courseService.deleteProfessorOfACourse(courseId);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/{courseId}/professor")
	public Professor addProfessorOfACourse(
			@PathVariable String courseId, @RequestBody Professor professor) {
		return courseService.setProfessorOfACourse(courseId, professor);
	}
	
	@GetMapping("/{courseId}/announcements")
	public List<Announcement> getAnnouncementOfACourse(
			@PathVariable String courseId) {
		return courseService.getAnnoucementsOfACourse(courseId);
	}
	
	@GetMapping("/{courseId}/announcements/{announcementId}")
	public Announcement getAnnoucementByIdOfACourse(
			@PathVariable String courseId,
			@PathVariable String announcementId) {
		return courseService.getAnnoucementByIdOfACourse(courseId, announcementId);
	}
	
	@PostMapping("/{courseId}/announcements")
	public ResponseEntity<Object> addAnnouncementOfACourse(
			@PathVariable String courseId,
			@RequestBody Announcement announcement) {
		announcement = courseService.addAnnoucementOfACourse(courseId, announcement);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(announcement.getId()).toUri();
			return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{courseId}/announcements/{announcementId}")
	public Announcement updateAnnoucementByIdOfACourse(
			@PathVariable String courseId,
			@PathVariable String announcementId,
			@RequestBody Announcement announcement) {
		return courseService.updateAnnoucementByIdOfACourse(courseId, announcementId, announcement);
	}
	
	@DeleteMapping("/{courseId}/announcements/{announcementId}")
	public void deleteAnnoucementByIdOfACourse(
			@PathVariable String courseId,
			@PathVariable String announcementId) {
		courseService.deleteAnnoucementOfACourse(courseId, announcementId);
	}
}
