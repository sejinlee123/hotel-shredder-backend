package com.leshredder.hotel_booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leshredder.hotel_booking.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
