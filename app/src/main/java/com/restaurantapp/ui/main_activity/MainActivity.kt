package com.restaurantapp.ui.main_activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.restaurantapp.R
import com.restaurantapp.data.CartItem
import com.restaurantapp.data.CuisinesCategories
import com.restaurantapp.data.Dish
import com.restaurantapp.ui.BaseActivity
import com.restaurantapp.ui.SplashActivity
import com.restaurantapp.ui.cart_activity.CartActivity
import com.restaurantapp.ui.cart_activity.CartViewModel
import com.restaurantapp.ui.category_dishes.DishesAdapter
import com.restaurantapp.utils.Constants
import com.restaurantapp.utils.setLanguage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : BaseActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var _cartViewModel: CartViewModel
    private lateinit var _mainActivityViewModel: MainActivityViewModel
    private lateinit var _dishesAdapter: DishesAdapter
    private lateinit var _sharedPreferences: SharedPreferences

    private var _languages = arrayOf("Language", "English", "Hindi")

    private lateinit var _cuisinesPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _sharedPreferences = getSharedPreferences(
            Constants.PREF_NAME, Context.MODE_PRIVATE
        )
        setLanguage(_sharedPreferences)
        setContentView(R.layout.activity_main)
        setUpLanguageSelector()
        setUpViewPager()

        _mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        _cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        _mainActivityViewModel.getFamousDishes()?.observe(this, {
            _dishesAdapter.setDishes(it)
        })

        _cartViewModel.getCartItem()?.observe(this, {
            _dishesAdapter.setCartItemList(it)
            bindUI(it)
        })

        viewGoToCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        setUpRecyclerView()

    }


    private fun setUpRecyclerView() {
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        rvFamousDishes.layoutManager = staggeredGridLayoutManager
        rvFamousDishes.isNestedScrollingEnabled = false

        _dishesAdapter =
            DishesAdapter(this, object : DishesAdapter.ItemSelectedListener {
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
        rvFamousDishes.adapter = _dishesAdapter
    }

    private fun setUpViewPager() {
        _cuisinesPager = findViewById(R.id.cuisinesPager)
        _cuisinesPager.setPadding(0, 0, 80, 0);

        val cuisineCategoryAdapter =
            CuisineCategoryAdapter(this, CuisinesCategories.categoryList)
        _cuisinesPager.adapter = cuisineCategoryAdapter

        _cuisinesPager.currentItem = 1
        onCategoryChange(_cuisinesPager, cuisineCategoryAdapter)
        autoScrollViewPager(cuisineCategoryAdapter)
    }

    private fun setUpLanguageSelector() {
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, _languages)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(mylanguage)
        {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@MainActivity
            gravity = Gravity.CENTER

        }
    }

    private fun bindUI(cartList: MutableList<CartItem>?) {
        var total = 0
        if (cartList?.isNotEmpty() == true) {
            viewGoToCart.visibility = View.VISIBLE
            for (cartItem in cartList) {
                total = total + cartItem.quantity * cartItem.price
            }
            txtTotal.text = total.toString()
            txtCartItemCount.text = "${cartList.size} items"
        } else {
            viewGoToCart.visibility = View.GONE
        }
    }

    private fun onCategoryChange(
        _cuisinesPager: ViewPager?,
        cuisineCategoryAdapter: CuisineCategoryAdapter
    ) {
        _cuisinesPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    val index: Int = _cuisinesPager.currentItem
                    if (index == 0)
                        _cuisinesPager.setCurrentItem(
                            cuisineCategoryAdapter.count - 2,
                            false
                        )
                    else if (index == cuisineCategoryAdapter.count - 1)
                        _cuisinesPager.setCurrentItem(1, false)
                }
            }

        })
    }

    fun autoScrollViewPager(cuisineCategoryAdapter: CuisineCategoryAdapter) {
        val handler = Handler()
        val update = Runnable {
            if (_cuisinesPager.currentItem == cuisineCategoryAdapter.count - 1) {
                _cuisinesPager.setCurrentItem(0, true)
            } else {
                _cuisinesPager.setCurrentItem(
                    _cuisinesPager.currentItem + 1, true
                )
            }
        }

        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 6000, 6000)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val localeName: String = when (position) {
            1 -> "en"
            2 -> "hi"
            else -> _sharedPreferences.getString(Constants.LANGUAGE, "en")!!
        }

        if (localeName != _sharedPreferences.getString(Constants.LANGUAGE, "en")) {
            with(_sharedPreferences.edit()) {
                putString(Constants.LANGUAGE, localeName)
                apply()
            }

            val intent = Intent(this, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}