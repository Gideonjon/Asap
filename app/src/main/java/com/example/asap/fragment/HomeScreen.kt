package com.example.asap.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        }

        binding.recieve.setOnClickListener {


        }

        binding.bills.setOnClickListener {


        }




        return view
    }


}