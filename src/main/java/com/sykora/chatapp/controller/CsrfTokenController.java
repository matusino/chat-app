package com.sykora.chatapp.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfTokenController {

    /*
    Expose CSRF token to REST.
    Token can be used in Stomp client to authenticate through the CSRF security layer
     */
    @RequestMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

}
