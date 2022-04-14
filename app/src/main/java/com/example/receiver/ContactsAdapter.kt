package com.example.receiver

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receiver.ContactsAdapter.ViewHolder

class ContactsAdapter(private val listContact : List<Contact>, val context : Context) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contacts_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = listContact[position]

        holder.tvNumber.text = context.getString(R.string.number, contact.number)
        holder.tvName.text = context.getString(R.string.name, contact.name)
    }

    override fun getItemCount(): Int {
        return listContact.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvName: TextView = itemView.findViewById<TextView>(R.id.tv_name)
        val tvNumber: TextView = itemView.findViewById<TextView>(R.id.tv_number)

    }
}