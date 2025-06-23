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

class Admin_tambahbatchActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_tambahbatch)

        val edtNamaBatch = findViewById<EditText>(R.id.edtNamaBatch)
        val btnSimpanBatch = findViewById<Button>(R.id.btnSimpanBatch2)

        btnSimpanBatch.setOnClickListener {
            val namaBatch = edtNamaBatch.text.toString()

            if (namaBatch.isEmpty()) {
                Toast.makeText(this, "Nama batch tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                simpanBatch(namaBatch)
            }
        }
    }

    private fun simpanBatch(nama: String) {
        val api = ApiClient.instance
        val call = api.tambahBatchSoal(nama)

        call.enqueue(object : Callback<SendResponse> {
            override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@Admin_tambahbatchActivity, "Berhasil tambah batch soal", Toast.LENGTH_SHORT).show()
                    finish() // balik setelah tambah
                } else {
                    Toast.makeText(this@Admin_tambahbatchActivity, "Gagal tambah batch soal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                Toast.makeText(this@Admin_tambahbatchActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
