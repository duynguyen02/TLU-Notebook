package com.tianjun.tls_tkb.data.remote.api

import com.tianjun.tls_tkb.data.remote.mapper.login.LoginDto
import com.tianjun.tls_tkb.data.remote.mapper.login.LoginTokenDto
import com.tianjun.tls_tkb.data.remote.mapper.semester.SemesterInfoDto
import com.tianjun.tls_tkb.data.remote.mapper.subject.SubjectInfoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface StudentService {

    @Headers(
        "Accept: application/json, text/plain, */*",
        "Accept-Language: en-US,en;q=0.9",
        "Connection: keep-alive",
        "Sec-Fetch-Dest: empty",
        "Sec-Fetch-Mode: cors",
        "Sec-Fetch-Site: same-site",
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36",
        "sec-ch-ua: \"Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"115\", \"Chromium\";v=\"115\"",
        "sec-ch-ua-mobile: ?0",
        "sec-ch-ua-platform: \"Linux\""
    )
    @POST("education/public/login/ext/loginnew")
    suspend fun login(@Body request: LoginDto): Response<LoginTokenDto>

    @Headers(
        "Accept: application/json, text/plain, */*",
        "Accept-Language: en-US,en;q=0.9",
        "Connection: keep-alive",
        "Sec-Fetch-Dest: empty",
        "Sec-Fetch-Mode: cors",
        "Sec-Fetch-Site: same-site",
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36",
        "sec-ch-ua: \"Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"115\", \"Chromium\";v=\"115\"",
        "sec-ch-ua-mobile: ?0",
        "sec-ch-ua-platform: \"Linux\""
    )
    @GET("/education/api/semester/semester_info")
    suspend fun getSemesterInfo(
        @Header("Authorization") authorization : String,
        @Header("Origin") origin : String,
        @Header("Referer") referer : String
    ) : Response<SemesterInfoDto>

    @Headers(
        "Accept: application/json, text/plain, */*",
        "Accept-Language: en-US,en;q=0.9",
        "Connection: keep-alive",
        "Sec-Fetch-Dest: empty",
        "Sec-Fetch-Mode: cors",
        "Sec-Fetch-Site: same-site",
        "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36",
        "sec-ch-ua: \"Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"115\", \"Chromium\";v=\"115\"",
        "sec-ch-ua-mobile: ?0",
        "sec-ch-ua-platform: \"Linux\""
    )
    @GET("/education/api/StudentCourseSubject/studentLoginUser/{semester}")
    suspend fun getSubjects(
        @Path("semester") semester : String,
        @Header("Authorization") authorization : String,
        @Header("Origin") origin : String,
        @Header("Referer") referer : String
    ) : Response<List<SubjectInfoDto>>

}