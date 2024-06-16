package com.akmal.wordpress.repositories

sealed class ApiResponse<B>(var data: B? = null, var errorMassage: String? = null) {
    class Loading<B>(): ApiResponse<B>()
    class Okay<B>(private val oData: B): ApiResponse<B>(data = oData)
    class Error<B>(private val error: String): ApiResponse<B>(errorMassage = error)
}