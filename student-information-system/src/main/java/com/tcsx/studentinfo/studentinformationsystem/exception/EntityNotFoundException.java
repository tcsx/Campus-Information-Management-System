package com.tcsx.studentinfo.studentinformationsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException() {
	}

	public <T> EntityNotFoundException(Class<T> type, String id) {
		super(type.getSimpleName() + " of id " + id + " not found.");
		printStackTrace();
	}

	public <T> EntityNotFoundException(Class<T> type, long id) {
		this(type, String.valueOf(id));
	}
}
