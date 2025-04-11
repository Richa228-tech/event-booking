package com.example.eventservice.dtos;

import lombok.Data;

@Data
public class EventDTO {
    private String name;
    private String category;
    private String location;
    private Integer totalNoOfTickets;
    private String dateOfEvent;
}
