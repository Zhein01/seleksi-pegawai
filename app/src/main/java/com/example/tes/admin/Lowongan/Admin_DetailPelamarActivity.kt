package com.example.tes.admin.Lowongan

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tes.R
class Admin_DetailPelamarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_detail_pelamar)

        val btnkembali = findViewById<Button>(R.id.btnKembaliplr)
        val acc = findViewById<Button>(R.id.btnAccPelamarplr)

        acc.setOnClickListener {
            finish()
        }

        btnkembali.setOnClickListener {
            finish()
        }
    }
}
