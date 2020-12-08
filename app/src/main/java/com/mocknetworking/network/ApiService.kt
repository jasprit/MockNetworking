package com.mocknetworking.network


import com.mocknetworking.model.LoginData
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/login")
    fun callLoginApi(@Body params: LinkedHashMap<String, Any?>): Observable<LoginData>

}