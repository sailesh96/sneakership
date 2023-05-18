package com.example.sneakers.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sneakers.SneakerModel
import com.example.sneakers.SneakerRepository
import com.example.sneakers.cartdatabase.Cart

class HomeViewModel(val repository: SneakerRepository) : ViewModel() {
    val sneakerLiveData = MutableLiveData<ArrayList<SneakerModel>>()
    val sneakers: LiveData<ArrayList<SneakerModel>>
        get() = sneakerLiveData

    val cartLiveData = MutableLiveData<ArrayList<Cart>>()
    val cart: LiveData<ArrayList<Cart>>
        get() = cartLiveData

    fun getSneakers() {
        sneakerLiveData.value = repository.getSneakers()
    }

    fun addToCart(item: Cart) {
        repository.addToCart(item)
    }

    fun getCart(){
       cartLiveData.value = repository.getCart()
    }

    fun removeFromCart(item: Cart) {
        repository.removeFromCart(item)
    }
}

class HomeViewModelFactory(val respository: SneakerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SneakerRepository::class.java).newInstance(respository)
    }
}