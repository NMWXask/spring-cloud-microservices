package notification.service.strategy;

import notification.service.annotation.NotificationTypeAnnotation;
import notification.service.enums.NotificationType;
import notification.service.exception.IllegalStrategyException;
import notification.service.exception.NotFoundStrategyException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NotificationStrategyResolver {

    private final Map<NotificationType, NotificationStrategy> strategies;

    public NotificationStrategyResolver(List<NotificationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        strategy -> {
                            NotificationTypeAnnotation annotation = strategy.getClass()
                                    .getAnnotation(NotificationTypeAnnotation.class);
                            if (annotation == null) {
                                throw new IllegalStrategyException("Strategy is not annotated with @NotificationTypeAnnotation: " + strategy.getClass());
                            }
                            return annotation.value();
                        },
                        strategy -> strategy
                ));
    }

    public NotificationStrategy resolve(NotificationType type) {
        NotificationStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new NotFoundStrategyException("No strategy for type: " + type);
        }
        return strategy;
    }
}
