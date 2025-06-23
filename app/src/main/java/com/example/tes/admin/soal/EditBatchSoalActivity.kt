package com.example.tes.admin.soal

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.SendResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditBatchSoalActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_batch_soal)

        val nama = findViewById<EditText>(R.id.NamaBatch)
        val btnUpdate = findViewById<Button>(R.id.btnSimpanBatch)

        val idBatchSoal = intent.getIntExtra("idBatchSoal", 0)
        val namaBatchSoal = intent.getStringExtra("namaBatchSoal")
        nama.setText(namaBatchSoal)

        btnUpdate.setOnClickListener {
            val updatedNama = nama.text.toString()

            if (updatedNama.isNotEmpty()) {
                updateBatchSoal(idBatchSoal, updatedNama)
            } else {
                // Tampilkan pesan kesalahan jika nama kosong
            }
        }
    }

    private fun updateBatchSoal(id: Int, nama: String) {
        val apiService = ApiClient.instance
        val call = apiService.editBatchSoal(id, nama)

        call.enqueue(object : Callback<SendResponse> {
            override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditBatchSoalActivity, "Batch soal berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditBatchSoalActivity, "Batch soal gagal diperbarui", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                Toast.makeText(this@EditBatchSoalActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
