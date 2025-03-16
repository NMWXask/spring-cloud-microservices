CREATE TABLE IF NOT EXISTS tickets
(
    ticket_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id   BIGINT,
    artist_id BIGINT,
    seat      VARCHAR(255)
);