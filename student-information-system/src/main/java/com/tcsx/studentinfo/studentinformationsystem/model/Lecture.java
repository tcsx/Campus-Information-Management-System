package com.tcsx.studentinfo.studentinformationsystem.model;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;


@DynamoDBTable(tableName = "Lecture")
public class Lecture {
	@DynamoDBHashKey
	private String id;
	@DynamoDBAttribute
	private HashMap<String, Note> notes;
	@DynamoDBAttribute
	private HashMap<String, Material> materials;
	
	public Lecture() { }

	public Lecture(String id, HashMap<String, Note> notes, HashMap<String, Material> materials) {
		super();
		this.id = id;
		this.notes = notes;
		this.materials = materials;
	}

	
	public Note deleteNoteById(String id) {
		return notes.remove(id);
	}
	
	public Note addNote(Note note) {
		if(notes == null) notes = new HashMap<>();
		return notes.put(note.getId(), note);
	}

	public Material deleteMaterialById(String id) {
		return materials.remove(id);
	}
	
	public Material addMaterial(Material material) {
		if(materials == null) materials = new HashMap<>();
		return materials.put(material.getId(), material);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, Note> getNotes() {
		return notes;
	}

	public void setNotes(HashMap<String, Note> notes) {
		this.notes = notes;
	}

	public HashMap<String, Material> getMaterials() {
		return materials;
	}

	public void setMaterials(HashMap<String, Material> materials) {
		this.materials = materials;
	}
	
}