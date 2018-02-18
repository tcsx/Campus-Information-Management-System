package com.tcsx.studentinfo.studentinformationsystem.entity;

import java.util.HashMap;

public class Lecture {
	private HashMap<Long, Note> notes;
	private HashMap<Long, Material> materials;
	
	public Lecture() {}

	public Lecture(HashMap<Long, Note> notes, HashMap<Long, Material> materials) {
		super();
		this.notes = notes;
		this.materials = materials;
	}

	public HashMap<Long, Note> getNotes() {
		return notes;
	}

	public void setNotes(HashMap<Long, Note> notes) {
		this.notes = notes;
	}

	public HashMap<Long, Material> getMaterials() {
		return materials;
	}

	public void setMaterials(HashMap<Long, Material> materials) {
		this.materials = materials;
	}
	
	
}
