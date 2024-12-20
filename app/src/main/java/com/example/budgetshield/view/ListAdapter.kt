package com.example.budgetshield.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetshield.R

class ListAdapter(private val listMembers: List<RecordModel>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.item_member,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        val item = listMembers[position]
        holder.descript.text = item.description
        holder.ExrecordType.text = item.recordType
        holder.amount.text = item.amountPrice
        holder.recordDate.text = item.dateOfRecord

    }

    override fun getItemCount(): Int {
        return listMembers.size
    }
    class ViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        val descript = item.findViewById<TextView>(R.id.Description)
        val ExrecordType = item.findViewById<TextView>(R.id.EorI)
        val amount = item.findViewById<TextView>(R.id.price)
        val recordDate = item.findViewById<TextView>(R.id.date)
    }
}