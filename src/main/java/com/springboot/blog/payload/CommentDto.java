package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDto {

	private Long id;
	
	//name should not be null
	@NotEmpty(message = "Name should not be null or empty.")
	private String name;
	
	//email should not be null/empty
	//email should be in vadid format
	@NotEmpty(message = "Email should not be null or empty.")
	@Email(message = "Email should be in correct format.")
	private String email;
	
	//body should not be null/empty
	//body should contain atleast 10 characters
	@NotEmpty(message = "Body should not be null or empty.")
	@Size(min = 10, message = "Body should contain atleast 10 characters.")
	private String body;
	
}
