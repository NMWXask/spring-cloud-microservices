package ru.mcr.core.event.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Событие, представляющее создание заказа.
 *
 * <p>Этот класс используется для передачи информации о созданном заказе.</p>
 *
 * <p>Пример JSON:
 * <pre>
 * {
 *     "orderId": "12345",
 *     "orderDate": "2023-01-01T12:00:00",
 *     "ticketId": 1,
 *     "userId": 2,
 *     "artistId": 3,
 *     "seat": "A1"
 * }
 * </pre>
 * </p>
 *
 * @author Mikhaylov N.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent implements Serializable {

    /**
     * Идентификатор заказа.
     */
    private String orderId;

        /**
     * Идентификатор билета.
     */
    private Long ticketId;

    /**
     * Идентификатор пользователя.
     */
    private Long userId;

    /**
     * Идентификатор артиста.
     */
    private Long artistId;

    /**
     * Место.
     */
    private String seat;

}