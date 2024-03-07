package com.springboot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

	@Autowired
	private PostService postService;
	
	private static final Logger logger = LoggerFactory.getLogger(PostRestController.class);

	// Creating Post
	@PostMapping
	public ResponseEntity<PostDto> createPosts(@Valid @RequestBody PostDto postDto) {
		logger.info("Creating a new Post.");
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	// Get all Posts -- making changes for Pagination and Sorting
	@GetMapping
	public PostResponse getPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
			) 
	{
		logger.info("Getting all Posts");
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}

	// Get post by id
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
		logger.info("Getting Post with PostID : " + id);
		return ResponseEntity.ok(postService.getPostById(id));
	}

	// Update post
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
		PostDto response = postService.updatePost(postDto, id);
		logger.info("Updating Post with PostID : " + id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Delete post
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
		logger.info("Deleting Post with PostID : " + id);
		postService.deletePost(id);
		return new ResponseEntity<>("Post entity is deleted successfully", HttpStatus.OK);
	}

}
