package com.example.tes.admin.Lowongan

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tes.R

class Admin_AdapterDaftarPendaftar(
    private val mlist: List<Modelpendaftar>,
    private val context: Context
) : RecyclerView.Adapter<Admin_AdapterDaftarPendaftar.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nm: TextView = view.findViewById(R.id.txtNamaPendaftar)
        val btnDetail: Button = view.findViewById(R.id.btnLihatDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_admin_item_pendaftaran, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mlist[position]
        holder.nm.text = item.user.nama

        holder.btnDetail.setOnClickListener {
            val intent = Intent(context, DetailPendaftarActivity::class.java)
            intent.putExtra("pendaftar", item)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = mlist.size
}
