package karvio.service;

import karvio.repository.UserDeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDeviceService {

    private final UserDeviceRepository userDeviceRepository;

    public UserDeviceService(UserDeviceRepository userDeviceRepository) {
        this.userDeviceRepository = userDeviceRepository;
    }

    @Transactional
    public void registerToken(String fcmToken, Long userId) {

        boolean alreadyExists = userDeviceRepository.existsByFcmTokenAndUserId(fcmToken, userId);
        if (alreadyExists) return;

        userDeviceRepository.upsertFcmToken(fcmToken, userId);
    }

    @Transactional
    public void deleteToken(String fcmToken) {
        userDeviceRepository.deleteAll(userDeviceRepository.findByFcmToken(fcmToken));
    }
}
