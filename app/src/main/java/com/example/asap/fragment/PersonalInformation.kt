package com.example.asap.fragment

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.asap.R
import com.example.asap.activity.HomeActivity
import com.example.asap.databinding.FragmentPersonalInformationBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PersonalInformation : Fragment() {
    private var _binding: FragmentPersonalInformationBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentPersonalInformationBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()


        binding.continueBtn.setOnClickListener {

            if (binding.userNameEt.text.toString().isEmpty()) {
                binding.userName.error = "Input Error"
                binding.userName.requestFocus()

            }
            if (!Patterns.PHONE.matcher(binding.phoneNumberEt.text.toString()).matches()) {

                binding.userName.error = "Input Error"
                binding.userName.requestFocus()
            } else {
                showProgressBar()
                val sharedPreferences =
                    activity?.getSharedPreferences("info", Context.MODE_PRIVATE)
                val editor = sharedPreferences?.edit()
                editor?.putString("user_name", binding.userNameEt.text.toString())
                editor?.putString("phone_number", binding.phoneNumberEt.text.toString())
                editor?.apply()
                val userName = binding.userNameEt.text.toString()
                val phoneNumber = binding.phoneNumberEt.text.toString()



                databaseReference =
                    FirebaseDatabase.getInstance().getReference("UsersInfo").child(uid)

                val hashMap: HashMap<String, String> = HashMap()
                hashMap.put("userId", uid)
                hashMap.put("userName", userName)
                hashMap.put("phoneNumber", phoneNumber)
                hashMap.put("profileImage", "")

                databaseReference.setValue(hashMap)

                    .addOnCompleteListener {

                        if (it.isSuccessful) {

                            showSnackbar("Details Saved Successfully")
                            val intent = Intent(requireContext(), HomeActivity::class.java)
                            activity?.startActivity(intent)


                        } else {
                            hideProgressBar()
                            showSnackbar("Details Not Saved, Try Again!")
                        }

                    }
            }

        }





        return view
    }

    private fun showSnackbar(message: String) {
        val rootView = binding.root
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
        Snackbar.ANIMATION_MODE_SLIDE
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