package com.example.tes.admin

data class SendResponse(

    val success: Boolean,
    val message: String

)

data class SendResponseHasil(
    val success: Boolean,
    val message: String,
    val data: DataHasil?
)

data class DataHasil(
    val nama: String,
    val skor: Int,
    val status: String
)