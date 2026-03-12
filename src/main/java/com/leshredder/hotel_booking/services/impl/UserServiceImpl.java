package com.leshredder.hotel_booking.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.leshredder.hotel_booking.dtos.BookingDTO;
import com.leshredder.hotel_booking.dtos.LoginRequest;
import com.leshredder.hotel_booking.dtos.RegistrationRequest;
import com.leshredder.hotel_booking.dtos.Response;
import com.leshredder.hotel_booking.dtos.UserDTO;
import com.leshredder.hotel_booking.entities.Booking;
import com.leshredder.hotel_booking.entities.User;
import com.leshredder.hotel_booking.enums.UserRole;
import com.leshredder.hotel_booking.exceptions.InvalidCredentialException;
import com.leshredder.hotel_booking.exceptions.NotFoundException;
import com.leshredder.hotel_booking.repositories.BookingRepository;
import com.leshredder.hotel_booking.repositories.UserRepository;
import com.leshredder.hotel_booking.security.JwtUtils;
import com.leshredder.hotel_booking.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;


    @Override
    public Response registerUser(RegistrationRequest registrationRequest) {
        UserRole role = UserRole.CUSTOMER;
        if (registrationRequest.getRole() != null) {
            role = registrationRequest.getRole();
        }

        User userToSave = User.builder()
            .firstName(registrationRequest.getFirstName())
            .lastName(registrationRequest.getLastName())
            .email(registrationRequest.getEmail())
            .password(passwordEncoder.encode(registrationRequest.getPassword()))
            .phoneNumber(registrationRequest.getPhoneNumber())
            .role(role)
            .isActive(Boolean.TRUE)
            .build();
        
        userRepository.save(userToSave);
        return Response.builder()
            .status(200)
            .message("user created successfully")
            .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(()-> new NotFoundException("Email not found"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialException("Password doesn't match");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        return Response.builder()
            .status(200)
            .message("user logged in successfully")
            .role(user.getRole())
            .token(token)
            .isActive(user.getIsActive())
            .expirationTime("6 months")
            .build();
    }

    @Override
    public Response getAllUser() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<UserDTO> userDTOList = modelMapper.map(users, new TypeToken<List<UserDTO>>(){}.getType());
        return Response.builder()
            .status(200)
            .message("success")
            .users(userDTOList)
            .build();
    }

    @Override
    public User getCurrentLoggedInUser() {
        String email =  SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("User Not Found"));
    }

    @Override
    public Response getOwnAccountDetails() {
        String email =  SecurityContextHolder.getContext().getAuthentication().getName();
        User user = getCurrentLoggedInUser();
        log.info("Inside getOwnAccountDetails user email is {}", email);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return Response.builder()
            .status(200)
            .message("success")
            .user(userDTO)
            .build();
    }

    @Override
    public Response updateOwnAccount(UserDTO userDTO) {
        User existingUser = getCurrentLoggedInUser();
        log.info("Inside update user");
        if (userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getFirstName() != null) existingUser.setFirstName(userDTO.getFirstName());
        if (userDTO.getLastName() != null) existingUser.setLastName(userDTO.getLastName());
        if (userDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(userDTO.getPhoneNumber());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        userRepository.save(existingUser);
        return Response.builder()
            .status(200)
            .message("user updated successfully")
            .user(userDTO)
            .build();        
    }

    @Override
    public Response deleteOwnAccount() {
        User user = getCurrentLoggedInUser();
        userRepository.delete(user);

        return Response.builder()
            .status(200)
            .message("user deleted successfully")
            .build(); 
    }

    @Override
    public Response getMyBookingHistory() {
        User user = getCurrentLoggedInUser();
        List<Booking> bookingList = bookingRepository.findByUserId(user.getId());
        List<BookingDTO> bookingDTOList = modelMapper.map(bookingList, new TypeToken<List<BookingDTO>>() {}.getType());

        return Response.builder()
        .status(200)
        .message("success")
        .bookings(bookingDTOList)
        .build(); 
    }

}
