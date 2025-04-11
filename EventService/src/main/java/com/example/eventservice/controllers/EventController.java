package com.example.eventservice.controllers;

import com.example.eventservice.dtos.EventDTO;
import com.example.eventservice.services.EventService;
import com.sun.jdi.request.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/rakbank")
public class EventController {
    @Autowired
    private EventService eventService;
    @PostMapping("/eventCreateRequest")
    public ResponseEntity<?> addEvent(@RequestBody EventDTO eventRequest) {
        try{
            eventService.addEvent(eventRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/reserve/{eventId}")
    public ResponseEntity<Boolean> reserve(@PathVariable Long eventId, @RequestParam int quantity) {
        try {

            return ResponseEntity.ok(eventService.reserveTickets(eventId, quantity));
        }
        catch (Exception e){
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
        }
    }
}
