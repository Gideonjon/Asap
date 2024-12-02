package com.example.asap.fragment

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.asap.R
import com.example.asap.activity.HomeActivity
import com.example.asap.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()
        binding.passwordEt.filters = arrayOf(InputFilter.LengthFilter(8))


        binding.getStarted.setBackgroundResource(R.drawable.btn_deep_btn)

        binding.getStarted.setOnClickListener {

            if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailEt.text.toString()).matches()) {
                binding.email.error = "Invalid Email"
            }
            if (binding.passwordEt.text.toString().length < 8) {

                binding.password.error = "Password must be at least 8 characters"
            }
            if (!binding.passwordEt.text.toString().contains(
                    Regex("(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])")
                )
            ) {

                binding.password.error =
                    "Password must contain at least one uppercase letter, one lowercase letter, and one number"
            } else {
                showProgressBar()

                auth.signInWithEmailAndPassword(
                    binding.emailEt.text.toString(),
                    binding.passwordEt.text.toString()
                )

                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Welcome Back,",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(requireContext(), HomeActivity::class.java)
                            activity?.startActivity(intent)

                        } else {
                            hideProgressBar()
                            Toast.makeText(
                                requireContext(),
                                "Login Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            }
        }

        binding.forgotPassword.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_login_to_forgetPassword)

        }

        binding.arrowBack.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_login_to_onboardingScreen)

        }


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailEt.text.toString())
                        .matches()
                ) {
                    binding.email.error = "Incorrect Email"
                    binding.getStarted.setBackgroundResource(R.drawable.btn_deep_btn)
                    binding.email.requestFocus()
                } else {
                    binding.email.error = "Correct Email"
                    binding.getStarted.setBackgroundResource(R.drawable.btn_deep_btn)

                }
                if (binding.passwordEt.text.toString().length < 8) {
                    binding.password.error = "Password must be at least 8 characters"
                    binding.getStarted.setBackgroundResource(R.drawable.btn_deep_btn)
                    binding.password.requestFocus()
                } else {
                    binding.password.error = "8 digits complete"
                    binding.getStarted.setBackgroundResource(R.drawable.btn_deep_btn)

                }
                if (!binding.passwordEt.text.toString().contains(
                        Regex("(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])")
                    )
                ) {
                    binding.getStarted.setBackgroundResource(R.drawable.btn_deep_btn)
                    binding.password.error =
                        "Password must contain at least one uppercase letter, one lowercase letter, and one number"
                } else {
                    binding.getStarted.setBackgroundResource(R.drawable.btn_deep_btn)
                    binding.password.error =
                        "Password Is Strong"

                }
                if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailEt.text.toString())
                        .matches() && binding.passwordEt.text.toString().length < 8
                    && binding.passwordEt.text.toString()
                        .contains(Regex("(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])"))
                ) {

                    binding.getStarted.setBackgroundResource(R.drawable.sign_up_btn)
                }


            }

        }





        binding.passwordEt.addTextChangedListener(textWatcher)
        binding.emailEt.addTextChangedListener(textWatcher)




        return view
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        val color = ContextCompat.getColor(
            requireContext(),
            R.color.brand_color
        )

        binding.progressBar.indeterminateDrawable.setColorFilter(
            color,
            PorterDuff.Mode.SRC_IN
        )
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

}