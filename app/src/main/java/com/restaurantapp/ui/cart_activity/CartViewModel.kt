package com.restaurantapp.ui.cart_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.restaurantapp.data.CartItem
import com.restaurantapp.data.CartItemDataBase
import com.restaurantapp.data.CartItemsDAO

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private var _cartItemList: LiveData<MutableList<CartItem>>? = null
    private var _cartItemsDAO: CartItemsDAO
    private var _cartItemDataBase: CartItemDataBase

    init {
        _cartItemDataBase = CartItemDataBase.invoke(application)
        _cartItemsDAO = _cartItemDataBase.cartItemDAO()
        _cartItemList = _cartItemsDAO.getCartItemsList()
    }

    fun getCartItem(): LiveData<MutableList<CartItem>>? {
        return _cartItemList
    }

    suspend fun getItemId(id: Int): CartItem {
        return _cartItemsDAO.getItemId(id)
    }

    suspend fun updateItem(cartItem: CartItem) {
        _cartItemsDAO.updateItem(cartItem)
    }

    suspend fun removeItem(cartItem: CartItem) {
        _cartItemsDAO.delete(cartItem)
    }

    suspend fun onItemAdded(cartItem: CartItem) {
        _cartItemsDAO.insertItem(cartItem)
    }

}