package ru.mcr.artistservice.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.mcr.artistservice.dto.ArtistResponseDto;
import ru.mcr.artistservice.entity.Artist;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface ArtistMapper {

    ArtistResponseDto fromEntityToArtistResponseDto(Artist artist);
}
