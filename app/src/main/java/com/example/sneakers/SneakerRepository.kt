package com.example.sneakers

import android.content.Context
import com.example.sneakers.cartdatabase.Cart
import com.example.sneakers.cartdatabase.CartDatabase
import com.google.gson.Gson
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class SneakerRepository(val context: Context) {
    fun getSneakers(): ArrayList<SneakerModel> {
        val jsonArray = JSONArray(loadJSONFromAsset())
        val list = ArrayList<SneakerModel>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val product = Gson().fromJson(jsonObject.toString(), SneakerModel::class.java)
            list.add(product)
        }
        return list
    }

    fun addToCart(item: Cart) {
        val cartDao = CartDatabase.getDatabase(context).CartDao()
        cartDao.insert(item)
    }

    fun removeFromCart(item: Cart) {
        val cartDao = CartDatabase.getDatabase(context).CartDao()
        cartDao.delete(item.id!!)
    }

    fun getCart(): ArrayList<Cart> {
        val cartDao = CartDatabase.getDatabase(context).CartDao()
        return cartDao.getAll() as ArrayList<Cart>
    }

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = context.resources.openRawResource(R.raw.sneaker)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}