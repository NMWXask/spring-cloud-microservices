package ru.mcr.artistservice.exception;

public class ArtistNotFountException extends RuntimeException {
    public ArtistNotFountException(String message) {
        super(message);
    }
}
