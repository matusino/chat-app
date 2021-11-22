package com.sykora.chatapp.controller;

import com.sykora.chatapp.model.auth.LoginRequest;
import com.sykora.chatapp.model.auth.RegisterResponse;
import com.sykora.chatapp.model.User;
import com.sykora.chatapp.service.AuthenticationService;
import com.sykora.chatapp.service.UserService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.KeyException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<RegisterResponse> login(@RequestBody LoginRequest loginRequest) throws KeyException {
        RegisterResponse response = authenticationService.login(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity register(@Valid @RequestBody User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        try{
            userService.saveUser(user);
        }catch (Exception e){
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Registration successful", HttpStatus.OK);
    }
}
