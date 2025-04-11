package com.example.bookingservice.dtos;

import lombok.Data;

@Data
public class NotificationDTO {
    private String eventName;
    private String eventDate;
    private String eventLocation;
    private String userName;
    private String ticketType;
    private int numberOfTickets;
    private double paymentAmount;
}
