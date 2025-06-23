package com.example.tes.admin.User

import LamaranResponse
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tes.R
import com.example.tes.admin.ApiClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class FormulirActivity : AppCompatActivity() {

    private lateinit var edtNama: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtTelepon: EditText
    private lateinit var edtPendidikan: EditText
    private lateinit var btnUploadCV: Button
    private lateinit var btnKirim: Button
    private lateinit var btnKembali: Button

    private var selectedCvFile: File? = null
    private val PICK_PDF_REQUEST = 1001
    private var lowonganId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mendaftar_lowongan)

        edtNama = findViewById(R.id.edtNama)
        edtEmail = findViewById(R.id.edtEmail)
        edtTelepon = findViewById(R.id.edtTelepon)
        edtPendidikan = findViewById(R.id.edtPendidikan)
        btnUploadCV = findViewById(R.id.btnUploadCV)
        btnKembali = findViewById(R.id.btnKembali3)
        btnKirim = findViewById(R.id.btnKirim)

        lowonganId = intent.getIntExtra("lowongan_id", -1)

        if (lowonganId == -1) {
            Toast.makeText(this, "Lowongan ID tidak ditemukan", Toast.LENGTH_LONG).show()
            Log.e("FormulirActivity", "LOWONGAN_ID tidak dikirim dari intent")
            finish()
            return
        }

        btnKembali.setOnClickListener { finish() }

        btnUploadCV.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(Intent.createChooser(intent, "Pilih File CV"), PICK_PDF_REQUEST)
        }

        fun kirimLamaran(userId: Int, nama: String, email: String, telepon: String, pendidikan: String) {
            val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(), selectedCvFile!!)
            val body = MultipartBody.Part.createFormData("cv", selectedCvFile!!.name, requestFile)
            val userIdRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), userId.toString())
            val lowonganIdRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), lowonganId.toString())
            val namaRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), nama)
            val emailRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
            val teleponRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), telepon)
            val pendidikanRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), pendidikan)

            ApiClient.instance.kirimLamaran(
                userIdRequest, lowonganIdRequest, namaRequest, emailRequest,
                teleponRequest, pendidikanRequest, body
            ).enqueue(object : Callback<LamaranResponse> {
                override fun onResponse(call: Call<LamaranResponse>, response: Response<LamaranResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@FormulirActivity, "Lamaran berhasil dikirim", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@FormulirActivity, "Gagal mengirim lamaran", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LamaranResponse>, t: Throwable) {
                    Toast.makeText(this@FormulirActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        btnKirim.setOnClickListener {
            val nama = edtNama.text.toString()
            val email = edtEmail.text.toString()
            val telepon = edtTelepon.text.toString()
            val pendidikan = edtPendidikan.text.toString()

            if (nama.isEmpty() || email.isEmpty() || telepon.isEmpty() || pendidikan.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedCvFile == null) {
                Toast.makeText(this, "Silakan pilih file CV terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
            val userId = sharedPref.getInt("user_id", -1)

            if (userId == -1) {
                Toast.makeText(this, "User belum login", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            ApiClient.instance.cekLamaran(userId, lowonganId)
                .enqueue(object : Callback<CekLamaranResponse> {
                    override fun onResponse(call: Call<CekLamaranResponse>, response: Response<CekLamaranResponse>) {
                        if (response.isSuccessful && response.body()?.sudah_melamar == true) {
                            Toast.makeText(this@FormulirActivity, "Anda sudah melamar lowongan ini.", Toast.LENGTH_LONG).show()
                        } else {
                            kirimLamaran(userId, nama, email, telepon, pendidikan)
                        }
                    }

                    override fun onFailure(call: Call<CekLamaranResponse>, t: Throwable) {
                        Toast.makeText(this@FormulirActivity, "Gagal mengecek status lamaran", Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val file = uriToFile(uri)
                selectedCvFile = file
                Toast.makeText(this, "CV berhasil dipilih: ${file.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun uriToFile(uri: Uri): File {
        val inputStream = contentResolver.openInputStream(uri)
        val file = File(cacheDir, "cv_upload.pdf")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return file
    }
}
