package com.ashmita.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource{
	
	@Autowired
	private UserDaoService service;
	
	@GetMapping("/users")
	public List<User> retreiveAllUser(){
		return service.findAllUser();
	}
	
	@GetMapping("/users/{id}")
	public User retreiveUserById(@PathVariable Integer id){
		User user = service.findUserById(id);
		if(user == null) {
			throw new UserNotFoundException("id "+ id + " not found");
		}
		return user;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User resultUser= service.save(user);
		//return the URI of the resource => /users/{id}, 
		//id will be replaced by user.gerId()
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(resultUser.getId())
						.toUri();
		return ResponseEntity.created(location ).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable Integer id){
		service.deleteById(id);
	}

}
