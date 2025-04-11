package com.example.bookingservice.services;

import com.example.bookingservice.dtos.BookEventRequestDTO;
import com.example.bookingservice.dtos.NotificationDTO;
import com.example.bookingservice.dtos.PaymentRequestDTO;
import com.example.bookingservice.dtos.PaymentResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.retry.annotation.Retry;

@Slf4j
@Service
public class BookingService {
    @Autowired
    private RestTemplate restTemplate;

    private final String EVENT_URL = "http://localhost:8081/rakbank/reserve/";
    private final String PAYMENT_URL = "http://localhost:8083/payments";

    @Retry(name = "eventService", fallbackMethod = "fallbackReserve")
    public boolean reserveTickets(Long eventId, int quantity) {
        String url = EVENT_URL + eventId + "?quantity=" + quantity;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Sending an empty JSON body
        HttpEntity<Boolean> requestEntity = new HttpEntity<>(Boolean.TRUE, headers);

        ResponseEntity<Boolean> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Boolean.class
        );
        return response.getBody();
    }

    public boolean fallbackReserve(Long eventId, int quantity, Exception e) {
        System.out.println("Fallback for reserve called");
        return false;
    }
    public boolean bookAndPay(BookEventRequestDTO bookEventRequestDTO) {

        if(!reserveTickets(bookEventRequestDTO.getEventId(), bookEventRequestDTO.getQuantity())) {
            log.error("Reserve tickets failed");
            return false;
        }

        PaymentRequestDTO request = new PaymentRequestDTO();
        request.setBookingId(bookEventRequestDTO.getBookingId());
        request.setAmount(bookEventRequestDTO.getTotalAmount());

        ResponseEntity<PaymentResponseDTO> response =
                restTemplate.postForEntity("http://localhost:8083/payments", request, PaymentResponseDTO.class);

        log.info("response from payment service {}",response.getBody().toString());

        return response.getBody() != null && "SUCCESS".equals(response.getBody().getStatus());
    }

    public void sendNotification(NotificationDTO dto) {
        String url = "http://localhost:8084/notifications";
        restTemplate.postForEntity(url, dto, Void.class);
    }
}
