package com.mocknetworking.base

interface ApiResponseListener {
    fun <T> onResponse(it: T)
    fun onError(it: Throwable)
}