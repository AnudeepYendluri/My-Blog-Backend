package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepo;
	
	/*public Post createPost(Post post) {
		return postRepo.save(post);
	}*/
	
	 public Post createPost(Post post) {
	        try {
	            Post savedPost = postRepo.save(post);
	            System.out.println("Post saved successfully: " + savedPost);
	            return savedPost;
	        } catch (Exception e) {
	            System.err.println("Error saving post: " + e.getMessage());
	            throw e;
	        }
	    }
	
	public void deletePost(int postId) {
		postRepo.deleteById(postId);
	}
	
	public Optional<Post> getPostById(int postId) {
		return postRepo.findById(postId);
	}
	
	public List<Post> getAllPosts() {
		return postRepo.findAll();
	}
	
	public String editPost(int id, Post post) {
		post.setId(id);
		Post updatePost = postRepo.save(post);
		if(updatePost != null) {
			return "Post Updated Succesfully";
		}
		return "Something went wrong";
	}
	
	public Optional<Post> getPostById(Integer id) {
		Optional<Post> post = postRepo.findById(id);
		return post;
	}
	
}
