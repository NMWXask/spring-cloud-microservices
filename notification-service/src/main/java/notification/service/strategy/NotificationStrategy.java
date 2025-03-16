package notification.service.strategy;

import notification.service.model.Person;

public interface NotificationStrategy {
    void sendNotification(String message, Person person);
}
