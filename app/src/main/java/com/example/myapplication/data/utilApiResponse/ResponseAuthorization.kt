package com.example.myapplication.data.utilApiResponse

import com.google.gson.annotations.SerializedName

class ResponseAuthorization (
    @SerializedName("receiptId"         ) var receiptId         : String? = null,
    @SerializedName("rrn"               ) var rrn               : String? = null,
    @SerializedName("statusCode"        ) var statusCode        : String? = null,
    @SerializedName("statusDescription" ) var statusDescription : String? = null
)