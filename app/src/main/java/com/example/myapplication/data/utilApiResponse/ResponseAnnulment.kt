package com.example.myapplication.data.utilApiResponse

import com.google.gson.annotations.SerializedName

class ResponseAnnulment (
    @SerializedName("statusCode"        ) var statusCode        : String? = null,
    @SerializedName("statusDescription" ) var statusDescription : String? = null
)