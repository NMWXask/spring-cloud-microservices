package com.project.identityservice.controller;

import com.project.identityservice.dto.AuthenticationRequestDto;
import com.project.identityservice.dto.AuthenticationResponseDto;
import com.project.identityservice.dto.RegisterRequestDto;
import com.project.identityservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> registration(@RequestBody RegisterRequestDto request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authentication(@RequestBody AuthenticationRequestDto request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
