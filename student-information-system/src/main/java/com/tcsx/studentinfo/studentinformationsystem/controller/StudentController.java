package com.tcsx.studentinfo.studentinformationsystem.controller;

import java.net.URI;

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

import com.tcsx.studentinfo.studentinformationsystem.model.Student;
import com.tcsx.studentinfo.studentinformationsystem.service.StudentService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/students")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@GetMapping
	@ApiOperation(value = "Get All Students", notes = "Get all students in the database")
	public  Iterable<Student> getAllStudents() {
		return studentService.findAll();
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Get Specified Student", notes = "Get the student specified by id")
	public Student getStudent(@PathVariable String id) {
		return studentService.findById(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Add A Student")
	public ResponseEntity<Object> createStudent(@RequestBody Student student) {
		studentService.save(student);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(student.getStudentId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Update A Student")
	public Student updateStudent(@RequestBody Student student, @PathVariable String id) {
		studentService.deleteById(id);
		return studentService.save(student);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete A Student", notes = "Delete the student specified by id")
	public ResponseEntity<Object> deleteStudent(@PathVariable String id) {
		studentService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
