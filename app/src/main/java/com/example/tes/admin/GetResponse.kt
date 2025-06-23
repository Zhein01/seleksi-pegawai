package com.example.tes.admin

import com.example.tes.admin.User.ModelDaftarLowongan

data class GetResponse(
    val success: Boolean,
    val data: List<ModelDaftarLowongan>
)
