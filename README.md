# microservices
### Зачетный проект на курсе Otus Разработчик Spring Framework
## Стек технологий :
1. java 17
2. Kotlin 1.8.10 
3. Spring Cloud (Config Server, Discovery Server (Eureka), API Gateway Server)
4. Spring Data Jpa 
5. Spring Boot 
6. Spring Security(JWT) 
7. Kafka 
8. Feign Client
9. Map Struct
10. Postgresql
11. H2
12. Hibernate
13. Spring Boot Test
14. JUnit, Mockito
15. Rest Template
16. Spring Web
17. Spring Boot Actuator (Micrometer)
18. Prometheus
19. Grafana

### Компоненты приложения :
1. Config Service - удаленный репозиторий для хранения конфигурация бизнес - сервисов.
2. Eureka - Server - Service Discovery для регистрации основных сервисов приложения.
3. Gateway - Service - сервис - единая точка входа в приложение (Валидация JWT токена).
4. Identity - Service - Аутентификация\Авторизация приложения.
5. Booking-Service - сервис для обработки поступающих заказов на бронирование билетов с последующей передачей заказа
в сервис (Ticket-service).
6. Ticket - Service - сервис для обработки заказов на бронирование билетов, заполнение информации по заказу и отправка
информации в сервис уведомления.
7. Artist - Service - сервис содержит в себе актуальную информацию выступающих артистов.
8. Notification - Service - сервис для отправки уведомлений клиенту о номере заказа клиента.

### Информация о бизнес - компонентакх приложения :
1. Booking-Service - сервис принимает заказ от клиента, посредством Kafka брокера передает событие заказа Ticket - service.
2. Ticket - Service - принимает событие заказа Booking-Service, бронирует номер билета и место, так же обращается 
в Artist - Service для заполнения информации для билета по выступающему артисту, после чего отправляет запрос на 
отправку уведомления в Notification - Service.
3. Artist - Service - содержит в себе данные о выступающих артистах и передает эти данные по запросу Ticket - Service.
4. Notification - Service - принимает запрос от Ticket - Service и отправляет уведомление клиенту с основной информацией
по заказу. Для определения вида уведомления (EMAIL, PUSH) применяется паттерн проектирования Стратегия.

### Дополнительная информация :
Сбор базовых и прикладных метрик организован с помощью Spring Boot Actuator (Micrometer) Java Annotation, Spring AOP
поставка метрик в Prometheus, визуализация Grafana (Сервисы развернуты в контейнерах с помощью docker - compose)