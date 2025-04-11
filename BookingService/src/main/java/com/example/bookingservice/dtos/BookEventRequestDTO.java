package com.example.bookingservice.dtos;

import lombok.Data;

@Data
public class BookEventRequestDTO {
    private Long bookingId;

    private Long userId;
    private Long eventId;
    private String ticketType;
    private Integer quantity;
    private Double totalAmount;
}