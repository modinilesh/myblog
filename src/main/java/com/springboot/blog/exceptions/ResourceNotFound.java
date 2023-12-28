package com.springboot.blog.exceptions;

public class ResourceNotFound extends RuntimeException{

	private String resourceName;
	private String fieldName;
	private long fieldValue;
	
	public ResourceNotFound(String resourceName, String fieldName, long fieldValue) {
		super(resourceName + "not found with " + fieldName + " : " + fieldValue);
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public String getResourceName() {
		return resourceName;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public long getFieldValue() {
		return fieldValue;
	}
	
	
}
