package com.example.tes.admin.Lowongan

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.SendResponse
import com.example.tes.admin.soal.BatchNameResponse
import com.example.tes.admin.soal.GetResponeBatch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject

class Admin_DetailLowonganActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_detail_lowongan)

        val Nama = findViewById<TextView>(R.id.nama)
        val Perusahaan = findViewById<TextView>(R.id.perusahaan)
        val Lokasi = findViewById<TextView>(R.id.lokasi)
        val Periode = findViewById<TextView>(R.id.periode)
        val Deskripsi = findViewById<TextView>(R.id.deskripsi)
        val Kualifikasi = findViewById<TextView>(R.id.kualifikasi)
        val btnlihatpendaftar = findViewById<Button>(R.id.btnLihatPendaftarForAdmin)
        val btnmulaiseleksi = findViewById<Button>(R.id.btnMulaiSeleksiforAdmin)
        val btnkembali = findViewById<Button>(R.id.btnbackDaftarLowonganForAdmin)
        val btngambarbck = findViewById<ImageView>(R.id.bckbck)
        val btnLihatHasil = findViewById<Button>(R.id.lihatHasil)
        val btnAkhiriLowongan = findViewById<Button>(R.id.btnAkhiriLowongan)

        Nama.setText(intent.getStringExtra("nama"))
        Perusahaan.setText(intent.getStringExtra("perusahaan"))
        Lokasi.setText(intent.getStringExtra("lokasi"))
        Periode.setText(intent.getStringExtra("periode"))
        Deskripsi.setText(intent.getStringExtra("deskripsi"))
        Kualifikasi.setText(intent.getStringExtra("kualifikasi"))

        val lowonganId = intent.getIntExtra("lowongan_id", -1)
        val txtNamaBatch = findViewById<TextView>(R.id.txtNamaBatch)

        ApiClient.instance.getBatchNameByLowonganId(lowonganId).enqueue(object : Callback<BatchNameResponse> {
            override fun onResponse(call: Call<BatchNameResponse>, response: Response<BatchNameResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val batchSoal = response.body()?.data
                    txtNamaBatch.text = "Batch soal: ${batchSoal?.nama}"
                } else {
                    txtNamaBatch.text = ""
                }
            }

            override fun onFailure(call: Call<BatchNameResponse>, t: Throwable) {
                txtNamaBatch.text = ""
            }
        })

        btnlihatpendaftar.setOnClickListener {
            val intent = Intent(this, Admin_DaftarPendaftarActivity::class.java)
            intent.putExtra("lowongan_id", lowonganId)
            Log.d("DetailLowongan", "Kirim lowongan_id: $lowonganId")
            startActivity(intent)
        }

        btnmulaiseleksi.setOnClickListener {
            showBatchSoalDialog(lowonganId)
        }

        btnAkhiriLowongan.isEnabled = false
        if (lowonganId != -1) {
            ApiClient.instance.isLowonganBerakhir(lowonganId).enqueue(object : Callback<SendResponse> {
                override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                    if (response.isSuccessful) {
                        val isSelesai = response.body()?.success ?: false
                        if (isSelesai) {
                            btnAkhiriLowongan.isEnabled = false
                            btnAkhiriLowongan.setBackgroundColor(Color.GRAY)
                        }else{
                            btnAkhiriLowongan.isEnabled = true
                            btnAkhiriLowongan.setBackgroundColor(Color.parseColor("#4CAF50"))
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

        btnAkhiriLowongan.setOnClickListener {
            if (lowonganId != -1) {
                AlertDialog.Builder(this)
                    .setTitle("Konfirmasi")
                    .setMessage("Yakin ingin mengakhiri lowongan ini?")
                    .setPositiveButton("Ya") { _, _ ->
                        ApiClient.instance.akhiriLowongan(lowonganId).enqueue(object : Callback<SendResponse> {
                            override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                                if (response.isSuccessful && response.body()?.success == true) {
                                    Toast.makeText(this@Admin_DetailLowonganActivity, "Lowongan berhasil diakhiri", Toast.LENGTH_SHORT).show()
                                    btnAkhiriLowongan.isEnabled = false
                                    btnAkhiriLowongan.setBackgroundColor(Color.GRAY)
                                } else {
                                    val errorMessage = try {
                                        val errorBody = response.errorBody()?.string()
                                        val jsonObject = JSONObject(errorBody ?: "")
                                        jsonObject.getString("message")
                                    } catch (e: Exception) {
                                        "Terjadi kesalahan"
                                    }

                                    Toast.makeText(this@Admin_DetailLowonganActivity, "Gagal mengakhiri lowongan: $errorMessage", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                                Toast.makeText(this@Admin_DetailLowonganActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            }
        }

        btnLihatHasil.setOnClickListener {
            val intent = Intent(this, HasilSeleksiActivity::class.java)
            intent.putExtra("lowongan_id", lowonganId)
            startActivity(intent)
        }

        btnkembali.setOnClickListener {
            finish()
        }
        btngambarbck.setOnClickListener {
            finish()
        }

    }

    private fun showBatchSoalDialog(lowonganId: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_batch_soal, null)
        val spinner = dialogView.findViewById<Spinner>(R.id.spinnerBatchSoal)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Mulai Seleksi", null)
            .setNegativeButton("Batal", null)
            .create()

        ApiClient.instance.getBatchSoal().enqueue(object : Callback<GetResponeBatch> {
            override fun onResponse(call: Call<GetResponeBatch>, response: Response<GetResponeBatch>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val batchList = response.body()?.data ?: emptyList()
                    val batchNames = batchList.map { it.nama }
                    spinner.adapter = ArrayAdapter(this@Admin_DetailLowonganActivity, android.R.layout.simple_spinner_dropdown_item, batchNames)

                    dialog.show()

                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val selectedIndex = spinner.selectedItemPosition
                        val selectedBatch = batchList[selectedIndex]

                        ApiClient.instance.mulaiSeleksi(lowonganId, selectedBatch.id).enqueue(object : Callback<SendResponse> {
                            override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                                if (response.isSuccessful && response.body()?.success == true) {
                                    findViewById<TextView>(R.id.txtNamaBatch).text = "Batch soal: ${selectedBatch.nama}"
                                    Toast.makeText(this@Admin_DetailLowonganActivity, "Seleksi dimulai!", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                } else {
                                    Toast.makeText(this@Admin_DetailLowonganActivity, "Gagal memulai seleksi", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                                Toast.makeText(this@Admin_DetailLowonganActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }

                } else {
                    Toast.makeText(this@Admin_DetailLowonganActivity, "Gagal mengambil data batch soal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetResponeBatch>, t: Throwable) {
                Toast.makeText(this@Admin_DetailLowonganActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}