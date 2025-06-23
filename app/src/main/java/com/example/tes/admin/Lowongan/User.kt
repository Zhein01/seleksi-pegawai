package com.example.tes.admin.Lowongan


import java.io.Serializable

data class User(
    val id: Int,
    val nik: String,
    val nama: String,
    val alamat: String,
    val jkl: String,
    val no_hp: String,
    val username: String,
    val created_at: String,
    val updated_at: String
) : Serializable
