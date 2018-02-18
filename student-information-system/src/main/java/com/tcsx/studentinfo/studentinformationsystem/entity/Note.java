package com.tcsx.studentinfo.studentinformationsystem.entity;

public class Note {
	private Long id;
	private Long name;
	
	public Note() {}

	public Note(Long id, Long name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getName() {
		return name;
	}

	public void setName(Long name) {
		this.name = name;
	}
	
	
}
