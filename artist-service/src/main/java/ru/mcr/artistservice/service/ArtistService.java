package ru.mcr.artistservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mcr.artistservice.annotation.Monitor;
import ru.mcr.artistservice.dto.ArtistResponseDto;
import ru.mcr.artistservice.entity.Artist;
import ru.mcr.artistservice.exception.ArtistNotFountException;
import ru.mcr.artistservice.mapper.ArtistMapper;
import ru.mcr.artistservice.repository.ArtistRepository;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    private static final  String METRIC_NAME = "artist_service_requests_total";

    @Monitor(metricName = METRIC_NAME)
    public ArtistResponseDto findById(String id) {
        Long artistId = Long.valueOf(id);
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFountException("Artist not found with id: " + id));
        return artistMapper.fromEntityToArtistResponseDto(artist);
    }
}
