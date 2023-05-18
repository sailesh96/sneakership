package com.example.sneakers

object Constants {
    enum class SneakerColorEnums {
        Blue, Red, Black
    }
    fun SneakerSize(): ArrayList<Int> {
        return arrayListOf(7, 8, 9)
    }
    fun SneakerColors():ArrayList<String>{
        return arrayListOf(SneakerColorEnums.Blue.name,SneakerColorEnums.Red.name,SneakerColorEnums.Black.name)
    }
}