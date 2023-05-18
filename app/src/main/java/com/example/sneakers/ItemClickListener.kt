package com.example.sneakers

interface ItemClickListener {
    fun onItemClick(item:SneakerModel,isCart:Boolean=false)
}
