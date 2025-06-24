package com.example.tes.admin.User

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.tes.LoginActivity
import com.example.tes.R
import com.example.tes.admin.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    // Declare views directly without ViewBinding
    private lateinit var editNIK: EditText
    private lateinit var editNama: EditText
    private lateinit var editAlamat: EditText
    private lateinit var editHp: EditText
    private lateinit var editTmp: EditText
    private lateinit var editTgl: EditText
    private lateinit var editUsername: EditText
    private lateinit var editPassword: EditText
    private lateinit var spinnerGender: Spinner
    private lateinit var btnDaftar: Button
    private lateinit var btnKembali: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_calon_karyawan)

        // Initialize views
        editNIK = findViewById(R.id.editNIK)
        editNama = findViewById(R.id.editNama)
        editAlamat = findViewById(R.id.editAlamat)
        editHp = findViewById(R.id.editHp)
        editTmp = findViewById(R.id.editTmp)
        editTgl = findViewById(R.id.editTgl)
        editUsername = findViewById(R.id.editUsername)
        editPassword = findViewById(R.id.editPasswordrgs)
        spinnerGender = findViewById(R.id.spinnerGender)
        btnDaftar = findViewById(R.id.btnDaftargs)
        btnKembali = findViewById(R.id.btnKembalirgs)

        // Set up gender spinner with options
        val genderOptions = listOf("L", "P")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter

        // Handle register button click
        btnDaftar.setOnClickListener {
            registerUser()
        }

        // Handle back button click
        btnKembali.setOnClickListener {
            finish()  // Close the activity and go back
        }
    }

    private fun registerUser() {
        // Collect user input
        val nik = editNIK.text.toString()
        val nama = editNama.text.toString()
        val alamat = editAlamat.text.toString()
        val jenisKelamin = spinnerGender.selectedItem.toString()
        val noHp = editHp.text.toString()
        val tmpLahir = editTmp.text.toString()
        val tglLahir = editTgl.text.toString()
        val username = editUsername.text.toString()
        val password = editPassword.text.toString()

        // Validate inputs
        if (nik.isEmpty() || nama.isEmpty() || alamat.isEmpty() || noHp.isEmpty() ||
            username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val request = RegisterRequest(nik, nama, alamat, jenisKelamin, noHp, tmpLahir, tglLahir, username, password)

        ApiClient.instance.registerUser(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                Log.d("API", "Kode Response: ${response.code()}")
                Log.d("API", "Isi Body: ${response.body()}")
                Log.d("API", "Isi Error Body: ${response.errorBody()?.string()}")

                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    val userData = registerResponse?.data

                    Toast.makeText(this@RegisterActivity, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
                    navigateToLogin(userData)
                    clearFields()

                } else {
                    Toast.makeText(this@RegisterActivity, "Registrasi Gagal. Coba lagi.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Perbaiki Password anda", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun navigateToLogin(userData: UserData?) {
        // Navigasi ke LoginActivity setelah registrasi sukses
        val intent = Intent(this, LoginActivity::class.java)

        // Opsional: Jika perlu, kamu bisa menambahkan data ke Intent
        userData?.username?.let {
            intent.putExtra("user_name", it)
        }

        startActivity(intent)
        finish()  // Menutup RegisterActivity setelah navigasi
    }

    private fun clearFields() {
        editNIK.text.clear()
        editNama.text.clear()
        editAlamat.text.clear()
        editHp.text.clear()
        editUsername.text.clear()
        editPassword.text.clear()
        spinnerGender.setSelection(0)
    }
}
