package com.example.tes.admin.User

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tes.R
import com.example.tes.admin.ModelSoal

class AdapterSoal(
    private val listSoal: MutableList<ModelSoal>,
    private val context: Context
) : RecyclerView.Adapter<AdapterSoal.SoalViewHolder>() {

    inner class SoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtPertanyaan: TextView = itemView.findViewById(R.id.txtPertanyaan)
        val radioA: RadioButton = itemView.findViewById(R.id.radioAa)
        val radioB: RadioButton = itemView.findViewById(R.id.radioBa)
        val radioC: RadioButton = itemView.findViewById(R.id.radioCa)
        val radioD: RadioButton = itemView.findViewById(R.id.radioDa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_soal_item, parent, false)
        return SoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: SoalViewHolder, position: Int) {
        val soal = listSoal[position]

        holder.txtPertanyaan.text = soal.soal
        holder.radioA.text = "A. ${soal.pil1}"
        holder.radioB.text = "B. ${soal.pil2}"
        holder.radioC.text = "C. ${soal.pil3}"
        holder.radioD.text = "D. ${soal.pil4}"

        val radioGroup = holder.itemView.findViewById<RadioGroup>(R.id.radioGroupJawabana)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            soal.jawabanUser = when (checkedId) {
                R.id.radioAa -> "A"
                R.id.radioBa -> "B"
                R.id.radioCa -> "C"
                R.id.radioDa -> "D"
                else -> ""
            }
        }
    }

    override fun getItemCount(): Int = listSoal.size
}
