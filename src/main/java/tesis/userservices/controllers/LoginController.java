package tesis.userservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tesis.userservices.dtos.LoginResponse;
import tesis.userservices.dtos.UserDto;
import tesis.userservices.services.UserService;


@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<LoginResponse> login(@RequestBody UserDto request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }



}
