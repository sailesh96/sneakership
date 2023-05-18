package com.example.sneakers

import java.io.Serializable

data class SneakerModel(
   val id:String?,
   val brand:String?,
   val colorway:String?,
   val gender:String?,
   val media:Media?,
   val size:Int?,
   val releaseDate:String?,
   val retailPrice:Int?,
   val styleId:String?,
   val name:String?,
   val title:String?,
   val year:Int?,
   var addedToCart:Boolean=false
):Serializable
data class Media(
    val imageUrl:String?,
    val smallImageUrl:String?,
    val thumbUrl:String?
)
