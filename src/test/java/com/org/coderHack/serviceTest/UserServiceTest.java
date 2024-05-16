package com.org.coderHack.serviceTest;

import com.org.coderHack.dto.UserRequestDto;
import com.org.coderHack.dto.UserResponseDto;
import com.org.coderHack.entities.Badge;
import com.org.coderHack.entities.User;
import com.org.coderHack.exceptions.ResourceNotFoundException;
import com.org.coderHack.repositories.UserRepository;
import com.org.coderHack.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void registerUserTest() {
        User user = new User("001", "Ram");
        when(userRepository.insert(any(User.class))).thenReturn(user);

        UserResponseDto registeredUser = userService.createUser(new UserRequestDto("001", "Ram"));

        assertEquals("Ram", registeredUser.getUserName());
    }

    @Test
    public void getUserTest() {
        User user = new User("001", "Ram");
        when(userRepository.findById(any(String.class))).thenReturn(Optional.of(user));

        UserResponseDto fetchedUser = userService.getUserById("001");

        assertEquals("001", fetchedUser.getUserId());
        assertEquals("Ram", fetchedUser.getUserName());
    }

    @Test
    public void checkResourceNotFoundException() {

        when(userRepository.findById(any(String.class))).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById("51"));
    }



    @Test
    public void deregisterUserThrowsResourceNotFoundExceptionTest() {
        when(userRepository.findById(any(String.class))).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> userService.deregisterUser("35"));
    }


    @Test
    public void updateScoreTest(){
        //Registering a user first :
        User user = new User("001", "Ram");
        when(userRepository.insert(any(User.class))).thenReturn(user);

        UserResponseDto registeredUser = userService.createUser(new UserRequestDto("001", "Ram"));

        //Checking the User :
        assertEquals("001", registeredUser.getUserId());
        assertEquals("Ram", registeredUser.getUserName());
        assertEquals(0.0, registeredUser.getScore());
        assertEquals(Set.of(), registeredUser.getBadges());

        //Updating the score of the User :
        user.setScore(90.0);
        user.setBadges(Set.of(Badge.CODE_NINJA, Badge.CODE_CHAMP, Badge.CODE_MASTER));

        when(userRepository.findById(any(String.class))).thenReturn(Optional.of(user));

        UserResponseDto updatedUser = userService.updateScore("001", 90.0);

        //Checking the updated User :
        assertEquals("001", updatedUser.getUserId());
        assertEquals("Ram", updatedUser.getUserName());
        assertEquals(90.0, updatedUser.getScore());
        assertEquals(Set.of(Badge.CODE_NINJA, Badge.CODE_CHAMP, Badge.CODE_MASTER), updatedUser.getBadges());

    }

    @Test
    public void updateScoreThrowsResourceNotFoundExceptionTest(){
        when(userRepository.findById(any(String.class))).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> userService.updateScore("32", 90.0));
    }

    @Test
    public void updateScoreThrowsRuntimeExceptionTest(){

        //Throw runtime exception if   0 > score > 100 :
        assertThrows(RuntimeException.class, () -> userService.updateScore("1", -1.0));
        assertThrows(RuntimeException.class, () -> userService.updateScore("5", 101.0));

    }

}
