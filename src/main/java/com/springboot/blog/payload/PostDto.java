package com.springboot.blog.payload;


import java.util.Set;

import com.springboot.blog.entity.Comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostDto {
	
	private Long id;
	private String title;
	private String description;
	private String content;
	private Set<Comment> comments;

}
