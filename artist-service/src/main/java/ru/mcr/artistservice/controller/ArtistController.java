package ru.mcr.artistservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mcr.artistservice.dto.ArtistResponseDto;
import ru.mcr.artistservice.service.ArtistService;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDto> getArtist(@PathVariable String id) {
        return new ResponseEntity<>(artistService.findById(id), HttpStatus.OK);
    }
}
