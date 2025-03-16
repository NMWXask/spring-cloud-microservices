package ru.mcr.bookingservice.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO для создания заказа.
 * Пример JSON:
 * <pre>
 * {
 *     "ticketId": 1,
 *     "userId": 2,
 *     "artistId": 3,
 *     "seat": "A1"
 * }
 * </pre>
 *
 * @author Mikhaylov N.
 */

@Builder
@Data
public class CreateOrderDto implements Serializable {
    private Long ticketId;
    private Long userId;
    private Long artistId;
    private String seat;
}