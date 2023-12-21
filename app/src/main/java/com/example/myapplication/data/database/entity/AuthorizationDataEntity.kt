package com.example.myapplication.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class AuthorizationDataEntity (
    @PrimaryKey(autoGenerate = true) val ide: Long = 0,
    val id: String,
    val commerceCode: String,
    val terminalCode: String,
    val amount: String,
    val card: String,
    var receiptId: String,
    var rrn: String,
    var status: Boolean,
    var statusCode: String,
    var statusDescription: String
)