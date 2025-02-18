package com.vietpq.JobHunter.controller;

import com.turkraft.springfilter.boot.Filter;
import com.vietpq.JobHunter.dto.convertDTO.ConvertToUserDTO;
import com.vietpq.JobHunter.dto.page.ResultPaginationDTO;
import com.vietpq.JobHunter.dto.user.ResCreateUserDTO;
import com.vietpq.JobHunter.dto.user.ResUpdateUserDTO;
import com.vietpq.JobHunter.dto.user.ResUserDTO;
import com.vietpq.JobHunter.entity.User;
import com.vietpq.JobHunter.exception.InvalidEmailException;
import com.vietpq.JobHunter.exception.InvalidException;
import com.vietpq.JobHunter.service.user.UserService;
import com.vietpq.JobHunter.util.anotation.ApiMesage;
import com.vietpq.JobHunter.util.validator.AuthValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ApiMesage("Create a new user")
    public ResponseEntity<ResCreateUserDTO> createUser(@RequestBody User user) throws InvalidException {

       User newUser =  this.userService.handleCreateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(ConvertToUserDTO.convertToResCreateUserDTO(newUser));
    }
    @DeleteMapping("/users/{id}")
    @ApiMesage("Detele a user")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) throws InvalidException {
        userService.handleDeleteUser(id);
        return ResponseEntity.ok(null);
    }

   @GetMapping("/users/{id}")
   @ApiMesage("Create a new user")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable("id") long id){
        return ResponseEntity.ok(ConvertToUserDTO.convertToResUserDTO(userService.fetchUserById(id)));
    }

    @GetMapping("/users")
    public ResponseEntity<ResultPaginationDTO> getAllUsers(@Filter Specification<User> specification, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userService.fetchAllUsers(specification,pageable));
    }
    @PutMapping("/users")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User user) throws InvalidEmailException {
        return ResponseEntity.status(HttpStatus.OK).body(ConvertToUserDTO.convertToResUpdateUserDTO(userService.handleUpdateUser(user)));
    }
}
