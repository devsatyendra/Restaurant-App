package com.restaurantapp.data

class Cuisine(var id: Int, var name: String, var imageUrl: String) {
}

object CuisinesCategories {
    val categoryList = listOf<Cuisine>(
        Cuisine(
            1,
            "Biryani",
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/bl3yxlqolxqwyiyzi2gp"
        ),
        Cuisine(
            2,
            "Pizzas",
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_520,h_520/rng/md/carousel/production/yrub9l6abs9etopbf0yu"
        ),
        Cuisine(
            3,
            "North Indian",
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_520,h_520/rng/md/carousel/production/d7p1locpg021y41ox2nq"
        ),
        Cuisine(
            4,
            "Ice Cream & Shakes",
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_520,h_520/rng/md/carousel/production/yrub9l6abs9etopbf0yu"
        ),
        Cuisine(
            5,
            "Biryani",
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/bl3yxlqolxqwyiyzi2gp"
        ),
        Cuisine(
            6,
            "Pizzas",
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_520,h_520/rng/md/carousel/production/yrub9l6abs9etopbf0yu"
        ),
    )
}