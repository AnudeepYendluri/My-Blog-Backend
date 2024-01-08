package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Post;
import com.example.demo.service.PostService;

@RestController
@RequestMapping("/posts")
@CrossOrigin("*")
public class PostController {

	@Autowired
	private PostService postService;
	
	@PostMapping
	public ResponseEntity<Post> createPost(@RequestBody Post post){
		Post post1 = postService.createPost(post);
			return ResponseEntity.ok(post1);				
	}
	
	
	
	@GetMapping
	public ResponseEntity<List<Post>> getAllPost() {
		List posts = postService.getAllPosts();
		return ResponseEntity.ok(posts);
	}	
	
	@PutMapping("/{id}")
	public ResponseEntity<String> editPost(@PathVariable Integer id, @RequestBody Post post) {
	   
	    if (!id.equals(post.getId())) {
	        return ResponseEntity.badRequest().body("ID in path variable and post body must match");
	    }

	    String postUpdateResult = postService.editPost(id, post);

	    return ResponseEntity.ok(postUpdateResult);
	}


	
	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deletePost(@PathVariable int postId) {
	    try {
	        // Log for debugging
	        System.out.println("Deleting post with ID: " + postId);

	        postService.deletePost(postId);
	        return ResponseEntity.noContent().build();
	    } catch (Exception e) {
	        // Log the exception for debugging
	        System.err.println("Error deleting post: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable int postId) {
        Optional<Post> postOptional = postService.getPostById(postId);
        if (postOptional.isPresent()) {
            return ResponseEntity.ok(postOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }
	
	
}
