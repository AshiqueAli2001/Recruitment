package com.interland.auth.exception;

public class RecordUpdateException extends Exception {

	public RecordUpdateException() {
		super();
	}

	public RecordUpdateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RecordUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public RecordUpdateException(String message) {
		super(message);
	}

	public RecordUpdateException(Throwable cause) {
		super(cause);
	}

}
