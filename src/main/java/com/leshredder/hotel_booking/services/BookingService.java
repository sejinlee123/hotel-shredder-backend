package com.leshredder.hotel_booking.services;

import com.leshredder.hotel_booking.dtos.BookingDTO;
import com.leshredder.hotel_booking.dtos.Response;

public interface BookingService {

    Response getAllBookings();
    Response createBooking(BookingDTO bookingDTO);
    Response findBookingByReferenceNo(String  bookingReference);
    Response updateBooking(BookingDTO bookingDTO);
}
