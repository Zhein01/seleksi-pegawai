package com.example.tes.admin.soal

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.ApiService
import com.example.tes.admin.ModelSoal
import com.example.tes.admin.SendResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Admin_Adapter_lihatsoalforuser(
    private val listSoal: MutableList<ModelSoal>,
    private val context: Context
) : RecyclerView.Adapter<Admin_Adapter_lihatsoalforuser.SoalViewHolder>() {

    inner class SoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtPertanyaan: TextView = itemView.findViewById(R.id.txtPertanyaan)
        val radioA: RadioButton = itemView.findViewById(R.id.radioAa)
        val radioB: RadioButton = itemView.findViewById(R.id.radioBa)
        val radioC: RadioButton = itemView.findViewById(R.id.radioCa)
        val radioD: RadioButton = itemView.findViewById(R.id.radioDa)
        val jawaban: TextView = itemView.findViewById(R.id.jawaban)
        val btnEdit: Button = itemView.findViewById(R.id.btnEditSoala)
        val btnHapus: Button = itemView.findViewById(R.id.btnHapusSoala)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_admin_editsoal_foradmin, parent, false)
        return SoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: SoalViewHolder, position: Int) {
        val soal = listSoal[position]
        holder.txtPertanyaan.text = soal.soal
        holder.radioA.text = "A. ${soal.pil1}"
        holder.radioB.text = "B. ${soal.pil2}"
        holder.radioC.text = "C. ${soal.pil3}"
        holder.radioD.text = "D. ${soal.pil4}"
        holder.jawaban.text = soal.jawaban

        holder.radioA.isEnabled = false
        holder.radioB.isEnabled = false
        holder.radioC.isEnabled = false
        holder.radioD.isEnabled = false

        val jawabanBenar = holder.jawaban.text.toString()
        if (jawabanBenar == "A") {
            holder.radioA.isChecked = true
        } else if (jawabanBenar == "B") {
            holder.radioB.isChecked = true
        } else if (jawabanBenar == "C") {
            holder.radioC.isChecked = true
        } else if (jawabanBenar == "D") {
            holder.radioD.isChecked = true
        }

        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, Admin_editSoalActivity::class.java)
            intent.putExtra("id_soal", soal.id)
            intent.putExtra("soal", soal.soal)
            intent.putExtra("pil1", soal.pil1)
            intent.putExtra("pil2", soal.pil2)
            intent.putExtra("pil3", soal.pil3)
            intent.putExtra("pil4", soal.pil4)
            intent.putExtra("jawaban", soal.jawaban)
            context.startActivity(intent)
        }

        holder.btnHapus.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(context)
            builder.setTitle("Hapus Soal?")
            builder.setMessage("Apakah Anda yakin ingin menghapus soal ini?")

            builder.setPositiveButton("Ya") { dialog, _ ->
                hapusSoal(soal.id, position)
                dialog.dismiss()
            }

            builder.setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }

            builder.show()
        }
    }

    override fun getItemCount(): Int = listSoal.size
    private fun hapusSoal(idSoal: Int, position: Int) {
        val apiService = ApiClient.instance
        val call = apiService.hapusSoal(idSoal)

        call.enqueue(object : Callback<SendResponse> {
            override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.success == true) {
                        Toast.makeText(context, "Soal berhasil dihapus", Toast.LENGTH_SHORT).show()
                        listSoal.removeAt(position)
                        notifyItemRemoved(position)
                    } else {
                        Toast.makeText(context, "Gagal menghapus soal", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Gagal menghapus soal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
