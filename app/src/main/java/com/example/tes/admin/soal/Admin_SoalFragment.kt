package com.example.tes.admin.soal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.ModelBatch
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Admin_SoalFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Admin_AdapterDaftarSoalSeleksi
    private val listBatch = mutableListOf<ModelBatch>()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_admin_form_tambah_soal, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewSoal)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = Admin_AdapterDaftarSoalSeleksi(listBatch, requireContext())
        recyclerView.adapter = adapter

        val btnTambah = view.findViewById<FloatingActionButton>(R.id.btnTambahsoal)
        btnTambah.setOnClickListener {
            val intent = Intent(requireContext(), Admin_tambahbatchActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        loadBatchSoal()
    }

    private fun loadBatchSoal() {
        ApiClient.instance.getBatchSoal().enqueue(object : Callback<GetResponeBatch> {
            override fun onResponse(call: Call<GetResponeBatch>, response: Response<GetResponeBatch>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val data = response.body()?.data ?: emptyList()
                    listBatch.clear()
                    listBatch.addAll(data)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Gagal ambil data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetResponeBatch>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}