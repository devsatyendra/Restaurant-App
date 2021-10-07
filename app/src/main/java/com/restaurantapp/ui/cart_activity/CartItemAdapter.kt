package com.restaurantapp.ui.cart_activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.restaurantapp.R
import com.restaurantapp.data.CartItem
import kotlinx.android.synthetic.main.cart_item_view.view.*
import kotlinx.android.synthetic.main.dishes_item_view.view.imgVegOrNonVeg
import kotlinx.android.synthetic.main.dishes_item_view.view.txtDishName
import kotlinx.android.synthetic.main.dishes_item_view.view.txtPrice

class CartItemAdapter(
    private val _context: Context,
    private val _itemSelectedListener: ItemSelectedListener
) :
    RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {
    private lateinit var _cartItemsList: List<CartItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(_context).inflate(R.layout.cart_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = _cartItemsList[position]
        holder.setData(cartItem, position)
    }

    override fun getItemCount(): Int {
        return _cartItemsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setCartItems(it: MutableList<CartItem>?) {
        _cartItemsList = it!!
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentPosition: Int = 0
        private lateinit var currentCartItem: CartItem

        fun setData(cartItem: CartItem, position: Int) {
            this.currentPosition = position
            this.currentCartItem = cartItem
            bindUI()
        }

        init {
            itemView.btnPlus.setOnClickListener {
                _itemSelectedListener.onItemAdded(currentCartItem)
            }

            itemView.btnMinus.setOnClickListener {
                _itemSelectedListener.onItemRemoved(currentCartItem)
            }
        }

        private fun bindUI() {

            itemView.txtDishName.text = currentCartItem.name
            itemView.txtPrice.text =
                (currentCartItem.price * currentCartItem.quantity).toString()

            if (currentCartItem.isVeg) {
                itemView.imgVegOrNonVeg.setImageResource(R.drawable.veg_icon_bg)
            } else {
                itemView.imgVegOrNonVeg.setImageResource(R.drawable.non_veg_icon_bg)
            }
            itemView.txtQuantity.text = currentCartItem.quantity.toString()

        }
    }

    interface ItemSelectedListener {
        fun onItemAdded(item: CartItem)
        fun onItemRemoved(item: CartItem)
    }
}