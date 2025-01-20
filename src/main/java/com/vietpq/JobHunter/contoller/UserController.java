package com.vietpq.JobHunter.contoller;

import com.vietpq.JobHunter.entity.User;
import com.vietpq.JobHunter.exception.InvalidException;
import com.vietpq.JobHunter.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
       User newUser =  this.userService.handleCreateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) throws InvalidException {
        if(id>1500){
            throw  new InvalidException("Id lon hon 1500");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.noContent().build();
    }

   @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id){
        User user = this.userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> listUser = this.userService.getAlluser();
        return ResponseEntity.status(HttpStatus.OK).body(listUser);
    }
    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User userUpdate =  this.userService.handleUpdateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdate);
    }
}
