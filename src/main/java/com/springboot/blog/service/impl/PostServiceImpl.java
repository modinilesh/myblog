package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exceptions.ResourceNotFound;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	ModelMapper modelMapper;

	//create Post
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
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		//Creating object of Sort
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		//Creating Pageable object
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		//get Page Object of post from pageable
		Page<Post> getAllPosts = postRepository.findAll(pageable);
		
		//get content from Page Object
		List<Post> posts = getAllPosts.getContent();
		
		List<PostDto> contentAsDTO = posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
		
		//Creating a Good Response
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(contentAsDTO);
		postResponse.setPageNo(getAllPosts.getNumber());
		postResponse.setPageSize(getAllPosts.getSize());
		postResponse.setTotalElements(getAllPosts.getTotalElements());
		postResponse.setTotalPages(getAllPosts.getTotalPages());
		postResponse.setLast(getAllPosts.isLast());

		return postResponse;
	}

	// get post by ID
	@Override
	public PostDto getPostById(long id) {
		// getting post
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "Id", id));

		// mapping post to DTO
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

//		PostDto postResponse = new PostDto();
//		postResponse.setId(post.getId());
//		postResponse.setTitle(post.getTitle());
//		postResponse.setDescription(post.getDescription());
//		postResponse.setContent(post.getContent());
		
		//using ModelMapper
		PostDto postResponse = modelMapper.map(post, PostDto.class);
		return postResponse;

	}

	// Converting DTO to Post
	public Post mapToPost(PostDto postDto) {

//		Post post = new Post();
//		post.setId(postDto.getId());
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		
		Post post = modelMapper.map(postDto, Post.class);

		return post;

	}

	
}
