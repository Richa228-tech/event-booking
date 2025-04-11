package com.example.paymentservice.dtos;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private Long paymentId;
    private String status;

    public PaymentResponseDTO(Long paymentId, String status) {
        this.paymentId = paymentId;
        this.status = status;
    }
}
