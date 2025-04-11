package com.example.userservice.services;

import com.example.userservice.dtos.UserDTO;
import com.example.userservice.entities.User;
import com.example.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User saveUser(UserDTO user) {
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        return repository.save(newUser);
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }
}
