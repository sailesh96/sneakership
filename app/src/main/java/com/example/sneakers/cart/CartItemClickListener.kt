package com.example.sneakers.cart

import com.example.sneakers.roomdatabase.Cart

interface CartItemClickListener {
    fun onItemClick(item: Cart)
}