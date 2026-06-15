package karvio.repository;

import karvio.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    List<UserDevice> findByFcmToken(String fcmToken);

    @Query("SELECT ud.fcmToken FROM UserDevice ud WHERE ud.userId = :userId")
    List<String> findFcmTokensByUserId(@Param("userId") Long userId);
}
