package com.app.pocket.controllers;

import com.app.pocket.pojo.JwtRequest;
import com.app.pocket.pojo.JwtResponse;
import com.app.pocket.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println("Inside JwtController.createJwtToken");
        return jwtService.createJwtToken(jwtRequest);
    }
}
