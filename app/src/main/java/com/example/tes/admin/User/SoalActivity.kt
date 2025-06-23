package com.example.tes.admin.User

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.ModelSoal
import com.example.tes.admin.SendResponse
import com.example.tes.admin.soal.GetResponseSoal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SoalActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterSoal
    private val listSoal = mutableListOf<ModelSoal>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_soal)

        val userId = intent.getIntExtra("user_id", -1)
        val lamaranId = intent.getIntExtra("lamaran_id", -1)

        if (userId == -1 || lamaranId == -1) {
            Toast.makeText(this, "ID tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        recyclerView = findViewById(R.id.recyclerViewSoala)
        adapter = AdapterSoal(listSoal, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getSoal(userId, lamaranId)

        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            hitungDanKirimJawaban(lamaranId)
        }
    }

    private fun getSoal(userId: Int, lamaranId: Int) {
        val call = ApiClient.instance.getSoalByUserAndLamaran(userId, lamaranId)
        call.enqueue(object : Callback<GetResponseSoal> {
            override fun onResponse(
                call: Call<GetResponseSoal>,
                response: Response<GetResponseSoal>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success) {
                        listSoal.clear()
                        listSoal.addAll(body.data)
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@SoalActivity, body?.message ?: "Gagal", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@SoalActivity, "Respon gagal: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetResponseSoal>, t: Throwable) {
                Toast.makeText(this@SoalActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun hitungDanKirimJawaban(lamaranId: Int) {
        var jumlahBenar = 0

        for (soal in listSoal) {
            if (soal.jawabanUser.equals(soal.jawaban, ignoreCase = true)) {
                jumlahBenar++
            }
        }

        val call = ApiClient.instance.kirimJawaban(lamaranId, jumlahBenar)
        call.enqueue(object : Callback<SendResponse> {
            override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SoalActivity, "Jawaban berhasil dikirim!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@SoalActivity, "Gagal kirim jawaban" + response.message().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                Toast.makeText(this@SoalActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}