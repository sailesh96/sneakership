package com.example.sneakers.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakers.CommonUtils
import com.example.sneakers.ItemClickListener
import com.example.sneakers.R
import com.example.sneakers.SneakerModel
import com.example.sneakers.databinding.ItemLayoutBinding

class SneakerAdapter(
    val context: Context,
    val list: ArrayList<SneakerModel>,
    val listener: ItemClickListener
) :
    RecyclerView.Adapter<SneakerAdapter.SneakerViewHolder>() {

    class SneakerViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvName
        val price = binding.tvPrice
        val image = binding.ivSneaker
        val add = binding.ivAdd

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext());
        val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false)
        return SneakerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SneakerViewHolder, position: Int) {
        val item = list[position]
        CommonUtils.loadImage(context, item.media?.imageUrl, holder.image)
        holder.name.text = item.name
        holder.price.text =
            context.getString(R.string.set_price, (item.retailPrice ?: 0).toString())
        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
        if (item.addedToCart) {
            holder.add.setImageResource(R.drawable.ic_clear)
            holder.add.setColorFilter(
                ContextCompat.getColor(context, R.color.colorRed),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        } else {
            holder.add.setImageResource(R.drawable.ic_add)
            holder.add.setColorFilter(
                ContextCompat.getColor(context, R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
        holder.add.setOnClickListener {
            list[position].addedToCart = !list[position].addedToCart
            notifyItemChanged(position)
            listener.onItemClick(list[position], true)
        }
    }
}