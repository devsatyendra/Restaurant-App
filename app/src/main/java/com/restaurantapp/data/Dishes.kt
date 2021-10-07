package com.restaurantapp.data

data class Dish(
    var id: Int,
    var name: String,
    var rating: Double = 5.0,
    var imageUrl: String,
    var price: Int,
    var isVeg: Boolean
) {}

object Dishes {
    var famousDishesList = mutableListOf<Dish>(
        Dish(
            1,
            "Paneer Kali Mirch",
            4.3,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/rfg8se64g8ltpvxm7buq",
            250,
            true
        ),
        Dish(
            2,
            "Kadai Paneer",
            5.0,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/dnwevgvuvuqb2s8hxtou",
            225,
            true
        ),
        Dish(
            3,
            "Paneer Malai Methi",
            4.4,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/linqcbcefknkgnjpf5dc",
            180,
            true
        ),
    )

    var categoryList = mutableListOf<Dish>(
        Dish(
            1,
            "Paneer Kali Mirch",
            4.3,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/rfg8se64g8ltpvxm7buq",
            250,
            true
        ),
        Dish(
            2,
            "Kadai Paneer",
            5.0,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/dnwevgvuvuqb2s8hxtou",
            225,
            true
        ),
        Dish(
            3,
            "Paneer Malai Methi",
            4.4,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/linqcbcefknkgnjpf5dc",
            180,
            true
        ),
        Dish(
            4,
            "Smoky Red Rice Bowl",
            4.2,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/t8ufbpqvs8twatdx2hb6",
            250,
            false
        ),
        Dish(
            5,
            "Chicken & Fries",
            5.0,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/pasdg6owucvi23hcp9eb",
            250,
            false
        ),
        Dish(
            6,
            "Butterscotch Tub",
            4.2,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/lkyoxgf5ujxovsqtkvr1",
            350,
            true
        ),
        Dish(
            7,
            "Naked Nutella Waffle",
            4.4,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/z7zdt9uuovndh8iszqwv",
            324,
            true
        ),
        Dish(
            8,
            "Crispy Chicken",
            4.8,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/bxwvkxjdxx5hhsjobgea",
            150,
            false
        ),
        Dish(
            9,
            "Crunchillicious Combo",
            4.4,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/u1r45judfs5ke6ny9jsw",
            180,
            true
        ),
        Dish(
            10,
            "Butterscotch Pastries",
            4.0,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/n3ob5wsffjcdcjk5sdwm",
            70,
            true
        ),
        Dish(
            11,
            "Strawberry Shake",
            4.2,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/llqfdibvj2vl8p0ublnv",
            90,
            true
        ),Dish(
            12,
            "Chicken Leg Rice",
            4.5,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/qfh3nkkkmggrmtv7j5fj",
            180,
            false
        ),
        Dish(
            13,
            "Mutton Biryani(3pcs)",
            4.5,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_1024/bl3yxlqolxqwyiyzi2gp",
            270,
            false
        ),
    )
}