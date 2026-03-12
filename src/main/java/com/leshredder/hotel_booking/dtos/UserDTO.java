package com.leshredder.hotel_booking.dtos;

import java.time.LocalDateTime;

import com.leshredder.hotel_booking.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private Long id;


    private String email;


    private String password;
    private String firstName;
    private String lastName;


    private String phoneNumber;


    private UserRole role;

    private Boolean isActive;
    private LocalDateTime createdAt;
}
