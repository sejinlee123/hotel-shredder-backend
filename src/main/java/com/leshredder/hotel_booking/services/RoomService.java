package com.leshredder.hotel_booking.services;
import com.leshredder.hotel_booking.dtos.RoomDTO;
import com.leshredder.hotel_booking.enums.RoomType;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.leshredder.hotel_booking.dtos.Response;

public interface RoomService {

    Response addRoom(RoomDTO roomDTO, MultipartFile imageFile);

    Response updateRoom(RoomDTO roomDTO, MultipartFile imageFile);

    Response getAllRooms();

    Response getRoomById(Long id);

    Response deleteRoom(Long id);

    Response getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, RoomType roomType);

    List<RoomType> getAllRoomTypes();

    Response searchRoom(String input);

}
