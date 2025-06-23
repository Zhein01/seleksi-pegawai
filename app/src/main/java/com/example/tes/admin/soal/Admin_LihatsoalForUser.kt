package com.example.tes.admin.soal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.ModelSoal
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Admin_LihatsoalForUser : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Admin_Adapter_lihatsoalforuser
    private val soalList = mutableListOf<ModelSoal>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.recicleview_lihatsoal)

        val btnTambah = findViewById<FloatingActionButton>(R.id.btnTambah)
        val back = findViewById<ImageView>(R.id.back)
        recyclerView = findViewById(R.id.recyclerViewSoala)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Admin_Adapter_lihatsoalforuser(soalList, this)
        recyclerView.adapter = adapter

        btnTambah.setOnClickListener {
            val idBatchSoal = intent.getIntExtra("idBatchSoal", 0)
            val intent = Intent(this, TambahSoalActivity::class.java)
            intent.putExtra("idBatchSoal", idBatchSoal)
            startActivity(intent)
        }

        back.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        val idBatchSoal = intent.getIntExtra("idBatchSoal", 0)
        loadSoalData(idBatchSoal)
    }

    private fun loadSoalData(idBatchSoal: Int) {
        ApiClient.instance.getSoalByBatch(idBatchSoal)
            .enqueue(object : Callback<GetResponseSoal> {
                override fun onResponse(call: Call<GetResponseSoal>, response: Response<GetResponseSoal>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        soalList.clear()
                        soalList.addAll((response.body()?.data ?: emptyList()) as Collection<ModelSoal>)
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@Admin_LihatsoalForUser, "Gagal ambil data soal", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetResponseSoal>, t: Throwable) {
                    Toast.makeText(this@Admin_LihatsoalForUser, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
