package com.example.tes.admin.User

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tes.R
import com.example.tes.admin.ApiClient

class HasilActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tampilan_hasil)

        val tvNama = findViewById<TextView>(R.id.tvNamaPelamar)
        val tvSkor = findViewById<TextView>(R.id.tvSkor)
        val tvStatus = findViewById<TextView>(R.id.tvKeterangan)
        val btnBack = findViewById<Button>(R.id.backHasil)

        val nama = intent.getStringExtra("nama")
        val skor = intent.getStringExtra("skor")
        val status = intent.getStringExtra("status")

        if(status.equals("lulus")){
            findViewById<TextView>(R.id.tvSelamat).text = "Selamat Anda Dinyatakan Lulus Seleksi"
            findViewById<ImageView>(R.id.gambarHasil).setImageResource(R.drawable.success)
        }

        tvNama.text = nama ?: "-"
        tvSkor.text = skor ?: "-"
        tvStatus.text = status ?: "-"

        btnBack.setOnClickListener {
            finish()
        }

        val btnCetak = findViewById<Button>(R.id.cetak)
        btnCetak.setOnClickListener {
            val lamaranId = intent.getIntExtra("lamaran_id", 0)
            val url = ApiClient.BASE_URL + "user/lamaran/hasil/cetak-pdf?lamaran_id=$lamaranId"

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}