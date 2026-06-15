package karvio.repository;

import karvio.entity.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationHistory, Long> {

    List<NotificationHistory> findByUserIdOrderByCreatedAtDesc(Long userId);
}
