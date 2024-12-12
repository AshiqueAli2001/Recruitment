package com.interland.auth.dto;

import java.util.List;

import org.json.simple.JSONObject;


public class ServiceResponse {

	private String message;
	private String code;
	private List<JSONObject> details;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<JSONObject> getDetails() {
		return details;
	}
	public void setDetails(List<JSONObject> details) {
		this.details = details;
	}
	public ServiceResponse(String message, String code, List<JSONObject> details) {
		super();
		this.message = message;
		this.code = code;
		this.details = details;
	}
	
	
}
