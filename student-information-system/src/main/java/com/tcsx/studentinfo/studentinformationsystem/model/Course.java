package com.tcsx.studentinfo.studentinformationsystem.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;
import com.fasterxml.jackson.annotation.JsonIgnore;

@DynamoDBTable(tableName = "Course")
public class Course {
	@DynamoDBHashKey
	private String id;
	@DynamoDBAttribute
	private String name;
	@DynamoDBAttribute
	private Board board;
	@DynamoDBAttribute
	private Roster roster;
	@DynamoDBAttribute
	@DynamoDBTypeConvertedJson
	private Set<String> lectures;
	@DynamoDBAttribute
	private String professor;
	@DynamoDBAttribute
	@DynamoDBTypeConvertedJson
	private Set<String> announcements;
	@DynamoDBAttribute
	private String topicArn;

	public Course() {
		lectures = new HashSet<>();
		announcements = new HashSet<>();
		roster = new Roster();
	}


	public Course(String id, String name, Board board, Roster roster, Set<String> lectures, String professor,
			Set<String> announcements) {
		super();
		this.id = id;
		this.name = name;
		this.board = board;
		this.roster = roster;
		this.lectures = lectures;
		this.professor = professor;
		this.announcements = announcements;
	}

	public String getTopicArn() {
		return topicArn;
	}


	public void setTopicArn(String topicArn) {
		this.topicArn = topicArn;
	}


	public boolean deleteLectureById(String id) {
		return lectures.remove(id);
	}

	public void addLecture(Lecture lecture) {
		lectures.add(lecture.getId());
	}

	public String deleteStudentById(String studentId) {
		return roster.remove(studentId);
	}
	
	@DynamoDBIgnore
	@JsonIgnore
	public HashMap<String, String> getStudents(){
		return getRoster().getStudents();
	}
	
	public boolean addAnnouncement(Announcement announcement) {
		return announcements.add(announcement.getId());
	}
	
	public boolean deleteAnnouncementById(String announcementId) {
		return announcements.remove(announcementId);
	}

	public void addStudent(Student student) {
		roster.add(student);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Roster getRoster() {
		return roster;
	}

	public void setRoster(Roster roster) {
		this.roster = roster;
	}

	public Set<String> getLectures() {
		return lectures;
	}

	public void setLectures(Set<String> lectures) {
		this.lectures = lectures;
	}


	public String getProfessor() {
		return professor;
	}


	public void setProfessor(String professor) {
		this.professor = professor;
	}


	public Set<String> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(Set<String> announcements) {
		this.announcements = announcements;
	}
	
}
