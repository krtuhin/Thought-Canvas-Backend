package com.rootapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rootapp.payloads.ApiResponse;
import com.rootapp.payloads.UserDto;
import com.rootapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST -> create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        UserDto createdUserDto = this.userService.createUser(userDto);

        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    // PUT -> update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId) {

        UserDto updatedUserDto = this.userService.updateUser(userDto, userId);

        return ResponseEntity.ok(updatedUserDto);
    }

    // GET -> fetch user/users
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long userId) {

        UserDto userDto = this.userService.getUserById(userId);

        return ResponseEntity.ok(userDto);
    }

    // GET -> fetch all users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAlleUser() {

        List<UserDto> userDtos = this.userService.getAllUsers();

        return ResponseEntity.ok(userDtos);
    }

    // DELETE -> delete user
    @PreAuthorize("hasRole('ADMIN')") // only admin can delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long userId) {

        this.userService.deleteUser(userId);

        return ResponseEntity.ok(ApiResponse.builder().message("user deleted successfully").success(true).build());
    }

}
