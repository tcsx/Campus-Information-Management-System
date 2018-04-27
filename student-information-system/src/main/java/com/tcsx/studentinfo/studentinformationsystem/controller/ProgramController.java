package com.tcsx.studentinfo.studentinformationsystem.controller;

import java.net.URI;
import java.util.List;

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

import com.tcsx.studentinfo.studentinformationsystem.model.Course;
import com.tcsx.studentinfo.studentinformationsystem.model.Program;
import com.tcsx.studentinfo.studentinformationsystem.service.ProgramService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/programs")
public class ProgramController {

	@Autowired
	ProgramService programService;
	
	@GetMapping
	@ApiOperation(value = "Get All Programs")
	public  Iterable<Program> getAllPrograms() {
		return programService.findAll();
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Get One Program", notes = "Get the program specified by Id")
	public Program getProgramById(@PathVariable String id) {
		return programService.findByProgramId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Add A New Program")
	public ResponseEntity<Object> createProgram(@RequestBody Program program){
		programService.save(program);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(program.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Update A Program", notes = "Update the program specified by Id. You can not change program id or course lists in this api")
	public void updateProgram(@RequestBody Program program, @PathVariable String id) {
		programService.save(program);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete One Program", notes = "Delete the program specified by Id")
	public ResponseEntity<Object> deleteProgram(@PathVariable String id) {
		programService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{programId}/courses")
	@ApiOperation(value = "Get All Courses Of The Specified Program")
	public List<Course> getAllCoursesOfAProgram(@PathVariable String programId) { 
		return programService.findAllCourses(programId);
	}
	
	
	@PostMapping("/{programId}/courses")
	@ApiOperation(value = "Add A Courses To The Specified Program")
	public ResponseEntity<Object> addCourse(@PathVariable String programId, @RequestBody Course course) {
		programService.addCourse(programId, course);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(course.getId()).toUri();
			return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping("/{programId}/courses/{courseId}")
	@ApiOperation(value = "Delete A Courses Of The Specified Program")
	public ResponseEntity<Object> deleteCourse(@PathVariable String programId, @PathVariable String courseId) {
		programService.deleteCourseById(programId, courseId);
		return ResponseEntity.noContent().build();
	}
	
}
