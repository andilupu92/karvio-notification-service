package karvio.controller;

import karvio.dto.request.NotificationRequest;
import karvio.dto.response.NotificationResponse;
import karvio.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping()
    public ResponseEntity<List<NotificationResponse>> notifications(@RequestHeader("X-User-Id") Long userId) {
        return new ResponseEntity<>(notificationService.notifications(userId), HttpStatus.OK);
    }

    @PostMapping("/saveAndSendNotification")
    public ResponseEntity<Void> handleInternalNotification(@RequestBody NotificationRequest dto) {
        notificationService.saveAndSendPush(dto);
        return ResponseEntity.ok().build();
    }

}
