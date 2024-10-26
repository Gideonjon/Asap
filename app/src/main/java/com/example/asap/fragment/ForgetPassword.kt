package com.example.asap.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.asap.R
import com.example.asap.databinding.FragmentForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgetPassword : Fragment() {
private var _binding : FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentForgetPasswordBinding.inflate(inflater,container,false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()

        binding.getStarted.setOnClickListener {
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailEt.text.toString()).matches()) {
                binding.email.error = "Invalid Email"
            }else{
                auth.sendPasswordResetEmail(binding.emailEt.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Check Your Email,",
                                Toast.LENGTH_SHORT
                            ).show()
                            Navigation.findNavController(view)
                                .navigate(R.id.action_forgetPassword_to_login)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Email not sent",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            }

            }




        binding.arrowBack.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_createAccount_to_onboardingScreen)

        }
        return view

    }


}