package com.example.asap.fragment

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.asap.R
import com.example.asap.databinding.FragmentSendBinding
import com.example.asap.utils.SendMoney
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Send : Fragment() {
    private var _binding: FragmentSendBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        _binding = FragmentSendBinding.inflate(inflater,container,false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Transactions")


        val languages = resources.getStringArray(R.array.BankName)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdownmenu, languages)
        val autoCompleteTv = binding.bankName
        autoCompleteTv.setAdapter(arrayAdapter)

        val editText = binding.accountNumberEt.text
        editText?.filters = arrayOf(InputFilter.LengthFilter(10))

        binding.arrowBack.setOnClickListener {

            Navigation.findNavController(view)
                .navigate(R.id.action_send2_to_homeScreen)

        }


        binding.send.setOnClickListener {
            if (isSpinnerEmpty(binding.bankName)) {
                Toast.makeText(requireContext(), "Select An Item", Toast.LENGTH_LONG).show()
            }
            if (binding.accountNumberEt.text.toString().length == 10) {
                Toast.makeText(requireContext(), "Account Number must be 10", Toast.LENGTH_LONG)
                    .show()
            }
            if (binding.amountEt.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Amount Cant be Empty", Toast.LENGTH_LONG)
                    .show()
            } else {
                val bankName = binding.bankName.text.toString()
                val accountNumber = binding.accountNumberEt.text.toString()
                val amount = binding.amountEt.text.toString()
                val remark = binding.remark.text.toString()

                val jobPosted =
                    SendMoney(bankName, accountNumber,amount,remark)

                databaseReference.push().setValue(jobPosted).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showSnackbar("Funds Sent Successfully")
                        Navigation.findNavController(view)
                            .navigate(R.id.action_send2_to_homeScreen)
                    }
                }.addOnFailureListener {

                    showSnackbar("Funds Not Sent")

                }



            }
        }



        return view
    }
    fun isSpinnerEmpty(autoCompleteTextView: AutoCompleteTextView): Boolean {
        val adapter = autoCompleteTextView.adapter
        return adapter == null || adapter.count == 0
    }

    private fun showSnackbar(message: String) {
        val rootView = binding.root
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
        Snackbar.ANIMATION_MODE_SLIDE
    }



}