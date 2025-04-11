package com.example.eventservice.services;

import com.example.eventservice.dtos.EventDTO;
import com.example.eventservice.entities.Event;
import com.example.eventservice.enums.CategoryType;
import com.example.eventservice.repositories.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public void addEvent(EventDTO eventRequest) throws Exception{
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(eventRequest.getDateOfEvent(), formatter);
            Date dateOfEvent = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Event event = Event.builder()
                    .eventDate(dateOfEvent)
                    .eventName(eventRequest.getName())
                    .category(
                            eventRequest.getCategory().equalsIgnoreCase("business") ? CategoryType.Business
                                    : (eventRequest.getCategory().equalsIgnoreCase("nonprofit") ? CategoryType.NonProfit
                                    : (eventRequest.getCategory().equalsIgnoreCase("entertainment") ? CategoryType.Entertainment : CategoryType.CategoryNotPresent))
                    )
                    .totalNoOfSeats(eventRequest.getTotalNoOfTickets())
                    .build();

            eventRepository.save(event);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Transactional
    public boolean reserveTickets(Long eventId, int quantity) throws Exception{
        Event event = eventRepository.findByIdForUpdate(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getNoOfSeatsAvailable() < quantity) {
            return false; // No exception thrown here
        }

        event.setNoOfSeatsAvailable(event.getNoOfSeatsAvailable() - quantity);
        eventRepository.save(event);
        return true;

    }
}
