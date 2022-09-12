package com.programming.techie.springredditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String email;
    private String username;
    private Integer phoneNumber;
    private String address;
    private LocalDate created;
    private boolean enabled;

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
