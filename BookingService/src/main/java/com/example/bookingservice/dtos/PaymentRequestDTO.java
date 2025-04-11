package com.example.bookingservice.dtos;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long bookingId;
    private double amount;
}
