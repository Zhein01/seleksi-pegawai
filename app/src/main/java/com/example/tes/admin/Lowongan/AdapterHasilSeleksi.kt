package com.example.tes.admin.Lowongan

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tes.R
import com.example.tes.admin.ApiClient

class AdapterHasilSeleksi(
    private val mlist: List<ModelHasilSeleksi>,
    private val context: Context
) : RecyclerView.Adapter<AdapterHasilSeleksi.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nama = itemView.findViewById<TextView>(R.id.namaHasil)
        val skor = itemView.findViewById<TextView>(R.id.skorHasil)
        val status = itemView.findViewById<TextView>(R.id.status)
        val btnCetak = itemView.findViewById<Button>(R.id.cetak)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_hasil_seleksi, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mlist[position]
        holder.nama.text = data.nama
        holder.skor.text = "Skor: ${data.skor}"
        holder.status.text ="Status: ${data.status}"
        holder.btnCetak.setOnClickListener {
            val url = ApiClient.BASE_URL + "user/lamaran/hasil/cetak-pdf?lamaran_id=${data.lamaranId}"

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = mlist.size
}