package com.example.tes.admin.User

data class RegisterResponse(
    val status: String,
    val message: String,
    val data: UserData
)

data class UserData(
    val id: Int,
    val nik: String,
    val nama: String,
    val alamat: String,
    val jkl: String,
    val no_hp: String,
    val username: String
)
