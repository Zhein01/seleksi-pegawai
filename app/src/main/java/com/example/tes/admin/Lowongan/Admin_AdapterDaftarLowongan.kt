package com.example.tes.admin.Lowongan

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tes.admin.User.ModelDaftarLowongan
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.SendResponse

class Admin_AdapterDaftarLowongan(
    private val mlist: MutableList<ModelDaftarLowongan>,
    private val context: Context
) : RecyclerView.Adapter<Admin_AdapterDaftarLowongan.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val nmlowongan: TextView = itemView.findViewById(R.id.txtPosisi)
        val nmperusahaan: TextView = itemView.findViewById(R.id.txtPerusahaan)
        val nmlokasi: TextView = itemView.findViewById(R.id.txtLokasi)
        val btnlhtdetail: Button = itemView.findViewById(R.id.btnDetail)
        val btnedit: Button = itemView.findViewById(R.id.btnEditLowongan)
        val btnhapus: Button = itemView.findViewById(R.id.btnhapus)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_admin_item_lowongan, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mlist[position]
        holder.nmlowongan.text = item.nama
        holder.nmperusahaan.text = item.perusahaan
        holder.nmlokasi.text = item.lokasi

        holder.btnlhtdetail.setOnClickListener {
            val intent = Intent(context, Admin_DetailLowonganActivity::class.java)

            intent.putExtra("lowongan_id", item.id)
            intent.putExtra("nama", item.nama)
            intent.putExtra("lokasi", item.lokasi)
            intent.putExtra("deskripsi", item.deskripsi)
            intent.putExtra("perusahaan", item.perusahaan)
            intent.putExtra("kualifikasi", item.kualifikasi)
            intent.putExtra("periode", item.periode)
            context.startActivity(intent)
        }
        holder.btnedit.setOnClickListener {
            val intent = Intent(context, Admin_EditLowongan::class.java)
            intent.putExtra("id_lowongan", item.id)
            intent.putExtra("nama", item.nama)
            intent.putExtra("lokasi", item.lokasi)
            intent.putExtra("deskripsi", item.deskripsi)
            intent.putExtra("perusahaan", item.perusahaan)
            intent.putExtra("kualifikasi", item.kualifikasi)
            intent.putExtra("periode", item.periode)
            context.startActivity(intent)
        }
        holder.btnhapus.setOnClickListener {
            hapusLowongan(position)
        }
    }
    override fun getItemCount(): Int {
        return mlist.size
    }
    private fun hapusLowongan(position: Int) {
        val item = mlist[position]
        val itemId = item.id

        androidx.appcompat.app.AlertDialog.Builder(context)
            .setTitle("Hapus Lowongan")
            .setMessage("Apakah Anda yakin ingin menghapus lowongan ini?")
            .setPositiveButton("Hapus") { dialog, _ ->
                val api = ApiClient.instance
                val call = api.hapusLowongan(itemId)

                call.enqueue(object : retrofit2.Callback<SendResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<SendResponse>,
                        response: retrofit2.Response<SendResponse>
                    ) {
                        if (response.isSuccessful && response.body()?.success == true) {
                            mlist.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, mlist.size)
                        }
                    }
                    override fun onFailure(call: retrofit2.Call<SendResponse>, t: Throwable) {
                        // Optional: tampilkan error message
                    }
                })
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
