package com.example.oauth.web;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.oauth.document.User;
import com.example.oauth.dto.SignupDTO;
import com.example.oauth.security.TokenGenerator;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserDetailsManager userDetailsManager;

    @Autowired
    TokenGenerator tokenGenerator;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody SignupDTO signupDTO) {
        User user = new User(signupDTO.getUsername(), signupDTO.getPassword());
        userDetailsManager.createUser(user);

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, signupDTO.getPassword(),
                Collections.EMPTY_LIST);

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

}
