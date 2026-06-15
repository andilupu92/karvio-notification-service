package karvio.mapper;

import karvio.dto.request.NotificationRequest;
import karvio.dto.response.NotificationResponse;
import karvio.entity.NotificationHistory;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponse toResponse(NotificationHistory notificationHistory);

    @InheritConfiguration(name = "toResponse")
    List<NotificationResponse> toResponseList(List<NotificationHistory> notificationHistoryList);

    NotificationHistory toEntity(NotificationRequest notificationRequest);
    //void updateEntityFromRequest(DocumentRequest documentRequest, @MappingTarget Document document);
}
