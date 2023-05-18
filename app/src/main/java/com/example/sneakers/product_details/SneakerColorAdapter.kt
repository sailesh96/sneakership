package com.example.sneakers.product_details

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakers.Constants
import com.example.sneakers.R
import com.example.sneakers.databinding.SneakerColorLayoutBinding


class SneakerColorAdapter(
    val context: Context, val list: ArrayList<String>, val color: String
) : RecyclerView.Adapter<SneakerColorAdapter.SneakerColorViewHolder>() {

    class SneakerColorViewHolder(binding: SneakerColorLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val color = binding.ivColor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerColorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext());
        val binding = SneakerColorLayoutBinding.inflate(layoutInflater, parent, false)
        return SneakerColorViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SneakerColorViewHolder, position: Int) {
        val item = list[position]
        when (item) {
            Constants.SneakerColorEnums.Blue.name -> {
                setColor(
                    holder.color,
                    ContextCompat.getColor(context, R.color.colorBlue),
                    color == item
                )
            }

            Constants.SneakerColorEnums.Red.name -> {
                setColor(
                    holder.color,
                    ContextCompat.getColor(context, R.color.colorRed),
                    color == item
                )
            }

            Constants.SneakerColorEnums.Black.name -> {
                setColor(
                    holder.color,
                    ContextCompat.getColor(context, R.color.black),
                    color == item
                )
            }
        }

    }

    fun setColor(colorImage: ImageView, color: Int, isSelected: Boolean) {
        val drawable = colorImage.background.mutate() as GradientDrawable
        drawable.setColor(color)
        if (isSelected) {
            colorImage.setColorFilter(
                ContextCompat.getColor(context, R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN
            );
        } else {
            colorImage.setColorFilter(
                color,
                android.graphics.PorterDuff.Mode.SRC_IN
            );
        }
    }
}