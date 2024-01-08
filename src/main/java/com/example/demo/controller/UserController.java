package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.UserAuthenticationProvider;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserAuthenticationProvider userAuthenticationProvider;

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		User user1 = userService.registerUser(user);
		if(user1 !=null) {
		user1.setToken(userAuthenticationProvider.createToken(user1.getEmail()));
		return ResponseEntity.ok(user1);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user) {
	    User authenticatedUser = userService.loginUser(user);
	    if (authenticatedUser != null) {
	        // Generate a new token with a new expiration time
	        String authToken = userAuthenticationProvider.createToken(authenticatedUser.getEmail());

	        // Use a Map to encapsulate the response data
	        Map<String, Object> responseMap = new HashMap<>();
	        responseMap.put("user", authenticatedUser);
	        responseMap.put("authToken", authToken);
	        responseMap.put("userId", authenticatedUser.getId());

	        return ResponseEntity.ok(responseMap);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	    }
	}

	
	@DeleteMapping("/delete/{email}")
	public ResponseEntity<?> deleteUserByEmail(@PathVariable String email){
		boolean deleted = userService.deleteUserByEmail(email);
		if(deleted) {
			return ResponseEntity.ok("User Deleted Succesfully");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
		}
	}
	
	@GetMapping("/allusers")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		if(!users.isEmpty()) {
			return ResponseEntity.ok(users);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(users);
					
		}
	}
	
	@GetMapping("/getuser/{email}")
	public ResponseEntity<?> getUserByEmail(String email) {
		User user = userService.findByEmail(email);
		if(user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
	}
	
	
}
