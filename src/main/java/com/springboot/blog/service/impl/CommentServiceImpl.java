package com.springboot.blog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exceptions.BlogAPIException;
import com.springboot.blog.exceptions.ResourceNotFound;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	// creating new comment
	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {

		// map commentDto to Comment
		Comment comment = mapToComment(commentDto);

		// Fetching post by ID
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("Post", "id", postId));

		// Setting Post to the Comment
		comment.setPost(post);

		// save comment to the database
		Comment savedComment = commentRepository.save(comment);

		// map savedComment to DTO
		CommentDto response = mapCommentToDTO(savedComment);

		return response;
	}

	// get all comments
	@Override
	public List<CommentDto> getAllComments(long postId) {

		// getting all comments for a perticular PostId
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("Post", "Id", postId));
		Set<Comment> comments = post.getComments();
		List<CommentDto> response = new ArrayList<>();

		// mapping comment to DTO
		for (Comment c : comments) {
			CommentDto cd = mapCommentToDTO(c);
			response.add(cd);
		}

		// Using Lambda function
//		List<CommentDto> response2 = comments.stream().map(Comment -> mapCommentToDTO(Comment)).collect(Collectors.toList());
//		System.out.println(response2);
		return response;
	}

	// Get comment by Comment ID
	@Override
	public CommentDto getCommentById(long postId, long commentId) {

		// retrieving post by postId
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("Post", "Id", postId));

		// retrieving Comment by postId
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFound("comment", "id", commentId));

		// Check if comment belongs to Post or not
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post.");
		}

		return mapCommentToDTO(comment);
	}

	// Update Comment
	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto inputComment) {
		// fetch post by postId
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("Post", "Id", postId));

		// fetch Comment by commentId
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFound("Comment", "Id", commentId));

		// check if comment belongs to post
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the Post.");
		}

		// Updating comment
		comment.setName(inputComment.getName());
		comment.setEmail(inputComment.getEmail());
		comment.setBody(inputComment.getBody());

		// saving comment to database
		commentRepository.save(comment);

		return mapCommentToDTO(comment);
	}

	// Delete Comment
	@Override
	public void deleteComment(long postId, long commentId) {
		// fetch post by postId
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("Post", "Id", postId));

		// fetch Comment by commentId
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFound("Comment", "Id", commentId));

		// check if comment belongs to post
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the Post.");
		}
		
		//deleting comment
		commentRepository.delete(comment);
	}

	// Converting Comment to DTO
	public CommentDto mapCommentToDTO(Comment comment) {

		CommentDto commentResponse = new CommentDto();
		commentResponse.setId(comment.getId());
		commentResponse.setName(comment.getName());
		commentResponse.setEmail(comment.getEmail());
		commentResponse.setBody(comment.getBody());
		return commentResponse;

	}

	// Converting DTO to Comment
	public Comment mapToComment(CommentDto commentDto) {

		Comment comment = new Comment();
		comment.setId(commentDto.getId());
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());

		return comment;

	}

}
