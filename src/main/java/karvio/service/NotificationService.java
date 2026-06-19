package karvio.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import karvio.dto.request.NotificationRequest;
import karvio.dto.response.NotificationResponse;
import karvio.mapper.NotificationMapper;
import karvio.repository.NotificationRepository;
import karvio.repository.UserDeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final NotificationMapper notificationMapper;

    public List<NotificationResponse> notifications(Long userId) {
        return notificationMapper.toResponseList(notificationRepository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    @Transactional
    public void saveAndSendPush(NotificationRequest dto) {

        notificationRepository.save(notificationMapper.toEntity(dto));

        List<String> userTokens = userDeviceRepository.findFcmTokensByUserId(dto.userId());

        if (userTokens.isEmpty()) {
            System.out.println("The user " + dto.userId() + " has no devices registered.");
        }

        for (String token : userTokens) {
            sendFcmMessage(token, dto.title(), dto.body());
        }
    }

    private void sendFcmMessage(String token, String title, String body) {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            System.err.println("Eroare la trimiterea Firebase pt token-ul: " + token + " -> " + e.getMessage());
            // Opțional: dacă eroarea spune că token-ul e invalid, îl poți șterge din DB aici
        }
    }
}
