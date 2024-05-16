package com.org.coderHack.services;

import com.org.coderHack.dto.UserRequestDto;
import com.org.coderHack.dto.UserResponseDto;
import com.org.coderHack.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

     UserResponseDto createUser(UserRequestDto userRequestDto);
     UserResponseDto getUserById(String userId) throws ResourceNotFoundException;
     List<UserResponseDto> getUsers();
     UserResponseDto updateScore(String userId, Double score) throws ResourceNotFoundException;

     void deregisterUser(String userId) throws ResourceNotFoundException;
}
