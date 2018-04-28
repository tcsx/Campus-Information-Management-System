package com.tcsx.studentinfo.studentinformationsystem.service;

import java.io.Console;

public class RegInfo {
	private String input;
	private String stateMachineArn;
	public RegInfo() {
		
	}
	
	public RegInfo(String studentId, String courseId, String email, String name, String stateMachineArn) {
		this.input = String.format("{\"studentEmail\": \"%s\",\"studentId\": \"%s\","
				+ "\"studentName\": \"%s\",\"courseId\": \"%s\"}",
				email, studentId, name, courseId);
		this.stateMachineArn = stateMachineArn;
	}
	
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getStateMachineArn() {
		return stateMachineArn;
	}
	public void setStateMachineArn(String stateMachineArn) {
		this.stateMachineArn = stateMachineArn;
	}
	
	
}
