package com.example.tes.admin.Lowongan

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.SendResponse
import retrofit2.Call

class DetailPendaftarActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_detail_pelamar)

        val pendaftar = intent.getSerializableExtra("pendaftar") as Modelpendaftar

        findViewById<TextView>(R.id.txtNamaplr).setText(pendaftar.nama)
        findViewById<TextView>(R.id.txtEmailplr).setText(pendaftar.email)
        findViewById<TextView>(R.id.txtTeleponplr).setText(pendaftar.telepon)
        findViewById<TextView>(R.id.txtPendidikanplr).setText(pendaftar.pendidikan)

        findViewById<Button>(R.id.btnKembaliplr).setOnClickListener {
            finish()
        }


        findViewById<Button>(R.id.btnLihatCVplr).setOnClickListener {
            val baseUrl = "http://192.168.207.21:8000/storage/"
            val fullCvUrl = baseUrl + pendaftar.cv

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(fullCvUrl), "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY

            val chooser = Intent.createChooser(intent, "Buka CV dengan")
            startActivity(chooser)
        }

        val btnAcc = findViewById<Button>(R.id.btnAccPelamarplr)
        if (pendaftar.status == "acc") {
            btnAcc.isEnabled = false
            btnAcc.text = "Sudah Di-ACC"
            btnAcc.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
        }


        btnAcc.setOnClickListener {
            val lamaranId = pendaftar.id

            ApiClient.instance.accLamaran(lamaranId).enqueue(object : retrofit2.Callback<SendResponse> {
                override fun onResponse(
                    call: Call<SendResponse>,
                    response: retrofit2.Response<SendResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(this@DetailPendaftarActivity, "Lamaran berhasil di-ACC", Toast.LENGTH_SHORT).show()

                        btnAcc.isEnabled = false
                        btnAcc.text = "Sudah Di-ACC"
                        btnAcc.setBackgroundColor(ContextCompat.getColor(baseContext, R.color.gray))
                    } else {
                        Toast.makeText(this@DetailPendaftarActivity, "Gagal meng-ACC lamaran", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                    Toast.makeText(this@DetailPendaftarActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
