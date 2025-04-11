package com.example.bookingservice.controllers;

import com.example.bookingservice.dtos.BookEventRequestDTO;
import com.example.bookingservice.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rakbank")
public class BookingController {

        @Autowired
        private BookingService service;

        @PostMapping("/book")
        public ResponseEntity<?> createBooking(@RequestBody BookEventRequestDTO req) {
            return ResponseEntity.ok(service.bookAndPay(req));
        }
    }