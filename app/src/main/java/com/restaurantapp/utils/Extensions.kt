package com.restaurantapp.utils

import android.app.Activity
import android.content.SharedPreferences
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.*

fun AppCompatActivity.setUpToolbar(toolbar: Toolbar, title: String) {
    setSupportActionBar(toolbar)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    supportActionBar!!.setDisplayShowTitleEnabled(true)
    supportActionBar!!.title = title

    toolbar.setNavigationOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View?) {
            finish()
        }
    })
}

fun Activity.setLanguage(sharedPreferences: SharedPreferences) {

    var locale = Locale(sharedPreferences.getString(Constants.LANGUAGE, "en"))
    val res = resources
    val dm = res.displayMetrics
    val conf = res.configuration
    conf.locale = locale
    res.updateConfiguration(conf, dm)
}