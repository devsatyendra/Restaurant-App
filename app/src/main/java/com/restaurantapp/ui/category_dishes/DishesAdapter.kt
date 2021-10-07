package com.restaurantapp.ui.category_dishes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.restaurantapp.R
import com.restaurantapp.data.CartItem
import com.restaurantapp.data.Dish
import kotlinx.android.synthetic.main.dishes_item_view.view.*

class DishesAdapter(
    private val _context: Context,
    private val _itemSelectedListener: ItemSelectedListener
) :
    RecyclerView.Adapter<DishesAdapter.ViewHolder>() {
    private var _cartList: MutableList<CartItem>? = null
    private lateinit var _dishesList: List<Dish>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(_context).inflate(R.layout.dishes_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = _dishesList[position]
        holder.setData(dish, position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return _dishesList.size
    }

    fun setDishes(it: MutableList<Dish>?) {
        _dishesList = it!!
        notifyDataSetChanged()
    }

    fun setCartItemList(it: MutableList<CartItem>?) {
        _cartList = it!!
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private lateinit var currentDish: Dish
        var currentPosition: Int = 0

        init {
            itemView.btnPlus.setOnClickListener {
                _itemSelectedListener.onItemAdded(currentDish)
            }

            itemView.btnMinus.setOnClickListener {
                _itemSelectedListener.onItemRemoved(currentDish)
            }
        }

        fun setData(dish: Dish, position: Int) {
            this.currentPosition = position
            this.currentDish = dish
            bindUI()
        }

        private fun bindUI() {

                itemView.txtDishName.text = currentDish.name
                Glide.with(_context).load(currentDish.imageUrl).into(itemView.imgDish)
                itemView.txtRating.text = currentDish.rating.toString()
                itemView.txtPrice.text = currentDish.price.toString()
                if (currentDish.isVeg) {
                    itemView.imgVegOrNonVeg.setImageResource(R.drawable.veg_icon_bg)
                } else {
                    itemView.imgVegOrNonVeg.setImageResource(R.drawable.non_veg_icon_bg)
                }

                val index =
                    _cartList?.indexOfFirst { cartItem -> cartItem.id == currentDish.id }

                if (index != -1) {
                    _cartList?.get(index!!)?.quantity?.let {
                        itemView.txtQuantity.text = _cartList?.get(index)?.quantity.toString()
                    }
                }else itemView.txtQuantity.text="0"

        }
    }

    interface ItemSelectedListener {
        fun onItemAdded(item: Dish)
        fun onItemRemoved(item: Dish)
    }
}
