package com.example.tes.admin.soal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.SendResponse

class TambahSoalActivity : AppCompatActivity() {
    private lateinit var radioGroup: RadioGroup
    private lateinit var jawabanBenar: TextView
    private lateinit var radio1: RadioButton
    private lateinit var radio2: RadioButton
    private lateinit var radio3: RadioButton
    private lateinit var radio4: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_soal)

        radioGroup = findViewById(R.id.radioGroupJawabana)
        jawabanBenar = findViewById(R.id.jawabanBenar)
        radio1 = findViewById(R.id.radio1)
        radio2 = findViewById(R.id.radio2)
        radio3 = findViewById(R.id.radio3)
        radio4 = findViewById(R.id.radio4)

        val idBatchSoal = intent.getIntExtra("idBatchSoal", 0)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio1 -> jawabanBenar.text = "A"
                R.id.radio2 -> jawabanBenar.text = "B"
                R.id.radio3 -> jawabanBenar.text = "C"
                R.id.radio4 -> jawabanBenar.text = "D"
            }
        }
        val editPertanyaan = findViewById<EditText>(R.id.editPertanyaan2)
        val jawabanA = findViewById<EditText>(R.id.jawaban1)
        val jawabanB = findViewById<EditText>(R.id.jawaban2)
        val jawabanC = findViewById<EditText>(R.id.jawaban3)
        val jawabanD = findViewById<EditText>(R.id.jawaban4)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            val soal = editPertanyaan.text.toString()
            val pil1 = jawabanA.text.toString()
            val pil2 = jawabanB.text.toString()
            val pil3 = jawabanC.text.toString()
            val pil4 = jawabanD.text.toString()
            val jawaban = jawabanBenar.text.toString()

            if (soal.isNotEmpty() && pil1.isNotEmpty() && pil2.isNotEmpty() && pil3.isNotEmpty() && pil4.isNotEmpty()) {
                ApiClient.instance.tambahSoal(
                    batchSoalId = idBatchSoal,
                    soal = soal,
                    pil1 = pil1,
                    pil2 = pil2,
                    pil3 = pil3,
                    pil4 = pil4,
                    jawaban = jawaban
                ).enqueue(object : retrofit2.Callback<SendResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<SendResponse>,
                        response: retrofit2.Response<SendResponse>
                    ) {
                        if (response.isSuccessful && response.body()?.success == true) {
                            // berhasil
                            Toast.makeText(this@TambahSoalActivity, "Berhasil menambahkan soal", Toast.LENGTH_SHORT).show()
                            finish() // Kembali ke halaman sebelumnya
                        } else {
                            Toast.makeText(this@TambahSoalActivity, "Gagal menambahkan soal", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: retrofit2.Call<SendResponse>, t: Throwable) {
                        Toast.makeText(this@TambahSoalActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Lengkapi semua field!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
