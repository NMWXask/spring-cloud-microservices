# Microservices
### Зачетный проект на курсе Otus Разработчик Spring Framework

## Стек технологий

- **Java 17**
- **Kotlin 1.8.10**
- **Spring Cloud**:
    - Config Server
    - Discovery Server (Eureka)
    - API Gateway Server
- **Spring Data JPA**
- **Spring Boot**
- **Spring Security (JWT)**
- **Kafka**
- **Feign Client**
- **MapStruct**
- **PostgreSQL**
- **H2**
- **Hibernate**
- **Spring Boot Test**
- **JUnit, Mockito**
- **RestTemplate**
- **Spring Web**
- **Spring Boot Actuator (Micrometer)**
- **Prometheus**
- **Grafana**

## Компоненты приложения

1. **Config Service** - удаленный репозиторий для хранения конфигураций бизнес-сервисов.
2. **Eureka Server** - Service Discovery для регистрации основных сервисов приложения.
3. **Gateway Service** - сервис, единая точка входа в приложение (валидация JWT токена).
4. **Identity Service** - аутентификация и авторизация приложения.
5. **Booking Service** - сервис для обработки заказов на бронирование билетов с последующей передачей заказа в Ticket Service.
6. **Ticket Service** - сервис для обработки заказов на бронирование билетов, заполнение информации по заказу и отправка информации в Notification Service.
7. **Artist Service** - сервис, содержащий актуальную информацию о выступающих артистах.
8. **Notification Service** - сервис для отправки уведомлений клиенту о номере заказа.

## Информация о бизнес-компонентах приложения

1. **Booking Service** - принимает заказ от клиента, посредством Kafka брокера передает событие заказа в Ticket Service.
2. **Ticket Service** - принимает событие заказа от Booking Service, бронирует номер билета и место, обращается в Artist Service для заполнения информации по выступающему артисту, после чего отправляет запрос на отправку уведомления в Notification Service.
3. **Artist Service** - содержит данные о выступающих артистах и передает эти данные по запросу Ticket Service.
4. **Notification Service** - принимает запрос от Ticket Service и отправляет уведомление клиенту с основной информацией по заказу. Для определения вида уведомления (EMAIL, PUSH) применяется паттерн проектирования Стратегия.
5. **Identity Service** - выполняет функцию регистрации пользователя в приложении и выдачи ему JWT токена, а также аутентификации уже зарегистрированного пользователя с выдачей токена.

## Дополнительная информация

Сбор базовых и прикладных метрик организован с помощью Spring Boot Actuator (Micrometer), Java Annotation, Spring AOP. Поставка метрик в Prometheus, визуализация в Grafana. Сервисы развернуты в контейнерах с помощью Docker Compose.

## Коллекция запросов Postman

[postman-collection/Microservice.postman_collection.json](postman-collection/Microservice.postman_collection.json)

## Локальный запуск

1. Убедитесь, что у вас установлены следующие инструменты:
    - [Kafka](https://kafka.apache.org/quickstart)
    - [Docker](https://www.docker.com/get-started)
    - [Docker Compose](https://docs.docker.com/compose/install/)

2. Запускаем сервисы в соответствии с порядком инициализации конфигураций:
    - Config Service
    - Eureka Server
    - Artist Service
    - Ticket Service
    - Booking Service
    - Notification Service
    - Identity Service
    - Gateway Service

3. Выполните команду `docker-compose up` для запуска Prometheus и Grafana.

Prometheus: [http://localhost:9090](http://localhost:9090)  
Grafana: [http://localhost:4000](http://localhost:4000)

## Настройка Grafana

### Откройте Grafana:

1. Перейдите в браузере по адресу [http://localhost:4000](http://localhost:4000).
2. Войдите в систему с именем пользователя `admin` и паролем `admin`.

### Добавьте источник данных Prometheus:

1. Перейдите в "Configuration" -> "Data Sources".
2. Нажмите "Add data source".
3. Выберите "Prometheus".
4. В поле URL введите `http://prometheus:9090`.
5. Нажмите "Save & Test".

### Создайте дашборд:

1. Перейдите в "Create" -> "Dashboard".
2. Нажмите "Add new panel".
3. В поле "Metrics" введите `client_account_requests_total`.
4. Настройте график по вашему усмотрению.
5. Сохраните дашборд.

### Пример конфигурации дашборда:

1. Добавление панели:
    - В поле "Query" введите `metric_name_requests_total`.
    - Настройте визуализацию (например, график или счетчик).
2. Сохранение дашборда:
    - Нажмите "Save dashboard".
    - Дайте имя вашему дашборду и нажмите "Save".