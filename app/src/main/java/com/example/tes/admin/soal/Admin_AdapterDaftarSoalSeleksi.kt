package com.example.tes.admin.soal

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tes.R
import com.example.tes.admin.ApiClient
import com.example.tes.admin.ModelBatch
import com.example.tes.admin.SendResponse
import retrofit2.Call
import retrofit2.Response

class Admin_AdapterDaftarSoalSeleksi(
    private val listBatch: List<ModelBatch>,
    private val context: Context
) : RecyclerView.Adapter<Admin_AdapterDaftarSoalSeleksi.BatchViewHolder>() {

    inner class BatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNamaBatch: TextView = itemView.findViewById(R.id.txtNamaBatch)
        val btnLihatBatch: Button = itemView.findViewById(R.id.btnlihatBatch)
        val btnEditBatch: Button = itemView.findViewById(R.id.btnEditBatch)
        val btnHapusBatch: Button = itemView.findViewById(R.id.btnHapusBatch)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daftarsoal, parent, false)
        return BatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: BatchViewHolder, position: Int) {
        val batch = listBatch[position]
        holder.txtNamaBatch.text = batch.nama

        holder.btnLihatBatch.setOnClickListener {
            val intent = Intent(context, Admin_LihatsoalForUser::class.java)
            intent.putExtra("idBatchSoal", batch.id)
            context.startActivity(intent)
        }

        holder.btnEditBatch.setOnClickListener {
            val intent = Intent(context, EditBatchSoalActivity::class.java)
            intent.putExtra("idBatchSoal", batch.id)
            intent.putExtra("namaBatchSoal", batch.nama)
            context.startActivity(intent)
        }

        holder.btnHapusBatch.setOnClickListener {
            if (context != null) {
                androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Hapus Batch Soal")
                    .setMessage("Apakah Anda yakin ingin menghapus batch ini?")
                    .setPositiveButton("Hapus") { dialog, _ ->
                        hapusBatch(batch.id, position)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Batal") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }


    }

    override fun getItemCount(): Int = listBatch.size

    private fun hapusBatch(id: Int, position: Int) {
        val api = ApiClient.instance
        api.deleteBatchSoal(id).enqueue(object : retrofit2.Callback<SendResponse> {
            override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(context, "Batch berhasil dihapus", Toast.LENGTH_SHORT).show()
                    (listBatch as MutableList).removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, listBatch.size)
                } else {
                    Toast.makeText(context, "Gagal hapus: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
