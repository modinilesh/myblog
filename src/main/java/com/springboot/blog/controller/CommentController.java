package com.springboot.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	// Create comment
	@PostMapping("/posts/{postId}")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
			@Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}

	// GetAll Comments
	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable(value = "postId") long postId) {
		return new ResponseEntity<List<CommentDto>>(commentService.getAllComments(postId), HttpStatus.OK);
	}

	// Get Comment By ID
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(
			@PathVariable(value = "postId") long postId,
			@PathVariable(value = "commentId") long commentId) {
		return new ResponseEntity<CommentDto>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
	}
	
	//Update Comment
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(
			@PathVariable(value = "postId") long postId,
			@PathVariable(value = "commentId") long commentId,
			@Valid @RequestBody CommentDto commentDto){
		return new ResponseEntity<CommentDto>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
	}
	
	//Delete Comment
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(
			@PathVariable(value = "postId") long postId,
			@PathVariable(value = "commentId") long commentId){
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<String>("Comment with Id : "+ commentId +" deleted successfully.", HttpStatus.OK);
	}

}
