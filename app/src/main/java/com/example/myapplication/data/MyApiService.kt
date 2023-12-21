package com.example.myapplication.data

import com.example.myapplication.data.utilApiResponse.RequestAnnulment
import com.example.myapplication.data.utilApiResponse.RequestAuthorization
import com.example.myapplication.data.utilApiResponse.ResponseAnnulment
import com.example.myapplication.data.utilApiResponse.ResponseAuthorization
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Header


interface MyApiService {
    //Call POST of Authorization
    @POST("api/payments/authorization")
   suspend fun authorization(
        @Header("Authorization") authorizationKey: String,
        @Body requestAuthorization: RequestAuthorization
    ): ResponseAuthorization

    @POST("api/payments/annulment")
   suspend fun annulment(
        @Header("Authorization") annulmentKey: String,
        @Body requestAnnulment: RequestAnnulment
   ): ResponseAnnulment
}


