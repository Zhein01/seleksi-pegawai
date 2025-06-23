package com.example.tes.admin.soal

import com.example.tes.admin.ModelBatch
import com.example.tes.admin.ModelSoal

data class GetResponseSoal(
    val success: Boolean,
    val message: String,
    val data: List<ModelSoal>
)