package com.fuenteadictos.fuenteadictos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fuenteadictos.fuenteadictos.error.UserAlreadyExistsException;
import com.fuenteadictos.fuenteadictos.error.UserNotFoundException;
import com.fuenteadictos.fuenteadictos.domain.User;
import com.fuenteadictos.fuenteadictos.repository.UserRepository;
import com.fuenteadictos.fuenteadictos.service.UserService;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        List<User> users = repository.findAll();
        if (users.isEmpty())
            throw new UserNotFoundException("No users found");
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUuid(String uuid) {
        return Optional.ofNullable(repository.findByUuid(uuid)).orElseThrow(() -> new UserNotFoundException());
    }

    @SuppressWarnings("deprecation") // REVISAR ESTA PARTE DEPRECADA, FIXME
    @Override
    @Transactional
    public User createUser(User newUser) {
        Optional<User> foundUser = Optional.empty();
        foundUser = repository.findByUsername(newUser.getUsername());
        if (foundUser.isPresent())
            throw new UserAlreadyExistsException("User with username " + newUser.getUsername() + " already exists");
        foundUser = repository.findByEmail(newUser.getEmail());
        if (foundUser.isPresent())
            throw new UserAlreadyExistsException("User with email " + newUser.getEmail() + " already exists");

        // Set user password
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, newUser.getPassword());
        newUser.setPassword(hash);

        return repository.save(newUser);
    }

    @Override
    @Transactional
    public User updateUser(User newUser, User oldUser) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    @Transactional
    public User retireUser(User user) {
        user.setVoided(true);
        return repository.save(user);
    }
    

    @Override
    @Transactional(readOnly = true)
	public User getUserByCredentials(User user) {

        Optional<User> oUser = repository.findByEmail(user.getEmail());

        if(!oUser.isPresent()) {
            return null;
        }

        String passwordHashed = oUser.get().getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        System.out.println(passwordHashed);
        System.out.println(user.getPassword());
        if(argon2.verify(passwordHashed, user.getPassword().getBytes())) {
            return oUser.get();
        }
        return null;
	}


}
