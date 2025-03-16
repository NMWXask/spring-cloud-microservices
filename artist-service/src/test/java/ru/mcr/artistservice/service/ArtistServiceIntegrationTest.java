package ru.mcr.artistservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mcr.artistservice.dto.ArtistResponseDto;
import ru.mcr.artistservice.exception.ArtistNotFountException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ArtistServiceIntegrationTest {

    @Autowired
    private ArtistService artistService;

    @Test
    void testFindById_ArtistExists() {
        long artistId = 1L;

        ArtistResponseDto result = artistService.findById(Long.toString(artistId));

        assertNotNull(result);
        assertEquals("Limp Bizkits", result.name());
        assertEquals("Alternative", result.genre());
        assertEquals("2024-12-12T20:00", result.performanceTime());
    }

    @Test
    void testFindById_ArtistNotFound() {
        long artistId = 999L;

        ArtistNotFountException exception = assertThrows(ArtistNotFountException.class, () -> {
            artistService.findById(Long.toString(artistId));
        });

        assertEquals("Artist not found with id: " + artistId, exception.getMessage());
    }
}