package com.restaurantapp.ui.main_activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.restaurantapp.R
import com.restaurantapp.data.Cuisine
import com.restaurantapp.ui.category_dishes.CategoryDishesActivity
import com.restaurantapp.utils.Constants
import kotlinx.android.synthetic.main.cuisine_category_slide.view.*

class CuisineCategoryAdapter(private val _context: Context, private val _categoryList: List<Cuisine>) :
    PagerAdapter() {

    override fun getCount(): Int {
        return _categoryList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.cuisine_category_slide, container, false)

        val cuisinesData = _categoryList[position]

        view.txtCuisineName.text = cuisinesData.name
        Glide.with(_context).load(cuisinesData.imageUrl).into(view.imgCuisine)

        view.setOnClickListener {
            val intent = Intent(_context, CategoryDishesActivity::class.java)
            intent.putExtra(Constants.CATEGORY_ID, cuisinesData.id)
            intent.putExtra(Constants.CATEGORY_NAME, cuisinesData.name)
            _context.startActivity(intent)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}