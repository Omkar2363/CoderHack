package com.org.coderHack.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userId;

    private String userName;
    private Double score;
    private Set<Badge> badges;

    public User(String userId, String userName){
        this.userId = userId;
        this.userName = userName;
        this.score = 0.0D;
        this.badges = new HashSet<>();
    }
}
