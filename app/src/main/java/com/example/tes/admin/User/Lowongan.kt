package com.example.tes.admin.User

import com.google.gson.annotations.SerializedName

data class Lowongan(
    val id: Int,
    val nama: String,
    val perusahaan: String,
    val lokasi: String,
    val periode: String,
    val deskripsi: String,
    val kualifikasi: String,
    val batch_soal_id: Int?
)