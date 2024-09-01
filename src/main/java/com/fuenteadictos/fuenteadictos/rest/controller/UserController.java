package com.fuenteadictos.fuenteadictos.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fuenteadictos.fuenteadictos.error.UserNotFoundException;
import com.fuenteadictos.fuenteadictos.mapper.UserMapper;
import com.fuenteadictos.fuenteadictos.domain.User;
import com.fuenteadictos.fuenteadictos.dto.UserDTO;
import com.fuenteadictos.fuenteadictos.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(value = "/fuenteadictos/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    
    private final UserService service;
    private final UserMapper userMapper;

    public UserController(UserService service, UserMapper userMapper) {
        this.service = service;
        this.userMapper = userMapper;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        List<User> users = service.getUsers();
        if (users.isEmpty())
            throw new UserNotFoundException("No users found");

        List<UserDTO> response = new ArrayList<>();
        users.forEach(
            u -> response.add(userMapper.toDto(u))
        );

        return response;
    }

    @GetMapping("/{uuid}")
    public Optional<UserDTO> getUser(@PathVariable String uuid) {
        Optional<User> userOptional = service.getUserByUuid(uuid);

        if (!userOptional.isPresent())
            throw new UserNotFoundException("User with uuid " + uuid + " not found");

        return Optional.ofNullable(userMapper.toDto(userOptional.get()));
    }

    @PutMapping("/update/{uuid}")
    public UserDTO updateUser(
        @PathVariable("uuid") String uuid,
        @Valid @RequestBody User newUser) {

            Optional<User> userOptional = service.getUserByUuid(uuid);
            if (!userOptional.isPresent())
                throw new UserNotFoundException();

            User user = userOptional.get();

            return userMapper.toDto(service.updateUser(newUser, user));
    }


    @DeleteMapping("/{uuid}")
    public UserDTO deleteUser(@PathVariable("uuid") String uuid) {
        Optional<User> userOptional = service.getUserByUuid(uuid);
        if (!userOptional.isPresent())
            throw new UserNotFoundException();

        User user = userOptional.get();

        return userMapper.toDto(service.retireUser(user));
    }

}
