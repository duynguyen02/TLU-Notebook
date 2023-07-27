package com.tianjun.tls_tkb.data.remote.mapper.login

import com.google.gson.annotations.SerializedName
import com.tianjun.tls_tkb.domain.model.LoginToken


data class LoginTokenDto(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("expires_in") val expiresIn: String,
    val scope: String
){
    companion object {
        fun dtoToLoginToken(dto : LoginTokenDto) = LoginToken(
            dto.accessToken,
            dto.tokenType,
            dto.refreshToken,
            dto.expiresIn,
            dto.scope
        )
        fun loginTokenToDto(loginToken: LoginToken) = LoginTokenDto(
            loginToken.accessToken,
            loginToken.tokenType,
            loginToken.refreshToken,
            loginToken.expiresIn,
            loginToken.scope
        )
    }
}
