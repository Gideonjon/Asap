package com.example.asap.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.asap.R
import com.example.asap.adapter.TransactionAdapter
import com.example.asap.databinding.FragmentTransactionsBinding
import com.example.asap.utils.SendMoney
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


class Transactions : Fragment() {
    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var userArrayList: MutableList<SendMoney>
    private lateinit var taskAdapter: TransactionAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentTransactionsBinding.inflate(inflater,container,false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference.child("Transactions")

        return view
    }

    private fun getUserData() {
        userArrayList = mutableListOf()
        taskAdapter = JobPostedAdapter(userArrayList)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {


                    for (userSnapshot in snapshot.children) {

                        val user = userSnapshot.getValue(JobsInfo::class.java)

                        if (user != null) {
                            userArrayList.add(user)
                        }

                    }
                    binding.recyclerView.adapter = taskAdapter

                }
                taskAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(error: DatabaseError) {


            }


        })


    }

}