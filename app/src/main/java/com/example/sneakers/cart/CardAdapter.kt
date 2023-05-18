package com.example.sneakers.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakers.CommonUtils
import com.example.sneakers.ItemClickListener
import com.example.sneakers.R
import com.example.sneakers.SneakerModel
import com.example.sneakers.databinding.CartItemLayoutBinding
import com.example.sneakers.roomdatabase.Cart

class CartAdapter(
    val context: Context, var list: ArrayList<Cart>, val listener: CartItemClickListener
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(binding: CartItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvName
        val price = binding.tvPrice
        val image = binding.ivSneaker
        val clear = binding.ivClear

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext());
        val binding = CartItemLayoutBinding.inflate(layoutInflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = list[position]
        CommonUtils.loadImage(context, item.image, holder.image)
        holder.name.text = item.name
        holder.price.text =
            context.getString(R.string.set_price, (item.retailPrice ?: 0).toString())
        holder.clear.setOnClickListener {
            list.removeAt(position)
            notifyItemChanged(position)
            listener.onItemClick(item)
        }
    }
}