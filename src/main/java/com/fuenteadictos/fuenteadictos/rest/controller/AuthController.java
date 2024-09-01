package com.fuenteadictos.fuenteadictos.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fuenteadictos.fuenteadictos.domain.User;
import com.fuenteadictos.fuenteadictos.dto.UserDTO;
import com.fuenteadictos.fuenteadictos.mapper.UserMapper;
import com.fuenteadictos.fuenteadictos.service.UserService;
import com.fuenteadictos.fuenteadictos.util.JWTUtil;

@RestController
@RequestMapping(value = "fuenteadictos/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
public class AuthController {
    
	private final UserService service;
	private final JWTUtil jwtUtil;
	private final UserMapper userMapper;

	public AuthController(UserService service, JWTUtil jwtUtil, UserMapper userMapper) {
		this.service = service;
		this.jwtUtil = jwtUtil;
		this.userMapper = userMapper;
	}


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
	public UserDTO register(@Valid @RequestBody User user) {
		User newUser = service.createUser(user);
        return userMapper.toDto(newUser);
	}
	
	

}
