package karvio.service;

import karvio.entity.UserDevice;
import karvio.repository.UserDeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDeviceService {

    private final UserDeviceRepository userDeviceRepository;

    public UserDeviceService(UserDeviceRepository userDeviceRepository) {
        this.userDeviceRepository = userDeviceRepository;
    }

    @Transactional
    public void registerToken(String fcmToken, Long userId) {

        List<UserDevice> existingDevices = userDeviceRepository.findByFcmToken(fcmToken);

        if (!existingDevices.isEmpty()) {
            UserDevice device = existingDevices.getFirst();

            if (existingDevices.size() > 1) {
                userDeviceRepository.deleteAll(existingDevices.subList(1, existingDevices.size()));
            }

            if (!device.getUserId().equals(userId)) {
                device.setUserId(userId);
                userDeviceRepository.save(device);
            }

        } else {
            userDeviceRepository.save(UserDevice.builder()
                    .fcmToken(fcmToken)
                    .userId(userId)
                    .build());
        }
    }

    @Transactional
    public void deleteToken(String fcmToken) {
        userDeviceRepository.deleteAll(userDeviceRepository.findByFcmToken(fcmToken));
    }
}
