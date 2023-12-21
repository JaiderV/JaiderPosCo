package com.example.myapplication.utils

sealed class Resource<out T>(val data: T? = null, val message: String? = null, val errorCode: String? = null ){

    class Success <T>(data: T): Resource<T>(data)

    class Error <T> (errorCode: String) : Resource<T>(errorCode = errorCode)

    class Loading <T> (data: T? = null) : Resource<T>(data)
}
