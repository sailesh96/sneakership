package com.example.sneakers.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM Cart")
    fun getAll(): List<Cart>


    @Query("DELETE FROM Cart WHERE id=:id")
    fun delete(id: String)

    @Insert
    fun insert(item: Cart)

}

