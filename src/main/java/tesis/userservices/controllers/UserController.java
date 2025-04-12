package tesis.userservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tesis.userservices.dtos.UserDto;
import tesis.userservices.dtos.UserRequest;
import tesis.userservices.models.User;
import tesis.userservices.services.UserService;


import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody UserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<User> changePassword(@RequestBody UserDto request) {
        User user = userService.changePassword(request);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Long document,@RequestBody UserRequest request) {
        userService.updateUser(document , request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/document/{document}")
    public ResponseEntity<User> getUserByNumberDoc(@PathVariable Long document) {
        return ResponseEntity.ok(userService.getUserByNumberDoc(document));
    }

}
