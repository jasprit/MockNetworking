package com.mocknetworking.model

data class LoginData(
    val isSuccessful: Boolean? = null,
    var username: String? = null
)

object LoginDataService {
    var loginData: LoginData? = null
}

