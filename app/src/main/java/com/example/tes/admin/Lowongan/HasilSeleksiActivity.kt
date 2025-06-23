package com.example.tes.admin.Lowongan

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.User.HasilSeleksiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HasilSeleksiActivity : AppCompatActivity() {
    private lateinit var adapter: AdapterHasilSeleksi
    private lateinit var recyclerView: RecyclerView
    private val hasilList = mutableListOf<ModelHasilSeleksi>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_seleksi)

        val lowonganId = intent.getIntExtra("lowongan_id", -1)
        recyclerView = findViewById(R.id.listHasilSeleksi)
        adapter = AdapterHasilSeleksi(hasilList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        if (lowonganId != -1) {
            fetchHasilSeleksi(lowonganId)
        }

        findViewById<ImageView>(R.id.bck).setOnClickListener {
            finish()
        }
    }

    private fun fetchHasilSeleksi(lowonganId: Int) {
        ApiClient.instance.getHasilSeleksi(lowonganId)
            .enqueue(object : Callback<HasilSeleksiResponse> {
                override fun onResponse(
                    call: Call<HasilSeleksiResponse>,
                    response: Response<HasilSeleksiResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        hasilList.clear()
                        hasilList.addAll(response.body()!!.data)
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<HasilSeleksiResponse>, t: Throwable) {
                    Toast.makeText(this@HasilSeleksiActivity, "Gagal: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
