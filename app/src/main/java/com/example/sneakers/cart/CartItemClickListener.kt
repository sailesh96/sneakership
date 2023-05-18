package com.example.sneakers.cart

import com.example.sneakers.cartdatabase.Cart

interface CartItemClickListener {
    fun onItemClick(item: Cart)
}