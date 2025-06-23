package com.example.tes

import com.example.tes.admin.User.ViewPagerAdapter
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val daftar: LinearLayout = findViewById(R.id.daftar)
        val daftarlamaran: LinearLayout = findViewById(R.id.lamaransaya)
        val profil: LinearLayout = findViewById(R.id.profil)
        val viewpager = findViewById<ViewPager2>(R.id.viewpager)

        viewpager.adapter = ViewPagerAdapter(this)
        daftar.setOnClickListener{
           viewpager.currentItem=0
        }
        daftarlamaran.setOnClickListener{
                viewpager.currentItem=1
        }
        profil.setOnClickListener{
            viewpager.currentItem=2
        }
    }
}