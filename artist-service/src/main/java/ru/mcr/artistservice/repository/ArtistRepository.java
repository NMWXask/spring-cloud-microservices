package ru.mcr.artistservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mcr.artistservice.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
