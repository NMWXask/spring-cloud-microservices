package notification.service.controller;

import notification.service.dto.NotificationRequest;
import notification.service.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest notificationRequest) {
        try {
            notificationService.sendNotification(
                    notificationRequest.message(),
                    notificationRequest.person(),
                    notificationRequest.type()
            );
            return ResponseEntity.ok("Уведомление отправлено успешно.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при отправке уведомления." + e.getMessage());
        }
    }
}