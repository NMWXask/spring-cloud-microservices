package ru.mcr.ticketservice.exception

class NonRetryableException: RuntimeException {
    constructor(message: String?) : super(message)
    constructor(cause: Throwable) : super(cause)
}