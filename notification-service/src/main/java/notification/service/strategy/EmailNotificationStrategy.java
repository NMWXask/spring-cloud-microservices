package notification.service.strategy;

import notification.service.annotation.NotificationTypeAnnotation;
import notification.service.enums.NotificationType;
import notification.service.model.Person;
import org.springframework.stereotype.Service;

@Service
@NotificationTypeAnnotation(NotificationType.EMAIL)
public class EmailNotificationStrategy implements NotificationStrategy {
    @Override
    public void sendNotification(String message, Person person) {
        System.out.println("Отправка email уведомления : " + message);
        System.out.println("Получатель :" + person.getName() + ", устройство :"+ person.getDeviceId());
    }
}
