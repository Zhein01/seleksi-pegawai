package com.example.tes.admin.User

import android.annotation.SuppressLint
import retrofit2.Callback
import retrofit2.Response
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import retrofit2.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tes.LoginActivity
import com.example.tes.R
import com.example.tes.admin.ApiClient

class ProfileFragment : Fragment() {
    private var user: UserData? = null
    private lateinit var txtNama: TextView
    private lateinit var txtAlamat: TextView
    private lateinit var txtTelepon: TextView
    private var userId: Int = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profil, container, false)

        txtNama = view.findViewById(R.id.txtNama)
        txtAlamat = view.findViewById(R.id.txtAlamat)
        txtTelepon = view.findViewById(R.id.txtTelepon)

        val btnEdit = view.findViewById<Button>(R.id.btnEditProfil)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        val sharedPref = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        userId = sharedPref.getInt("user_id", -1)
        if (userId == -1) {
            navigateToLogin()
            return view
        }

        getUser(userId)

        btnEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditProfilActivity::class.java)
            intent.putExtra("user_id", user?.id)
            intent.putExtra("nama", user?.nama)
            intent.putExtra("alamat", user?.alamat)
            intent.putExtra("telepon", user?.no_hp)
            intent.putExtra("username", user?.username)
            startActivity(intent)
        }


        btnLogout.setOnClickListener {
            logout(sharedPref)
        }

        return view
    }

    private fun navigateToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun logout(sharedPref: SharedPreferences) {
        sharedPref.edit().clear().apply()

        Toast.makeText(requireContext(), "Anda telah logout", Toast.LENGTH_SHORT).show()

        view?.postDelayed({
            navigateToLogin()
        }, 1000)
    }

    @Override
    override fun onResume() {
        super.onResume()
        getUser(userId)
    }

    fun getUser(id: Int){
        ApiClient.instance.getUserProfile(id).enqueue(object : Callback<UserProfileResponse> {
            override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                if (response.isSuccessful) {
                    user = response.body()?.data!!
                    if (user != null) {
                        txtNama.text = user?.nama
                        txtAlamat.text = user?.alamat
                        txtTelepon.text = user?.no_hp
                    } else {
                        Toast.makeText(requireContext(), "Data user tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}