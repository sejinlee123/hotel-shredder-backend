package com.leshredder.hotel_booking.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.leshredder.hotel_booking.enums.RoomType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomDTO {

    private Long id;

    private Integer roomNumber;


    private RoomType type;


    private BigDecimal pricePerNight;

    private Integer capacity;

    private String description;
    private String imageUrl;
}
