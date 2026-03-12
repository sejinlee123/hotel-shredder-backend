package com.leshredder.hotel_booking.services;

import com.leshredder.hotel_booking.dtos.NotificationDTO;

public interface NotificationService {

    void sendEmail(NotificationDTO notificationDTO);

    void sendSMS();
    
    void sendWhatsApp();
}
