package com.example.tes.admin.Lowongan

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.SendResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Admin_TambahLowongan : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_tambah_lowongan)

        val Nama = findViewById<EditText>(R.id.nama)
        val Perusahaan = findViewById<EditText>(R.id.Perusahaan)
        val Lokasi = findViewById<EditText>(R.id.Lokasi)
        val Periode = findViewById<EditText>(R.id.Periode)
        val Deskripsi = findViewById<EditText>(R.id.Deskripsi)
        val Kualifikasi = findViewById<EditText>(R.id.Kualifikasi)
        val btnTambah = findViewById<Button>(R.id.btnTambahLowongan)
        val btnBatal = findViewById<Button>(R.id.btnBatalbt)

        btnBatal.setOnClickListener {
            finish()
        }
        btnTambah.setOnClickListener {
            val nama = Nama.text.toString()
            val perusahaan = Perusahaan.text.toString()
            val lokasi = Lokasi.text.toString()
            val periode = Periode.text.toString()
            val deskripsi = Deskripsi.text.toString()
            val kualifikasi = Kualifikasi.text.toString()

            val api = ApiClient.instance
            api.tambahLowongan(nama, perusahaan, lokasi, periode, deskripsi, kualifikasi)
                .enqueue(object : Callback<SendResponse> {
                    override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                        if (response.isSuccessful && response.body()?.success == true) {
                            Toast.makeText(this@Admin_TambahLowongan, "Lowongan berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@Admin_TambahLowongan, "Error: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                        Toast.makeText(this@Admin_TambahLowongan, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}
