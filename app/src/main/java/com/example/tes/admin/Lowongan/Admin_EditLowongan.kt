package com.example.tes.admin.Lowongan

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.SendResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Admin_EditLowongan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_edit_lowongan)

        val editNama = findViewById<EditText>(R.id.editNamaLowongan)
        val editPerusahaan = findViewById<EditText>(R.id.editPerusahaan)
        val editLokasi = findViewById<EditText>(R.id.editLokasi)
        val editPeriode = findViewById<EditText>(R.id.editPeriode)
        val editDeskripsi = findViewById<EditText>(R.id.editDeskripsi)
        val editKualifikasi = findViewById<EditText>(R.id.editKualifikasi)

        val btnsimpan = findViewById<Button>(R.id.btnSimpanPerubahan)
        val btnbatal = findViewById<Button>(R.id.btnBatal)

        val id_lowongan = intent.getIntExtra("id_lowongan", 0)

        editNama.setText(intent.getStringExtra("nama"))
        editPerusahaan.setText(intent.getStringExtra("perusahaan"))
        editLokasi.setText(intent.getStringExtra("lokasi"))
        editPeriode.setText(intent.getStringExtra("periode"))
        editDeskripsi.setText(intent.getStringExtra("deskripsi"))
        editKualifikasi.setText(intent.getStringExtra("kualifikasi"))

        btnsimpan.setOnClickListener {
            val nama = editNama.text.toString()
            val perusahaan = editPerusahaan.text.toString()
            val lokasi = editLokasi.text.toString()
            val periode = editPeriode.text.toString()
            val deskripsi = editDeskripsi.text.toString()
            val kualifikasi = editKualifikasi.text.toString()

            val api = ApiClient.instance
            api.editLowongan(id_lowongan, nama, perusahaan, lokasi, periode, deskripsi, kualifikasi)
                .enqueue(object : Callback<SendResponse> {
                    override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                        if (response.isSuccessful && response.body()?.success == true) {
                            Toast.makeText(this@Admin_EditLowongan, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@Admin_EditLowongan, "Gagal memperbarui data", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                        Toast.makeText(this@Admin_EditLowongan, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        btnbatal.setOnClickListener {
            Admin_DaftarLowonganFragment.isEdit = true
            finish()
        }
    }
}
