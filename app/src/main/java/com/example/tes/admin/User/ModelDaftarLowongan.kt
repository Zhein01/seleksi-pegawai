package com.example.tes.admin.User

data class ModelDaftarLowongan(
    val id: Int,
    val nama: String,
    val perusahaan: String,
    val lokasi: String,
    val periode: String,
    val deskripsi: String,
    val kualifikasi: String,
    val batch_soal_id: Int?
)