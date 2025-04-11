package com.example.notificationservice.controllers;

import com.example.notificationservice.dtos.NotificationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @PostMapping
    public ResponseEntity<Void> sendNotification(@RequestBody NotificationDTO dto) {
        // Log / Process the notification
        System.out.println("Notification received: " + dto);
        return ResponseEntity.ok().build();
    }
}
