package com.tianjun.tls_tkb.data.remote.api

import retrofit2.Response
import retrofit2.http.GET

interface ServerService {
    @GET("/application.js")
    suspend fun getServerConfigFile() : Response<String>
}