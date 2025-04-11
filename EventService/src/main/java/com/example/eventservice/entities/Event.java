package com.example.eventservice.entities;

import com.example.eventservice.enums.CategoryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "event_name")
    public String eventName;
    @Enumerated(EnumType.STRING)
    public CategoryType category;
    @Column(name = "date_of_event")
    public Date eventDate;
    @Column(name = "total_no_of_seats")
    public Integer totalNoOfSeats;
    @Column(name = "no_of_seats_available")
    public Integer noOfSeatsAvailable;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}