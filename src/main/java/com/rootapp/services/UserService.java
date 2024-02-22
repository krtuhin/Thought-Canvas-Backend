package com.rootapp.services;

import java.util.List;

import com.rootapp.payloads.UserDto;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Long userId);

    UserDto getUserById(Long userId);

    UserDto getUserByEmail(String email);

    List<UserDto> getAllUsers();

    void deleteUser(Long userId);

}
