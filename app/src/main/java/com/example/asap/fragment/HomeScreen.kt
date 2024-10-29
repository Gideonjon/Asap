package com.example.asap.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.asap.R
import com.example.asap.databinding.FragmentHomeScreenBinding


class HomeScreen : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.send.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeScreen_to_send2)


        }

        binding.recieve.setOnClickListener {


        }

        binding.bills.setOnClickListener {


        }




        return view
    }


}