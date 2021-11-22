package com.sykora.chatapp.service;

import com.sykora.chatapp.model.auth.LoginRequest;
import com.sykora.chatapp.model.auth.RegisterResponse;
import org.springframework.stereotype.Service;

import java.security.KeyException;

@Service
public interface AuthenticationService {

    RegisterResponse login(LoginRequest loginRequest) throws KeyException;
}
