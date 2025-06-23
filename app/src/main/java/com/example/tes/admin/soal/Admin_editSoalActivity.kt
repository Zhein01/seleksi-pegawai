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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Admin_editSoalActivity : AppCompatActivity() {

    private lateinit var editPertanyaan: EditText
    private lateinit var jawaban1: EditText
    private lateinit var jawaban2: EditText
    private lateinit var jawaban3: EditText
    private lateinit var jawaban4: EditText
    private lateinit var jawabanBenar: TextView
    private lateinit var radioGroup: RadioGroup

    private var idSoal: Int = 0
    private var jawaban: String = "A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_edit_soal)

        editPertanyaan = findViewById(R.id.editPertanyaan2)
        jawaban1 = findViewById(R.id.jawaban1)
        jawaban2 = findViewById(R.id.jawaban2)
        jawaban3 = findViewById(R.id.jawaban3)
        jawaban4 = findViewById(R.id.jawaban4)
        jawabanBenar = findViewById(R.id.jawabanBenar)
        radioGroup = findViewById(R.id.radioGroupJawabana)
        val radio1 = findViewById<RadioButton>(R.id.radio1)
        val radio2 = findViewById<RadioButton>(R.id.radio2)
        val radio3 = findViewById<RadioButton>(R.id.radio3)
        val radio4 = findViewById<RadioButton>(R.id.radio4)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        // Ambil data dari intent
        idSoal = intent.getIntExtra("id_soal", 0)
        val soal = intent.getStringExtra("soal")
        val pil1 = intent.getStringExtra("pil1")
        val pil2 = intent.getStringExtra("pil2")
        val pil3 = intent.getStringExtra("pil3")
        val pil4 = intent.getStringExtra("pil4")
        jawaban = intent.getStringExtra("jawaban").toString()

        // Set nilai ke input
        editPertanyaan.setText(soal)
        jawaban1.setText(pil1)
        jawaban2.setText(pil2)
        jawaban3.setText(pil3)
        jawaban4.setText(pil4)
        jawabanBenar.text = jawaban

        if (jawaban == "A") {
            radio1.isChecked = true
        } else if (jawaban == "B") {
            radio2.isChecked = true
        } else if (jawaban == "C") {
            radio3.isChecked = true
        } else if (jawaban == "D") {
            radio4.isChecked = true
        }

        // Handle radio button
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio1 -> jawabanBenar.text = "A"
                R.id.radio2 -> jawabanBenar.text = "B"
                R.id.radio3 -> jawabanBenar.text = "C"
                R.id.radio4 -> jawabanBenar.text = "D"
            }
        }

        // Simpan tombol
        btnSimpan.setOnClickListener {
            updateSoal()
        }
    }

    private fun updateSoal() {
        val soal = editPertanyaan.text.toString()
        val pil1 = jawaban1.text.toString()
        val pil2 = jawaban2.text.toString()
        val pil3 = jawaban3.text.toString()
        val pil4 = jawaban4.text.toString()
        val jawaban = jawabanBenar.text.toString()

        // Panggil API edit
        ApiClient.instance.editSoal(
            id = idSoal,
            soal = soal,
            pil1 = pil1,
            pil2 = pil2,
            pil3 = pil3,
            pil4 = pil4,
            jawaban = jawaban
        ).enqueue(object : Callback<SendResponse> {
            override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Admin_editSoalActivity, "Berhasil edit soal!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@Admin_editSoalActivity, "Gagal edit soal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                Toast.makeText(this@Admin_editSoalActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
