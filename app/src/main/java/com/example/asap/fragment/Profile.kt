package com.example.asap.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.asap.R
import com.example.asap.activity.MainActivity
import com.example.asap.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth


class Profile : Fragment() {
private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentProfileBinding.inflate(inflater,container,false)

        val view = binding.root

        auth = FirebaseAuth.getInstance()
        binding.textView10.text = auth.currentUser?.email.toString()



        binding.logout.setOnClickListener {
            if (auth.currentUser != null) {
                auth.signOut()
                Toast.makeText(requireContext(), "Log Out Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity::class.java)
                activity?.startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Cant Log Out", Toast.LENGTH_SHORT).show()

            }
        }



            return view
    }


}