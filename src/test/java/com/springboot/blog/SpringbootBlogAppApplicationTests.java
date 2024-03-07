package com.springboot.blog;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import com.springboot.blog.entity.Post;
import com.springboot.blog.repository.PostRepository;

@SpringBootTest
class SpringbootBlogAppApplicationTests {
	
	
	
	@Test
	void getAllPosts() {
		
		Post post = mock(Post.class);
		
		PostRepository postRepository = mock(PostRepository.class);
		
		//getallposts in sorted manner
		List<Post> posts = postRepository.findAll();
		
	}

}
