package dev.pegorari.rarehub.model


data class LoginResponse(
    val bearerToken: String?,
    val error: ErrorResponse?
)

