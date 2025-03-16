package notification.service.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import notification.service.enums.NotificationType;
import notification.service.model.Person;

public record NotificationRequest(String message,
                                  Person person,
                                  @JsonProperty("notificationType") NotificationType type) {
}
