package com.example.paymentservice.dtos;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long bookingId;
    private double amount;
}
