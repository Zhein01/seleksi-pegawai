package com.example.tes.admin.User

import com.example.tes.admin.Lowongan.ModelHasilSeleksi

data class ModelDaftarLamaran(
    val id: Int,
    val user_id: Int,
    val lowongan_id: Int,
    val status: String,
    val lowongan: Lowongan,
    val sudah_mengerjakan: Boolean
)

data class HasilSeleksiResponse(
    val success: Boolean,
    val message: String,
    val data: List<ModelHasilSeleksi>
)



