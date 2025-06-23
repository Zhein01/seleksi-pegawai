package com.example.tes.admin.Lowongan

import java.io.Serializable

data class Modelpendaftar(
    val id: Int,
    val user_id: Int,
    val lowongan_id: Int,
    val nama: String,
    val email: String,
    val telepon: String,
    val pendidikan: String,
    val cv: String?,
    val status: String,
    val user: User
) : Serializable
