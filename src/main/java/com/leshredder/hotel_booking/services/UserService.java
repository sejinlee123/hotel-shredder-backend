package com.leshredder.hotel_booking.services;

import com.leshredder.hotel_booking.dtos.LoginRequest;
import com.leshredder.hotel_booking.dtos.RegistrationRequest;
import com.leshredder.hotel_booking.dtos.Response;
import com.leshredder.hotel_booking.dtos.UserDTO;
import com.leshredder.hotel_booking.entities.User;

public interface UserService {
    

    Response registerUser(RegistrationRequest registrationRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUser();

    Response getOwnAccountDetails();

    User getCurrentLoggedInUser();

    Response updateOwnAccount(UserDTO userDTO);

    Response deleteOwnAccount();

    Response getMyBookingHistory();


}
