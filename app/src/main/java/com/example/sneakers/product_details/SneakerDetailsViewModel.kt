package com.example.sneakers.product_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sneakers.SneakerRepository
import com.example.sneakers.roomdatabase.Cart

class SneakerDetailsViewModel(val repository: SneakerRepository) : ViewModel() {

    fun addToCart(item: Cart) {
        repository.addToCart(item)
    }

    fun removeFromCart(item: Cart) {
        repository.removeFromCart(item)
    }
}

class SneakerDetailsViewModelFactory(val respository: SneakerRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SneakerRepository::class.java).newInstance(respository)
    }
}