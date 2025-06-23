data class LamaranResponse(
    val success: Boolean,
    val data: List<LamaranData>
)

data class LamaranData(
    val id: Int,
    val user_id: Int,
    val lowongan_id: Int,
    val nama: String,
    val email: String,
    val telepon: String,
    val pendidikan: String,
    val cv: String?,
    val lowongan: LowonganData?

)
data class LowonganData(
    val id: Int,
    val nama: String,
    val perusahaan: String,
    val lokasi: String,
    val deskripsi: String,
    val kualifikasi: String
)
