package com.rootapp.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public UserDto createUser(UserDto user) {

        User save = this.userRepository.save(this.dtoToUser(user));
        return this.userToDto(save);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        user.setImage(userDto.getImage());

        User updatedUser = this.userRepository.save(user);

        return userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        return this.userToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {

        User user = this.userRepository.findByEmail(email);
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = this.userRepository.findAll();

        List<UserDto> list = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

        return list;
    }

    @Override
    public void deleteUser(Long userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        this.userRepository.delete(user);
    }

    private User dtoToUser(UserDto userDto) {

        // return User.builder()
        // .name(userDto.getName())
        // .email(userDto.getEmail())
        // .password(userDto.getPassword())
        // .about(userDto.getAbout())
        // .image(userDto.getImage()).build();

        return modelMapper.map(userDto, User.class);
    }

    private UserDto userToDto(User user) {

        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .about(user.getAbout())
                .image(user.getImage()).build();
    }

}
