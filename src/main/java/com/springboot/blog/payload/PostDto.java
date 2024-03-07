package com.springboot.blog.payload;

import java.util.Set;

import com.springboot.blog.entity.Comment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostDto {

	private Long id;

	// post title should not be null / empty
	// post title should contain atleast 2 charaters
	@NotNull
	@Size(min = 2, message = "Post title should contain atleast 2 characters.")
	private String title;

	// post description should not be null / empty
	// post description should contain atleast 10 charaters
	@NotNull
	@Size(min = 10, message = "Post description should contain atleast 10 characters.")
	private String description;
	
	// post content should not be null / empty
	@NotNull
	private String content;
	
	
	private Set<Comment> comments;

}
