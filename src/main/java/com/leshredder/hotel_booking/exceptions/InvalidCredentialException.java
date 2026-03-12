package com.leshredder.hotel_booking.exceptions;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String message){
        super(message);
    }
}
