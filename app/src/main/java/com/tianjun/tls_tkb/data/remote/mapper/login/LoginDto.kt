package com.tianjun.tls_tkb.data.remote.mapper.login

import com.tianjun.tls_tkb.domain.model.Login

data class LoginDto(
    val username: String,
    val password: String
){
    companion object{
        fun dtoToLogin(dto : LoginDto) = Login(
            dto.username,
            dto.password
        )
        fun loginToDto(login: Login) = LoginDto(
            login.username,
            login.password
        )
    }
}