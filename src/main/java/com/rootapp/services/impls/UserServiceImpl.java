package com.rootapp.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rootapp.entities.User;
import com.rootapp.exceptions.ResourceNotFoundException;
import com.rootapp.payloads.UserDto;
import com.rootapp.repositories.UserRepository;
import com.rootapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // create user in the database
    @Override
    public UserDto createUser(UserDto user) {

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        User save = this.userRepository.save(this.dtoToUser(user));

        return this.userToDto(save);
    }

    // update user in the database
    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user.setAbout(userDto.getAbout());
        user.setImage(userDto.getImage());

        User updatedUser = this.userRepository.save(user);

        return userToDto(updatedUser);
    }

    // fetch user from database by id
    @Override
    public UserDto getUserById(Long userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        return this.userToDto(user);
    }

    // fetch user from database by email
    @Override
    public UserDto getUserByEmail(String email) {

        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Email " + email, 0l));

        return this.userToDto(user);
    }

    // fetch all user from database
    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = this.userRepository.findAll();

        List<UserDto> list = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

        return list;
    }

    // delete user from database
    @Override
    public void deleteUser(Long userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        this.userRepository.delete(user);
    }

    // converting userDto to user type
    private User dtoToUser(UserDto userDto) {

        // return User.builder()
        // .name(userDto.getName())
        // .email(userDto.getEmail())
        // .password(userDto.getPassword())
        // .about(userDto.getAbout())
        // .image(userDto.getImage()).build();

        return modelMapper.map(userDto, User.class);
    }

    // converting user to userDto type
    private UserDto userToDto(User user) {

        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .about(user.getAbout())
                .image(user.getImage()).build();
    }

}
