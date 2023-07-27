package com.tianjun.tls_tkb.domain.model


data class LoginToken(
    val accessToken: String,
    val tokenType: String,
    val refreshToken: String,
    val expiresIn: String,
    val scope: String
)