package com.prosoft.testcontainer_demo.service;

import com.prosoft.testcontainer_demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    User createUser(User user);

    Optional<User> updateUser(Long id, User user);

    void deleteUser(Long id);

}
