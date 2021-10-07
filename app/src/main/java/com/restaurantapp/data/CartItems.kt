package com.restaurantapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey var id: Int,
    var name: String,
    var price: Int,
    @ColumnInfo(name = "is_veg") var isVeg: Boolean,
    var quantity: Int = 0
) {
}

