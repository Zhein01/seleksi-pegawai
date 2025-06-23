package com.example.tes.admin

import Admin_adapterViewpager
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.tes.R

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val lowongan: LinearLayout = findViewById(R.id.lowongan)
        val soal: LinearLayout = findViewById(R.id.soal)
        val logout: LinearLayout = findViewById(R.id.logout)
        val viewpager = findViewById<ViewPager2>(R.id.admin_viewpager)
        viewpager.adapter = Admin_adapterViewpager(this)

        lowongan.setOnClickListener{
            viewpager.currentItem=0
        }
        soal.setOnClickListener{
            viewpager.currentItem=1
        }
        logout.setOnClickListener {
            viewpager.currentItem=2
        }
    }
}