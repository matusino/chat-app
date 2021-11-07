package com.sykora.chatapp.controller;

import com.sykora.chatapp.model.User;
import com.sykora.chatapp.service.UserService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BasicAuthController {

    private final UserService userService;

    public BasicAuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/login")
    public String basicauth() {
        return "You are authenticated";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity register(@ModelAttribute User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(user);
        return new ResponseEntity<>("Registration successful", HttpStatus.OK);
    }
}
