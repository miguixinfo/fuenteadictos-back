package com.fuenteadictos.fuenteadictos.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fuenteadictos.fuenteadictos.error.UserNotFoundException;
import com.fuenteadictos.fuenteadictos.domain.User;
import com.fuenteadictos.fuenteadictos.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(value = "/fuenteadictos/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    
    @Autowired
    private UserService service;

    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        List<User> users = service.getUsers();
        if (users.isEmpty())
            throw new UserNotFoundException("No users found");

        return service.getUsers();
    }


    @GetMapping("/{uuid}")
    public Optional<User> getUser(@PathVariable String uuid) {
        Optional<User> userOptional = service.getUserByUuid(uuid);

        if (!userOptional.isPresent())
            throw new UserNotFoundException("User with uuid " + uuid + " not found");

        return Optional.ofNullable(userOptional.get());
    }


    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        User newUser = service.createUser(user);
        return newUser;
    }


    @PutMapping("/update/{uuid}")
    public User updateUser(
        @PathVariable("uuid") String uuid,
        @Valid @RequestBody User newUser) {

            Optional<User> userOptional = service.getUserByUuid(uuid);
            if (!userOptional.isPresent())
                throw new UserNotFoundException();

            User user = userOptional.get();

            return service.updateUser(newUser, user);

    }


    @DeleteMapping("/{uuid}")
    public User deleteUser(@PathVariable("uuid") String uuid) {
        Optional<User> userOptional = service.getUserByUuid(uuid);
        if (!userOptional.isPresent())
            throw new UserNotFoundException();

        User user = userOptional.get();

        return service.retireUser(user);
    }

}
