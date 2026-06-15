package karvio.controller;

import karvio.service.UserDeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-device")
public class UserDeviceController {

    private final UserDeviceService userDeviceService;

    public UserDeviceController(UserDeviceService userDeviceService) {
        this.userDeviceService = userDeviceService;
    }

    @PostMapping("/register-token/{fcmToken}")
    public ResponseEntity<Void> registerToken(@PathVariable String fcmToken,
                                              @RequestHeader("X-User-Id") Long userId) {
        userDeviceService.registerToken(fcmToken, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-token/{fcmToken}")
    public ResponseEntity<Void> deleteToken(@PathVariable String fcmToken) {
        userDeviceService.deleteToken(fcmToken);
        return ResponseEntity.noContent().build();
    }
}
