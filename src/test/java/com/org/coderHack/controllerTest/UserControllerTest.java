package com.org.coderHack.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.coderHack.controllers.UserController;
import com.org.coderHack.dto.UpdateScoreRequestDto;
import com.org.coderHack.dto.UserRequestDto;
import com.org.coderHack.dto.UserResponseDto;
import com.org.coderHack.entities.Badge;
import com.org.coderHack.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void deregisterUserTest() throws Exception {
        String userId = "001";
        doNothing().when(userService).deregisterUser(userId);

        mockMvc.perform(delete("/coderHack/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Deregistered Successfully...!!!"));
    }

    @Test
    public void registerUserTest() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserId("001");
        userRequestDto.setUserName("Ram");

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserId("001");
        userResponseDto.setUserName("Ram");
        userResponseDto.setScore(0.0);
        userResponseDto.setBadges(Set.of());

        when(userService.createUser(Mockito.any(UserRequestDto.class))).thenReturn(userResponseDto);

        mockMvc.perform(post("/coderHack/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(objectMapper.writeValueAsString(userResponseDto)));
    }

    @Test
    public void getUserByIdTest() throws Exception {
        String userId = "001";

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserId("001");
        userResponseDto.setUserName("Ram");
        userResponseDto.setScore(0.0);
        userResponseDto.setBadges(Set.of());

        when(userService.getUserById(userId)).thenReturn(userResponseDto);

        mockMvc.perform(get("/coderHack/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userResponseDto)));
    }

    @Test
    public void getAllUsersTest() throws Exception {
        UserResponseDto userResponseDto1 = new UserResponseDto();
        userResponseDto1.setUserId("001");
        userResponseDto1.setUserName("Ram");
        userResponseDto1.setScore(0.0);
        userResponseDto1.setBadges(Set.of());

        UserResponseDto userResponseDto2 = new UserResponseDto();
        userResponseDto2.setUserId("001");
        userResponseDto2.setUserName("Ram");
        userResponseDto2.setScore(0.0);
        userResponseDto2.setBadges(Set.of());

        List<UserResponseDto> userResponseDtos = Arrays.asList(userResponseDto1, userResponseDto2);

        when(userService.getUsers()).thenReturn(userResponseDtos);

        mockMvc.perform(get("/coderHack/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userResponseDtos)));
    }

    @Test
    public void updateScoreOfUserTest() throws Exception {
        String userId = "001";
        UpdateScoreRequestDto updateScoreRequestDto = new UpdateScoreRequestDto();
        updateScoreRequestDto.setScore(90.0);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserId("001");
        userResponseDto.setUserName("Ram");
        userResponseDto.setScore(90.0);
        userResponseDto.setBadges(Set.of(Badge.CODE_NINJA, Badge.CODE_CHAMP, Badge.CODE_MASTER));

        when(userService.updateScore(Mockito.eq(userId), Mockito.anyDouble())).thenReturn(userResponseDto);

        mockMvc.perform(put("/coderHack/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateScoreRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userResponseDto)));
    }
}
