package com.leshredder.hotel_booking.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leshredder.hotel_booking.entities.BookingReference;

public interface BookingReferenceRepository extends JpaRepository<BookingReference, Long>{
    Optional<BookingReference> findByReferenceNo(String referenceNo);
}
