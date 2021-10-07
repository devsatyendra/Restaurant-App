package com.restaurantapp.ui.category_dishes

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.restaurantapp.data.Dish
import com.restaurantapp.data.Dishes

class CategoryDishesViewModel : ViewModel() {
    private var _dishesList: MutableLiveData<MutableList<Dish>>? = null

    fun getDishesList(): LiveData<MutableList<Dish>>? {
        if (_dishesList == null) {
            _dishesList = MutableLiveData()
            _dishesList?.value = Dishes.categoryList
        }
        return _dishesList
    }
}