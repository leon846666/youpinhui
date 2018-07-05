package com.youpinhui.entity;

import java.io.Serializable;

/**
 * @author Leon
 * 
 * This class is created for the result of CRUD operations.
 * 
 *  boolean success is the result of the operation.
 *  message is the feedback message;
 */
public class Result implements Serializable{

	private boolean success;
	private String message;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "Result [success=" + success + ", message=" + message + "]";
	}
	
	public Result(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	public Result() {
		super();
	}
	
	
	
}
