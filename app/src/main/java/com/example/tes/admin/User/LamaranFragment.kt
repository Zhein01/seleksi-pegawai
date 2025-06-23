package com.example.tes.admin.User

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tes.R
import com.example.tes.admin.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LamaranFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LamaranAdapter
    private val listLamaran = mutableListOf<ModelDaftarLamaran>()
    private var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lamaran_saya, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        userId = sharedPref.getInt("user_id", -1)

        recyclerView = view.findViewById(R.id.recyclerLamaran)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = LamaranAdapter(listLamaran, requireContext(), userId)
        recyclerView.adapter = adapter

        if (userId != -1) {
            getLamaran(userId)
        } else {
            Toast.makeText(requireContext(), "User ID tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getLamaran(userId: Int) {
        ApiClient.instance.getLamaranByUserId(userId).enqueue(object : Callback<List<ModelDaftarLamaran>> {
            override fun onResponse(
                call: Call<List<ModelDaftarLamaran>>,
                response: Response<List<ModelDaftarLamaran>>
            ) {

                Log.d("LamaranFragment", "Response code: ${response.code()}")
                Log.d("LamaranFragment", "Response: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    listLamaran.clear()
                    listLamaran.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat data lamaran", Toast.LENGTH_SHORT).show()
                    Log.e("LamaranSaya", "Response error: ${response.code()} - ${response.message()}")
                    Log.e("LamaranSaya", "Response body: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<List<ModelDaftarLamaran>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("LamaranSaya", "Failure: ${t.message}")
            }
        })
    }

    @Override
    override fun onResume() {
        super.onResume()
        if (userId != -1) {
            getLamaran(userId)
        } else {
            Toast.makeText(requireContext(), "User ID tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }
}
