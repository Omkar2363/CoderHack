package com.org.coderHack.services;

import com.org.coderHack.dto.UserRequestDto;
import com.org.coderHack.dto.UserResponseDto;
import com.org.coderHack.entities.Badge;
import com.org.coderHack.entities.User;
import com.org.coderHack.exceptions.ResourceNotFoundException;
import com.org.coderHack.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        //create User :
        User user = new User(userRequestDto.getUserId(), userRequestDto.getUserName());

        //register the User :
        User savedUser = userRepository.insert(user);

        UserResponseDto userResponseDto = modelMapper.map(savedUser, UserResponseDto.class);
        return userResponseDto;
    }

    @Override
    public UserResponseDto getUserById(String userId) {
        //find User of given Id :
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user ID", userId));

        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

        return userResponseDto;
    }

    @Override
    public List<UserResponseDto> getUsers() {

        List<User> users = userRepository.findAll();

        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        users.stream().forEach(user -> userResponseDtos.add(modelMapper.map(user, UserResponseDto.class)));

        List<UserResponseDto> sortedUserResponseDtos = userResponseDtos.stream()
                                                                        .sorted((a, b) -> b.getScore().compareTo(a.getScore()))
                                                                         .collect(Collectors.toList());

        return sortedUserResponseDtos;
    }

    @Override
    public UserResponseDto updateScore(String userId, Double score) throws ResourceNotFoundException {
        //Validating Score :
        if(score < 0){
            throw new RuntimeException("Score can not be less than 0.");
        }
        if(score > 100){
            throw new RuntimeException("Score can not be greater than 100.");
        }

        //Fetch User :
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user ID", userId));

        //UpdateUser
        User updatedUser = updateScoreAndBadges(user, score);

        //Update User in UserRepository :
        userRepository.save(updatedUser);

        UserResponseDto userResponseDto = modelMapper.map(updatedUser, UserResponseDto.class);

        return userResponseDto;

    }

    private User updateScoreAndBadges(User user, Double score){
        user.setScore(score);

        Set<Badge> badges = new HashSet<>();

        //Update Badges :
        if(score >= 1   &&  score < 30) badges.addAll(Arrays.asList(Badge.CODE_NINJA));
        if(score >= 30  &&  score < 60) badges.addAll(Arrays.asList(Badge.CODE_NINJA, Badge.CODE_CHAMP));
        if(score >= 60  &&  score <=100)badges.addAll(Arrays.asList(Badge.CODE_NINJA, Badge.CODE_CHAMP, Badge.CODE_MASTER));

        user.setBadges(badges);

        return user;
    }



    @Override
    public void deregisterUser(String userId) throws ResourceNotFoundException {

        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user ID", userId));

        userRepository.delete(user);
    }
}
