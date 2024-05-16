package com.org.coderHack.controllers;


import com.org.coderHack.dto.UpdateScoreRequestDto;
import com.org.coderHack.dto.UserRequestDto;
import com.org.coderHack.dto.UserResponseDto;
import com.org.coderHack.entities.User;
import com.org.coderHack.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(UserController.ROOT_PATH)
public class UserController {
    public static final String ROOT_PATH = "coderHack/";

    @Autowired
    private UserService userService;



    @PostMapping("/users")
    public ResponseEntity<UserResponseDto>  registerUser(@RequestBody UserRequestDto userRequestDto){
        //Register the User :
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.ACCEPTED);
    }


    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String userId){
        UserResponseDto userResponseDto = userService.getUserById(userId);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        List<UserResponseDto> userResponseDtos = userService.getUsers();
        return ResponseEntity.ok(userResponseDtos);
    }


    @PutMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> updateScoreOfUser(@PathVariable String userId, @RequestBody UpdateScoreRequestDto updateScoreRequestDto ){
        UserResponseDto userResponseDto = userService.updateScore(userId, updateScoreRequestDto.getScore());
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);

    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deregisterUser(@PathVariable String userId){
        userService.deregisterUser(userId);

        return new ResponseEntity<>("Deregistered Successfully...!!!", HttpStatus.OK);
    }




}



