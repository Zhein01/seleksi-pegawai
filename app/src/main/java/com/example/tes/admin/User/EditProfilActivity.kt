package com.example.tes.admin.User

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.SendResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfilActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)

        val edtNik = findViewById<EditText>(R.id.edtNik)
        val edtNama = findViewById<EditText>(R.id.edtNamaProfile)
        val edtAlamat = findViewById<EditText>(R.id.edtAlamat)
        val radioJkl = findViewById<RadioGroup>(R.id.radioGroupJkl)
        val noTelp = findViewById<EditText>(R.id.edtTeleponprofile)
        val btnSimpan = findViewById<Button>(R.id.btnSimpanprofile)
        val back = findViewById<Button>(R.id.backprofile)

        val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("user_id", -1)

        back.setOnClickListener {
            finish()
        }

        ApiClient.instance.getUserProfile(userId).enqueue(object : Callback<UserProfileResponse> {
            override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val data = response.body()?.data
                    edtNik.setText(data?.nik)
                    edtNama.setText(data?.nama)
                    edtAlamat.setText(data?.alamat)
                    if (data?.jkl == "L") {
                        radioJkl.check(R.id.radioLaki)
                    } else if (data?.jkl == "P") {
                        radioJkl.check(R.id.radioPerempuan)
                    }
                    noTelp.setText(data?.no_hp)
                } else {
                    Toast.makeText(this@EditProfilActivity, "Gagal memuat data profil", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                Toast.makeText(this@EditProfilActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        btnSimpan.setOnClickListener {
            val nik = edtNik.text.toString().trim()
            val nama = edtNama.text.toString().trim()
            val alamat = edtAlamat.text.toString().trim()
            val jkl = when (radioJkl.checkedRadioButtonId) {
                R.id.radioLaki -> "L"
                R.id.radioPerempuan -> "P"
                else -> ""
            }
            val noHp = noTelp.text.toString().trim()

            if (nik.isEmpty() || nama.isEmpty() || alamat.isEmpty() || jkl.isEmpty() || noHp.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ApiClient.instance.updateProfil(userId, nik, nama, alamat, jkl, noHp)
                .enqueue(object : Callback<SendResponse> {
                    override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                        if (response.isSuccessful) {
                            val res = response.body()
                            if (res?.success == true) {
                                Toast.makeText(this@EditProfilActivity, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(this@EditProfilActivity, "Gagall: ${res?.message}", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@EditProfilActivity, "Gagalllll: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                            edtAlamat.setText("${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                        Toast.makeText(this@EditProfilActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

}
