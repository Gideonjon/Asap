package com.example.asap.fragment

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.asap.R
import com.example.asap.databinding.FragmentHomeScreenBinding


class HomeScreen : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private var balance: Double = 500000.0 // Initial user balance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        val view = binding.root


        updateBalanceDisplay()

        binding.send.setOnClickListener {
            showSendDialog()
        }

        binding.imageCopy.setOnClickListener {

            copyToClipboard(binding.accountNumberTxt.text.toString())

        }

        binding.recieve.setOnClickListener {

            showToast("Coming soon")
        }

        binding.bills.setOnClickListener {
            showToast("Coming Soon")


        }




        return view
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun copyToClipboard(text: String) {
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)

        // Show a toast message for feedback
        Toast.makeText(requireContext(), "Text copied to clipboard!", Toast.LENGTH_SHORT).show()
    }

    private fun showSendDialog() {
        // Create an input dialog to ask for the amount to send
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Send Money")

        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        builder.setView(input)

        builder.setPositiveButton("Send") { dialog, _ ->
            val enteredAmount = input.text.toString().toDoubleOrNull()

            if (enteredAmount != null && enteredAmount > 0) {
                if (enteredAmount <= balance) {
                    // Deduct amount and update balance
                    balance -= enteredAmount
                    updateBalanceDisplay()
                    Toast.makeText(
                        requireContext(),
                        "₦$enteredAmount sent successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), "Insufficient balance!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(requireContext(), "Invalid amount entered!", Toast.LENGTH_SHORT)
                    .show()
            }

            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun updateBalanceDisplay() {
        // Update the displayed balance
        binding.balance.text = "₦ ${String.format("%,.2f", balance)}"
    }

}