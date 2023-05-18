package com.example.sneakers.product_details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakers.R
import com.example.sneakers.databinding.SneakerSizeLayoutBinding

class SneakerSizeAdapter(
    val context: Context,
    val list: ArrayList<Int>, val size: Int
) : RecyclerView.Adapter<SneakerSizeAdapter.SneakerViewHolder>() {

    class SneakerViewHolder(binding: SneakerSizeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvSize
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext());
        val binding = SneakerSizeLayoutBinding.inflate(layoutInflater, parent, false)
        return SneakerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SneakerViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.toString()
        if (size == item) {
            holder.name.isPressed = true
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.name.isPressed = false
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        }
    }
}