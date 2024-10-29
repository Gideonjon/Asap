package com.example.asap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.asap.R
import com.example.asap.utils.SendMoney

class TransactionAdapter(private val list: MutableList<SendMoney>) :
    RecyclerView.Adapter<TransactionAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val bankName: TextView = itemView.findViewById(R.id.userSent)
        val description: TextView = itemView.findViewById(R.id.description)
        val amount: TextView = itemView.findViewById(R.id.amount)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.transaction, parent, false)


        return TransactionAdapter.MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = list[position]

        holder.projectType.text = currentItem.projectType
        holder.projectDescription.text = currentItem.description
        holder.projectTitle.text = currentItem.projectTitle
        holder.workersNeeded.text = currentItem.workersNeeded


    }


}