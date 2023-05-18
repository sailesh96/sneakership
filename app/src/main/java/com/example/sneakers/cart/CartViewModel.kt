package com.example.sneakers.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sneakers.SneakerRepository
import com.example.sneakers.roomdatabase.Cart

class CartViewModel(val repository: SneakerRepository) : ViewModel() {
    val cartLiveData = MutableLiveData<ArrayList<Cart>>()
    val cart: LiveData<ArrayList<Cart>>
        get() = cartLiveData

    fun getCart() {
        cartLiveData.value = repository.getCart()
    }

    fun removeFromCart(item: Cart) {
        repository.removeFromCart(item)
    }
}

class CartViewModelFactory(val respository: SneakerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SneakerRepository::class.java).newInstance(respository)
    }
}