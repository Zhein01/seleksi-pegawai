package com.example.tes.admin.User
import android.content.Context
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
import com.example.tes.admin.GetResponse
import com.example.tes.admin.Lowongan.Admin_AdapterDaftarLowongan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragmentdaftarlowongan : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterDaftarLowongan
    private var dataLowongan = mutableListOf<ModelDaftarLowongan>()

    override fun onCreateView(
        inflater: LayoutInflater,
        containerView: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.daftar_lowongan, containerView,false)

        recyclerView = view.findViewById(R.id.recyclerViewLowongan)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterDaftarLowongan(dataLowongan, requireContext())
        recyclerView.adapter = adapter


        return view
    }

    override fun onResume() {
        super.onResume()

        loadLowonganData()
    }
    private fun loadLowonganData() {
        ApiClient.instance.getLowongan().enqueue(object : Callback<GetResponse> {
            override fun onResponse(call: Call<GetResponse>, response: Response<GetResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val newData = response.body()?.data ?: emptyList()
                    dataLowongan.clear()
                    dataLowongan.addAll(newData)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Gagal ambil data", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<GetResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

