package com.example.tes.admin.soal

import com.example.tes.admin.ModelBatch

data class GetResponeBatch(
    val success: Boolean,
    val message: String,
    val data: List<ModelBatch>
)

data class BatchNameResponse(
    val success: Boolean,
    val message: String,
    val data: ModelBatch?
)