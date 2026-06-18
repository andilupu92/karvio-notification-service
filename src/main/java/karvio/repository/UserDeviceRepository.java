package karvio.repository;

import karvio.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    List<UserDevice> findByFcmToken(String fcmToken);
    boolean existsByFcmTokenAndUserId(String fcmToken, Long userId);

    @Query("SELECT ud.fcmToken FROM UserDevice ud WHERE ud.userId = :userId")
    List<String> findFcmTokensByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = """
    INSERT INTO user_device (fcm_token, user_id, created_at)
    VALUES (:fcmToken, :userId, NOW())
    ON CONFLICT (fcm_token)
    DO UPDATE SET user_id = :userId
    """, nativeQuery = true)
    void upsertFcmToken(@Param("fcmToken") String fcmToken, @Param("userId") Long userId);
}
