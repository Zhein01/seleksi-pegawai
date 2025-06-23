package com.example.tes.admin.User

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tes.MainActivity2
import com.example.tes.R

class SuksesActiviy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sukses)
        val btnkembali = findViewById<Button>(R.id.btnBackToHome)
        btnkembali.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }
    }
}