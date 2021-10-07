package com.restaurantapp.ui.category_dishes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.restaurantapp.R
import com.restaurantapp.data.CartItem
import com.restaurantapp.data.Dish
import com.restaurantapp.ui.BaseActivity
import com.restaurantapp.ui.cart_activity.CartActivity
import com.restaurantapp.ui.cart_activity.CartViewModel
import com.restaurantapp.utils.Constants
import com.restaurantapp.utils.setLanguage
import com.restaurantapp.utils.setUpToolbar
import kotlinx.android.synthetic.main.activity_category_dishes.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CategoryDishesActivity : BaseActivity() {
    private lateinit var _viewModel: CategoryDishesViewModel
    private lateinit var _cartViewModel: CartViewModel
    private lateinit var _dishesAdapter: DishesAdapter
    private lateinit var _sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _sharedPreferences = getSharedPreferences(
            Constants.PREF_NAME, Context.MODE_PRIVATE
        )
        setLanguage(_sharedPreferences)
        setContentView(R.layout.activity_category_dishes)
        setUpToolbar(toolbar, intent.getStringExtra(Constants.CATEGORY_NAME)!!)

        _viewModel = ViewModelProvider(this).get(CategoryDishesViewModel::class.java)
        _cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        setUpRecyclerView()

        _viewModel.getDishesList()?.observeForever { it ->
            _dishesAdapter.setDishes(it)
        }

        _cartViewModel.getCartItem()?.observeForever { it ->
            bindUI(it)
            _dishesAdapter.setCartItemList(it)
        }

        viewGoToCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpRecyclerView() {
        GridLayoutManager(
            this,
            2,
            RecyclerView.VERTICAL,
            false
        ).apply {
            rvDishes.layoutManager = this
        }

        _dishesAdapter = DishesAdapter(this, object : DishesAdapter.ItemSelectedListener {

            override fun onItemAdded(item: Dish) {
                GlobalScope.launch {
                    val cartItem = _cartViewModel.getItemId(item.id)

                    if (cartItem == null) {
                        _cartViewModel.onItemAdded(
                            CartItem(
                                item.id,
                                item.name,
                                item.price,
                                item.isVeg,
                                1
                            )
                        )
                    } else {
                        _cartViewModel.updateItem(
                            CartItem(
                                item.id,
                                item.name,
                                item.price,
                                item.isVeg,
                                cartItem.quantity + 1
                            )
                        )
                    }
                }
            }

            override fun onItemRemoved(item: Dish) {
                GlobalScope.launch {
                    val cartItem = _cartViewModel.getItemId(item.id)

                    if (cartItem != null) {

                        if (cartItem.quantity - 1 > 0) {
                            _cartViewModel.updateItem(
                                CartItem(
                                    item.id,
                                    item.name,
                                    item.price,
                                    item.isVeg,
                                    cartItem.quantity - 1
                                )
                            )
                        } else {
                            _cartViewModel.removeItem(cartItem)
                        }
                    }
                }
            }
        })
        rvDishes.adapter = _dishesAdapter
    }

    private fun bindUI(cartList: MutableList<CartItem>?) {
        var total = 0
        if (cartList?.isNotEmpty() == true) {
            viewGoToCart.visibility = View.VISIBLE
            for (cartItem in cartList) {
                total = total + cartItem.quantity * cartItem.price
            }
            txtTotal.text = total.toString()
            txtCartItemCount.text = "${cartList.size} Items"
        } else {
            viewGoToCart.visibility = View.GONE
        }
    }

}