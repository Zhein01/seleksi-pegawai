package com.example.tes.admin.User


data class RegisterRequest(
    val nik: String,
    val nama: String,
    val alamat: String,
    val jkl: String,
    val no_hp: String,
    val tempat_lahir: String,
    val tgl_lahir: String,
    val username: String,
    val password: String
)
