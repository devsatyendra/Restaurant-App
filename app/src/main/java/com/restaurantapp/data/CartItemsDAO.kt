package com.restaurantapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartItemsDAO {

    @Query("SELECT * FROM cart_items WHERE id = :id LIMIT 1")
    fun getItemId(id: Int?): CartItem

    @Query("Select * from cart_items")
    fun getCartItemsList(): LiveData<MutableList<CartItem>>

    @Insert
    fun insertItem(vararg cartItem: CartItem)

    @Update
    fun updateItem(vararg cartItem: CartItem)
    
    @Delete
    fun delete(cartItem: CartItem)

}