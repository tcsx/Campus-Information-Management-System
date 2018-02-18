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

import com.tcsx.studentinfo.studentinformationsystem.entity.Student;
import com.tcsx.studentinfo.studentinformationsystem.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@GetMapping
	public  HashMap<Long, Student> getAllStudents() {
		return studentService.findAll();
	}
	
	@GetMapping("/{id}")
	public Student getStudent(@PathVariable long id) {
		return studentService.getOne(id);
	}
	
	@PostMapping
	public ResponseEntity<Object> createStudent(@RequestBody Student student) {
		studentService.save(student);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(student.getStudentId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	public void updateStudent(@RequestBody Student student, @PathVariable long id) {
		studentService.deleteById(id);
		studentService.save(student);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable long id) {
		studentService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
