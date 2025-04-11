package com.example.bookingservice.repositories;

import com.example.bookingservice.entities.BookingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEvent, Long> {
}
