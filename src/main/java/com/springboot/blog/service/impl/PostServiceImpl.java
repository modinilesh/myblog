package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exceptions.ResourceNotFound;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepository;

	@Override
	public PostDto createPost(PostDto postDto) {

		// Mapping DTO to Post
		Post post = mapToPost(postDto);

		// Saving Post
		Post newPost = postRepository.save(post);

		// Converting post to DTO as response
		PostDto postResponse = mapToDTO(newPost);

		return postResponse;
	}

	// get all post
	@Override
	public List<PostDto> getAllPosts() {

		List<Post> posts = postRepository.findAll();
		return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

	}

	// get post by ID
	@Override
	public PostDto getPostById(long id) {
		// getting post
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "id", id));

		// maping post to DTO
		PostDto response = mapToDTO(post);
		return response;
	}

	// Update post
	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		
		//get the post by id from database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "id", id));
		
		//updating the data in post from input(DTO)
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		//Update the post in database
		Post updatedPost = postRepository.save(post);
		
		//return DTO of updated post
		return mapToDTO(updatedPost);
	}
	
	//delete post
	@Override
	public void deletePost(long id) {
		//fetching Post
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "id", id));
		
		//deleting the post
		postRepository.delete(post);
		
	}

	
	
	

	// Converting Post to DTO
	public PostDto mapToDTO(Post post) {

		PostDto postResponse = new PostDto();
		postResponse.setId(post.getId());
		postResponse.setTitle(post.getTitle());
		postResponse.setDescription(post.getDescription());
		postResponse.setContent(post.getContent());
		return postResponse;

	}

	// Converting DTO to Post
	public Post mapToPost(PostDto postDto) {

		Post post = new Post();
		post.setId(postDto.getId());
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		return post;

	}

	
}
