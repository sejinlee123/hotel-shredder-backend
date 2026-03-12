package com.leshredder.hotel_booking.services.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.leshredder.hotel_booking.dtos.NotificationDTO;
import com.leshredder.hotel_booking.entities.Notification;
import com.leshredder.hotel_booking.enums.NotificationType;
import com.leshredder.hotel_booking.repositories.NotificationRepository;
import com.leshredder.hotel_booking.services.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService{
    
    private final JavaMailSender javaMailSender;
    private final NotificationRepository notificationRepository;
    
    //Kafka message que for bigger service
    @Override
    @Async
    public void sendEmail(NotificationDTO notificationDTO) {
        log.info("Inside send email");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(notificationDTO.getRecipient());
        simpleMailMessage.setSubject(notificationDTO.getSubject());
        simpleMailMessage.setText(notificationDTO.getBody());

        javaMailSender.send(simpleMailMessage);

        //SAVE TO DB
        Notification notificationToSave = Notification.builder()
            .recipient(notificationDTO.getRecipient())
            .subject(notificationDTO.getSubject())
            .body(notificationDTO.getBody())
            .bookingReference(notificationDTO.getBookingReference())
            .type(NotificationType.EMAIL)
            .build();
        
        notificationRepository.save(notificationToSave);
    }

    @Override
    public void sendSMS() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendSMS'");
    }

    @Override
    public void sendWhatsApp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendWhatsApp'");
    }

}
