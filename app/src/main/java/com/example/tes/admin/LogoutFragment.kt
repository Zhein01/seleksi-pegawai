package com.example.tes.admin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.tes.LoginActivity
import com.example.tes.R
class LogoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logout, container, false)

        val sharedPref = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)

        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            logout(sharedPref)
        }

        return view
    }

    private fun logout(sharedPref: SharedPreferences) {
        sharedPref.edit().clear().apply()

        Toast.makeText(requireContext(), "Anda telah logout", Toast.LENGTH_SHORT).show()

        view?.postDelayed({
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }, 1000)
    }
}