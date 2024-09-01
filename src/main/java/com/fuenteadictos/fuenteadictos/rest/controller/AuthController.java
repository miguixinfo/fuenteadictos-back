package com.fuenteadictos.fuenteadictos.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fuenteadictos.fuenteadictos.domain.User;
import com.fuenteadictos.fuenteadictos.service.UserService;
import com.fuenteadictos.fuenteadictos.util.JWTUtil;

@RestController
@RequestMapping(value = "fuenteadictos/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    
    @Autowired
	private UserService service;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody User user) {
		Map<String, Object> response = new HashMap<>();
		User loggedUser = service.getUserByCredentials(user);
		if(loggedUser != null) {
			String tokenJWT = jwtUtil.create(String.valueOf(loggedUser.getUsername()), loggedUser.getEmail());

			response.put("token", tokenJWT);
            response.put("success", "OK");
		} else response.put("success", "FAIL");
		return response;
	}

	@PostMapping("/register")
	public User register(@RequestBody User user) {
		User newUser = service.createUser(user);
        return newUser;
	}
	
	

}
