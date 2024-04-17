package com.fuenteadictos.fuenteadictos.service;


import java.util.List;
import java.util.Optional;

import com.fuenteadictos.fuenteadictos.domain.User;

import javax.validation.Valid;

public interface UserService {
    
    List<User> getUsers();

    Optional<User> getUserByUuid(String uuid);

    User createUser(@Valid  User newUser);

    User updateUser(@Valid User newUser, User oldUser);
    
    User retireUser(User user);

    User getUserByCredentials(User user);
}
