package com.example.myapplication.data.utilApiResponse

import com.google.gson.annotations.SerializedName

class RequestAuthorization (
    @SerializedName("id"           ) var id           : String? = null,
    @SerializedName("commerceCode" ) var commerceCode : String? = null,
    @SerializedName("terminalCode" ) var terminalCode : String? = null,
    @SerializedName("amount"       ) var amount       : String? = null,
    @SerializedName("card"         ) var card         : String? = null
)