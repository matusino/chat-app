package com.sykora.chatapp.service.impl;

import com.sykora.chatapp.config.jwt.JwtProvider;
import com.sykora.chatapp.model.auth.LoginRequest;
import com.sykora.chatapp.model.auth.RegisterResponse;
import com.sykora.chatapp.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public RegisterResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                        ,loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new RegisterResponse(token,loginRequest.getUsername());
    }

}
