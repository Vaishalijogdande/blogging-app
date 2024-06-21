package com.blogging_app.service;

import com.blogging_app.config.AppConstants;
import com.blogging_app.dto.UserDto;
import com.blogging_app.entity.Role;
import com.blogging_app.entity.User;
import com.blogging_app.exception.ResourceNotFoundException;
import com.blogging_app.repository.RoleRepository;
import com.blogging_app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public UserDto registerNewUser(UserDto userDto){

       User user = this.modelMapper.map(userDto, User.class);

       user.setPassword(this.passwordEncoder.encode(user.getPassword()));


        Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser =  this.userRepository.save(user);
        return this.modelMapper.map(newUser, UserDto.class);

    }

    public UserDto createUser(UserDto userDto){

        User user = this.DtoToUser(userDto);
        User savedUser= userRepository.save(user);

        return this.UserToDto(savedUser);
    }


    public UserDto updateUser(UserDto userDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", id));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User savedUser = this.userRepository.save(user);
        UserDto userDto1 =  this.UserToDto(savedUser);
        return userDto1;


    }
    public UserDto findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
      return this.UserToDto(user);
    }


    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(this::UserToDto)
                .collect(Collectors.toList());
        return userDtos;
    }

    public String deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
         userRepository.delete(user);
         return "User removed successfully!!!";
    }

    public User DtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto, User.class);
        return user;

    }

    public UserDto UserToDto(User user){
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;

    }


}
