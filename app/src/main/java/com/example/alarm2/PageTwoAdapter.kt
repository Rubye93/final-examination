package com.example.alarm2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class DeveloperAdapter(private val developersNames: List<String>) :
    RecyclerView.Adapter<PageTwoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageTwoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.page_two_item, parent, false)
        return PageTwoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PageTwoViewHolder, position: Int) {
        val name = developersNames[position]
        holder.bind(name)
    }

    override fun getItemCount(): Int {
        return developersNames.size
    }
}