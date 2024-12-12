package com.interland.admin.exception;

public class RecordNotFoundException extends Exception {

	public RecordNotFoundException() {
		super();
	}

	public RecordNotFoundException(String message, Throwable cause, boolean EnableSuppression,
			boolean writableStackTree) {
		super(message, cause, EnableSuppression, writableStackTree);
	}

	public RecordNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public RecordNotFoundException(String message) {
		super(message);
	}

	public RecordNotFoundException(Throwable cause) {
		super(cause);
	}

}
