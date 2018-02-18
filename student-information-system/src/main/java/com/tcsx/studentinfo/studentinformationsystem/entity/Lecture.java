package com.tcsx.studentinfo.studentinformationsystem.entity;

import java.util.HashMap;

public class Lecture {
	private long id;
	private HashMap<Long, Note> notes;
	private HashMap<Long, Material> materials;
	
	public Lecture() {}

	public Lecture(Long id, HashMap<Long, Note> notes, HashMap<Long, Material> materials) {
		super();
		this.id = id;
		this.notes = notes;
		this.materials = materials;
	}

	public Note getNoteById(long id) {
		return notes.get(id);
	}
	
	public Note deleteNoteById(long id) {
		return notes.remove(id);
	}
	
	public Note addNote(Note note) {
		return notes.put(note.getId(), note);
	}

	public Material getMaterialById(long id) {
		return materials.get(id);
	}
	
	public Material deleteMaterialById(long id) {
		return materials.remove(id);
	}
	
	public Material addMaterial(Material material) {
		return materials.put(material.getId(), material);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
