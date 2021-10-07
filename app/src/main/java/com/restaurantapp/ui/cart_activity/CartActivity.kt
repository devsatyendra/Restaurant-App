package com.restaurantapp.ui.cart_activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.restaurantapp.R
import com.restaurantapp.data.CartItem
import com.restaurantapp.ui.BaseActivity
import com.restaurantapp.utils.Constants
import com.restaurantapp.utils.setLanguage
import com.restaurantapp.utils.setUpToolbar
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartActivity : BaseActivity() {
    private lateinit var _viewModel: CartViewModel
    private lateinit var _cartItemAdapter: CartItemAdapter
    private lateinit var _sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _sharedPreferences = getSharedPreferences(
            Constants.PREF_NAME, Context.MODE_PRIVATE
        )
        setLanguage(_sharedPreferences)
        setContentView(R.layout.activity_cart)
        setUpToolbar(
            toolbar, "Cart"
        )

        _viewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        _viewModel.getCartItem()?.observe(this, {
            _cartItemAdapter.setCartItems(it)
            bindUI(it)
        })

        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        ).apply {
            rvCart.layoutManager = this
        }

        _cartItemAdapter =
            CartItemAdapter(this, object : CartItemAdapter.ItemSelectedListener {
                override fun onItemAdded(item: CartItem) {
                    GlobalScope.launch {
                        val cartItem = _viewModel.getItemId(item.id)
                        _viewModel.updateItem(
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

                override fun onItemRemoved(item: CartItem) {
                    GlobalScope.launch {
                        val cartItem = _viewModel.getItemId(item.id)

                        if (cartItem != null) {
                            if (cartItem.quantity - 1 > 0) {
                                _viewModel.updateItem(
                                    CartItem(
                                        item.id,
                                        item.name,
                                        item.price,
                                        item.isVeg,
                                        cartItem.quantity - 1
                                    )
                                )
                            } else {
                                _viewModel.removeItem(cartItem)
                            }
                        }
                    }
                }
            })
        rvCart.adapter = _cartItemAdapter
    }

    private fun bindUI(cartList: MutableList<CartItem>?) {
        var total = 0

        if (cartList?.size!! > 0) {
            for (cartItem in cartList) {
                total = total + cartItem.quantity * cartItem.price
            }

            txtSubTotal.text = total.toString()
            val taxes = total * 2.5 / 100
            txtCGST.text = taxes.toString()
            txtSGST.text = taxes.toString()
            val grandTotal = total + 2 * taxes
            txtGrandTotal.text = grandTotal.toString()
        } else {
            finish()
        }
    }
}
