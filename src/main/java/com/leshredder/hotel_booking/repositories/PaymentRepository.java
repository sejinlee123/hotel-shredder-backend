package com.leshredder.hotel_booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leshredder.hotel_booking.entities.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>{
    
}
