package com.org.coderHack.dto;

import com.org.coderHack.entities.Badge;
import com.org.coderHack.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String userId;

    private String userName;
    private Double score;
    private Set<Badge> badges;

}
