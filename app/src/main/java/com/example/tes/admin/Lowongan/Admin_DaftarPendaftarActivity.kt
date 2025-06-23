package com.example.tes.admin.Lowongan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tes.R
import com.example.tes.admin.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Admin_DaftarPendaftarActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Admin_AdapterDaftarPendaftar
    private val listPendaftar = mutableListOf<Modelpendaftar>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_daftar_pendaftar)

        recyclerView = findViewById(R.id.recyclerViewPendaftar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Admin_AdapterDaftarPendaftar(listPendaftar, this)
        recyclerView.adapter = adapter

        val lowonganId = intent.getIntExtra("lowongan_id", -1)
        if (lowonganId != -1) {
            getDataPendaftar(lowonganId)
        }
    }

    private fun getDataPendaftar(lowonganId: Int) {
        ApiClient.instance.getPendaftarByLowongan(lowonganId)
            .enqueue(object : Callback<GetPendaftarResponse> {
                override fun onResponse(
                    call: Call<GetPendaftarResponse>,
                    response: Response<GetPendaftarResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        listPendaftar.clear()
                        listPendaftar.addAll(response.body()?.data ?: emptyList())
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@Admin_DaftarPendaftarActivity, "Data kosong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetPendaftarResponse>, t: Throwable) {
                    Toast.makeText(this@Admin_DaftarPendaftarActivity, "Gagal: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
