package com.restaurantapp.ui.main_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.restaurantapp.data.Dish
import com.restaurantapp.data.Dishes

class MainActivityViewModel : ViewModel() {
    private var _famousDishesList: MutableLiveData<MutableList<Dish>>? = null

    fun getFamousDishes(): LiveData<MutableList<Dish>>? {
        if (_famousDishesList == null) {
            _famousDishesList = MutableLiveData()
            _famousDishesList?.value = Dishes.famousDishesList
        }
        return _famousDishesList
    }

}