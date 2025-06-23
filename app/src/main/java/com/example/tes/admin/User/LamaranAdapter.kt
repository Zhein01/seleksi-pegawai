package com.example.tes.admin.User

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.SendResponse
import com.example.tes.admin.SendResponseHasil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LamaranAdapter(private val lamaranList: List<ModelDaftarLamaran>, private val requireContext: Context, private val idUser: Int) :
    RecyclerView.Adapter<LamaranAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val posisiText: TextView = view.findViewById(R.id.tvNamaPosisi)
        val perusahaanText: TextView = view.findViewById(R.id.tvPerusahaan)
        val btnSoal: Button = view.findViewById(R.id.btnSoal)
        val btnHasil: Button = view.findViewById(R.id.btnLhtHasil)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lamaran, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lamaran = lamaranList[position]
        val lowongan = lamaran.lowongan
        val apiService = ApiClient.instance

        holder.posisiText.text = lowongan.nama
        holder.perusahaanText.text = lowongan.perusahaan

        val btnHasil = holder.btnHasil
        btnHasil.isEnabled = false
        if (lowongan.id != -1) {
            ApiClient.instance.isLowonganBerakhir(lowongan.id).enqueue(object : Callback<SendResponse> {
                override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                    if (response.isSuccessful) {
                        val isSelesai = response.body()?.success ?: false
                        if (!isSelesai) {
                            btnHasil.isEnabled = false
                            btnHasil.setBackgroundColor(Color.GRAY)
                        }else{
                            btnHasil.isEnabled = true
                            btnHasil.setBackgroundColor(Color.parseColor("#4CAF50"))
                            btnHasil.setOnClickListener {
                                apiService.lihatHasilLamaran(lamaran.id).enqueue(object : Callback<SendResponseHasil> {
                                    override fun onResponse(call: Call<SendResponseHasil>, response: Response<SendResponseHasil>) {
                                        if (response.isSuccessful && response.body()?.success == true) {
                                            val data = response.body()?.data
                                            val intent = Intent(requireContext, HasilActivity::class.java)
                                            intent.putExtra("lamaran_id", lamaran.id)
                                            intent.putExtra("nama", data?.nama)
                                            intent.putExtra("skor", data?.skor.toString())
                                            intent.putExtra("status", data?.status)
                                            requireContext.startActivity(intent)
                                        } else {
                                            Toast.makeText(requireContext, "Gagal mengambil hasil", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<SendResponseHasil>, t: Throwable) {
                                        Toast.makeText(requireContext, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                                    }
                                })
                            }
                        }
                    } else {
                        Log.e("LowonganStatus", "Gagal mengecek status")
                    }
                }

                override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                    Log.e("LowonganStatus", "Error: ${t.message}")
                }
            })
        }

        val btnSoal = holder.btnSoal
        btnSoal.isEnabled = false
        if (!(lowongan.batch_soal_id == null || lowongan.batch_soal_id == 0)) {
            apiService.cekSudahKirim(lamaran.id).enqueue(object : Callback<SendResponse> {
                override fun onResponse(
                    call: Call<SendResponse>,
                    response: Response<SendResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val sudahKirim = response.body()!!.success
                        if (sudahKirim) {
                            btnSoal.isEnabled = false
                            btnSoal.setBackgroundColor(Color.GRAY)
                        } else {
                            btnSoal.isEnabled = true
                            btnSoal.setBackgroundColor(Color.parseColor("#4CAF50"))
                            btnSoal.setOnClickListener {
                                val intent = Intent(requireContext, SoalActivity::class.java)
                                intent.putExtra("user_id", idUser)
                                intent.putExtra("lamaran_id", lamaran.id)
                                requireContext.startActivity(intent)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                    Log.e("LamaranAdapter", "Gagal cek status soal: ${t.message}")
                }
            })
        }
    }

    override fun getItemCount(): Int = lamaranList.size
}