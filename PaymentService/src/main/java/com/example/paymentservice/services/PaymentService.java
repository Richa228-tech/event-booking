package com.example.paymentservice.services;

import com.example.paymentservice.dtos.PaymentRequestDTO;
import com.example.paymentservice.dtos.PaymentResponseDTO;
import com.example.paymentservice.entities.Payment;
import com.example.paymentservice.repositories.PaymentRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    private static final String GATEWAY_URL = "http://localhost:9090/mock-gateway/pay";

    @Retry(name = "paymentRetry", fallbackMethod = "fallbackPayment")
    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
        ResponseEntity<String> response = restTemplate.postForEntity(GATEWAY_URL, request, String.class);

        Payment payment = new Payment();
        payment.setBookingId(request.getBookingId());
        payment.setAmount(request.getAmount());
        payment.setTimestamp(LocalDateTime.now());

        if (response.getStatusCode().is2xxSuccessful()) {
            payment.setStatus("SUCCESS");
        } else {
            payment.setStatus("FAILED");
        }

        Payment saved = repository.save(payment);
        return new PaymentResponseDTO(saved.getId(), saved.getStatus());
    }

    public PaymentResponseDTO fallbackPayment(PaymentRequestDTO request, Throwable t) {
        Payment payment = new Payment();
        payment.setBookingId(request.getBookingId());
        payment.setAmount(request.getAmount());
        payment.setStatus("FAILED");
        payment.setTimestamp(LocalDateTime.now());
        Payment saved = repository.save(payment);
        return new PaymentResponseDTO(saved.getId(), "FAILED");
    }
}
