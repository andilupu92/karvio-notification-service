package karvio.dto.response;

public record NotificationResponse(Long id,
                                   Long userId,
                                   String title,
                                   String body,
                                   boolean isRead
) { }
