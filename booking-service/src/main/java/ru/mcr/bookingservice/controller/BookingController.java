package ru.mcr.bookingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mcr.bookingservice.dto.CreateOrderDto;
import ru.mcr.bookingservice.service.BookingService;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderDto orderDto) throws JsonProcessingException {
        bookingService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto.toString());
    }
}
