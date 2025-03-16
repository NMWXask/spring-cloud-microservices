package ru.mcr.ticketservice.exception

class RetryableException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(cause: Throwable) : super(cause)
}