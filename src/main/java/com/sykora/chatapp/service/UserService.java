package com.sykora.chatapp.service;

import com.sykora.chatapp.model.User;

import java.util.Optional;

public interface UserService  {

    Optional<User> findByUsername(String username);

    User saveUser(User user);

}
