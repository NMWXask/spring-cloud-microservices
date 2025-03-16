package notification.service.service;

import notification.service.annotation.MeasureTime;
import notification.service.annotation.Monitor;
import notification.service.enums.NotificationType;
import notification.service.model.Person;
import notification.service.strategy.NotificationStrategy;
import notification.service.strategy.NotificationStrategyResolver;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {

    private final NotificationStrategyResolver strategyResolver;

    private static final  String METRIC_NAME = "notification_service_requests_total";

    public NotificationService(NotificationStrategyResolver strategyResolver) {
        this.strategyResolver = strategyResolver;
    }

    @Monitor(metricName = METRIC_NAME)
    @MeasureTime
    public void sendNotification(String message, Person person, NotificationType type) {
        NotificationStrategy strategy = strategyResolver.resolve(type);//определяем тип стратегии с помощью декоратора
        strategy.sendNotification(message, person);//отправляем уведомление в зависимости от типа
    }
}
