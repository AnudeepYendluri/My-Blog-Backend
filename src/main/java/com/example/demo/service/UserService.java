package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {	

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User registerUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User user1 = userRepo.save(user);
		if(user1 != null) {
			return user1;
		}
		return null;
	}

	/*
	public User loginUser(String email,String password) {
		User user = userRepo.findByEmail(email);
		if(user != null) {
			if(passwordEncoder.matches(password, user.getPassword())) {
		return user;
		}
		}
		return null;
	} */
	
	public User loginUser(User user) {
	    User user1 = userRepo.findByEmail(user.getEmail());
	    
	    if (passwordEncoder.matches(user.getPassword(),user1.getPassword())) {
	        return user1;
	    } else {
	        return null;
	    }
	}
	
	
	public boolean deleteUserByEmail(String email) {
		try {
		userRepo.deleteByEmail(email);
		return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	
	/*
	public Optional<User> getUserById(int id) {
		return userRepo.findById(id);
	}
	
	public String deleteUserById(int id) {
		userRepo.deleteById(id);
		return "User Deleted Succesfully";
	}
	
	*/
	
	
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	
	
	
	
	
}
