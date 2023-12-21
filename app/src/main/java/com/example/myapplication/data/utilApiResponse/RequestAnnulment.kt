package com.example.myapplication.data.utilApiResponse

import com.google.gson.annotations.SerializedName

class RequestAnnulment (
    @SerializedName("receiptId" ) var receiptId : String? = null,
    @SerializedName("rrn"       ) var rrn       : String? = null
)