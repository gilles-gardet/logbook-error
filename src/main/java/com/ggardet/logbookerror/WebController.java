package com.ggardet.logbookerror;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
public class WebController {

    @GetMapping("/public")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/private")
    public ResponseEntity<String> getAuthenticatedUser(final Principal principal) {
        return Optional.ofNullable(principal)
                .map(Principal::getName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
