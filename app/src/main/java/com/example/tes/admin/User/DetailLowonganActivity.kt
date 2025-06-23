package com.example.tes.admin.User

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tes.R

class DetailLowonganActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_lowongan)

        val Nama = findViewById<TextView>(R.id.txtPosisi2)
        val Perusahaan = findViewById<TextView>(R.id.txtPerusahaan2)
        val Lokasi = findViewById<TextView>(R.id.txtLokasi2)
        val Periode = findViewById<TextView>(R.id.txtTanggal2)
        val Deskripsi = findViewById<TextView>(R.id.txtDeskripsi2)
        val Kualifikasi = findViewById<TextView>(R.id.kualifikasi2)
        val btndaftarsekarang = findViewById<Button>(R.id.btnLamar)
        val btnback = findViewById<Button>(R.id.btnKembali)
        val imgback = findViewById<ImageView>(R.id.back)

        // Ambil semua data termasuk ID lowongan
        val lowonganId = intent.getIntExtra("lowongan_id", -1)
        val nama = intent.getStringExtra("nama")
        val perusahaan = intent.getStringExtra("perusahaan")
        val lokasi = intent.getStringExtra("lokasi")
        val periode = intent.getStringExtra("periode")
        val deskripsi = intent.getStringExtra("deskripsi")
        val kualifikasi = intent.getStringExtra("kualifikasi")

        Nama.text = nama
        Perusahaan.text = perusahaan
        Lokasi.text = lokasi
        Periode.text = periode
        Deskripsi.text = deskripsi
        Kualifikasi.text = kualifikasi

        imgback.setOnClickListener { finish() }
        btnback.setOnClickListener { finish() }

        btndaftarsekarang.setOnClickListener {
            val intent = Intent(this, FormulirActivity::class.java)
            intent.putExtra("lowongan_id", lowonganId)
            startActivity(intent)
        }
    }
}
