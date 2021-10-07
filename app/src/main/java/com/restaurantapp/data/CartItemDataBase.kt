package com.restaurantapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CartItem::class), version = 1)
abstract class CartItemDataBase : RoomDatabase() {
    abstract fun cartItemDAO(): CartItemsDAO

    companion object {
        @Volatile
        private var instance: CartItemDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            CartItemDataBase::class.java, "cart-list.db"
        )
            .build()
    }
}